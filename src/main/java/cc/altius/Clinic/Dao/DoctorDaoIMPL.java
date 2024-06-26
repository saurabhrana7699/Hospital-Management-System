/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Model.DoctorCalendar;
import cc.altius.Clinic.Model.DoctorIdAndScheduleDate;
import cc.altius.Clinic.Model.DoctorSchedule;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.ScheduleTime;
import cc.altius.Clinic.RowMapper.DoctorCalendarRowMapper;
import cc.altius.Clinic.RowMapper.DoctorScheduleRowMapper;
import cc.altius.Clinic.RowMapper.IdAndLabelRowMapper;
import cc.altius.Clinic.RowMapper.ScheduleTimeRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class DoctorDaoIMPL implements DoctorDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<IdAndLabel> getDoctorList() {
        String sqlString = "SELECT d.DOCTOR_ID `ID`, u.NAME `LABEL` FROM ms_doctor d LEFT JOIN us_user u ON d.USER_ID=u.USER_ID";
        return this.jdbcTemplate.query(sqlString, new IdAndLabelRowMapper());
    }

    @Override
    public DoctorSchedule getDoctorSchedule(int doctorId, String scheduleDate) {
        String sqlString = "SELECT d.DOCTOR_ID, u.NAME `DOCTOR_NAME` ,ds.SCHEDULE_DATE from ms_doctor d LEFT JOIN us_user u ON d.USER_ID=u.USER_ID  LEFT JOIN ms_doctor_schedule ds ON d.DOCTOR_ID=ds.DOCTOR_ID where d.DOCTOR_ID=:doctorId AND ds.SCHEDULE_DATE =:scheduleDate group by d.DOCTOR_ID , ds.SCHEDULE_DATE";
        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", doctorId);
        System.out.println("---------------------- " + scheduleDate);
        params.put("scheduleDate", scheduleDate);
        DoctorSchedule ds = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DoctorScheduleRowMapper());
        sqlString = "SELECT  ds.START_TIME, ds.STOP_TIME from  ms_doctor_schedule ds where ds.DOCTOR_ID=:doctorId AND ds.SCHEDULE_DATE =:scheduleDate";
        this.namedParameterJdbcTemplate.query(sqlString, params, new ScheduleTimeRowMapper());
        return ds;
    }

    @Override
    @Transactional
    public int addDoctorSchedule(DoctorSchedule doctorSchdule) {
        String sqlString = "DELETE ds.* from ms_doctor_schedule ds where ds.DOCTOR_ID=:DOCTOR_ID AND ds.SCHEDULE_DATE=:SCHEDULE_DATE";
        Map<String, Object> params = new HashMap<>();
        params.put("DOCTOR_ID", doctorSchdule.getDoctor().getId());
        params.put("SCHEDULE_DATE", doctorSchdule.getScheduleDate());

        this.namedParameterJdbcTemplate.update(sqlString, params);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ms_doctor_schedule");
        params.put("START_TIME", null);
        params.put("STOP_TIME", null);
        System.out.println(doctorSchdule);
        System.out.println("-----------------------------------");
        int rows = 0;
        for (ScheduleTime st : doctorSchdule.getScheduleTimeList()) {
            params.replace("START_TIME", st.getStartTime());
            params.replace("STOP_TIME", st.getStopTime());
            si.execute(params);
        }
        return rows;

    }

    @Override
    public List<ScheduleTime> getDoctorScheduleList(DoctorIdAndScheduleDate inputParams) {
        String sqlString = "SELECT ds.START_TIME, ds.STOP_TIME FROM ms_doctor_schedule ds where ds.DOCTOR_ID=:doctorId AND ds.SCHEDULE_DATE=:scheduleDate";
        Map<String, Object> params = new HashMap<>();

        params.put("doctorId", inputParams.getDoctorId());
        params.put("scheduleDate", inputParams.getScheduleDate());

        return this.namedParameterJdbcTemplate.query(sqlString, params, new ScheduleTimeRowMapper());

    }

    @Override
    public List<String> getDrAvailableSots(DoctorIdAndScheduleDate doctorSchedule) {
        String sqlString = "SELECT LEFT(ts.TIME_SLOT,5) `AVAILABLE_TIME_SLOT` "
                + "FROM ms_doctor_schedule ds  "
                + "LEFT JOIN ms_time_slots ts ON ts.TIME_SLOT >=ds.START_TIME AND ts.TIME_SLOT < ds.STOP_TIME  "
                + "LEFT JOIN ms_appointment a ON ds.DOCTOR_ID=a.DOCTOR_ID AND ds.SCHEDULE_DATE=a.SCHEDULE_DATE AND a.START_TIME=ts.TIME_SLOT "
                + "WHERE ds.DOCTOR_ID=:doctorId AND ds.SCHEDULE_DATE=:scheduleDate AND a.APPOINTMENT_ID IS NULL "
                + "ORDER BY ts.TIME_SLOT";
        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", doctorSchedule.getDoctorId());
        params.put("scheduleDate", doctorSchedule.getScheduleDate());

        return this.namedParameterJdbcTemplate.queryForList(sqlString, params, String.class);

    }

    @Override
    public List<DoctorCalendar> getDoctorCalendar(DoctorIdAndScheduleDate doctorSchedule) {
        String sqlString = "SELECT LEFT(ts.TIME_SLOT,5) `TIME_SLOT`, a.APPOINTMENT_ID, p.PATIENT_ID, p.NAME "
                + "FROM ms_doctor_schedule ds "
                + "LEFT JOIN ms_time_slots ts ON ts.TIME_SLOT >=ds.START_TIME AND ts.TIME_SLOT < ds.STOP_TIME "
                + "LEFT JOIN ms_appointment a ON ds.DOCTOR_ID=a.DOCTOR_ID AND ds.SCHEDULE_DATE=a.SCHEDULE_DATE AND a.START_TIME=ts.TIME_SLOT "
                + "LEFT JOIN ms_patient p ON a.PATIENT_ID=p.PATIENT_ID "
                + "WHERE ds.DOCTOR_ID=:doctorId AND ds.SCHEDULE_DATE=:scheduleDate "
                + "ORDER BY ts.TIME_SLOT";

        Map<String, Object> params = new HashMap<>();
        System.out.println(doctorSchedule);
        params.put("doctorId", doctorSchedule.getDoctorId());
        params.put("scheduleDate", doctorSchedule.getScheduleDate());
        System.out.println(this.namedParameterJdbcTemplate.query(sqlString, params, new DoctorCalendarRowMapper()));
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DoctorCalendarRowMapper());

    }

}
