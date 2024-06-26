/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Model.Appointment;
import cc.altius.Clinic.Model.User;
import cc.altius.Clinic.RowMapper.AppointmentRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    private String appointmentString = "SELECT "
            + "    a.APPOINTMENT_ID, "
            + "    p.PATIENT_ID, p.NAME `PATIENT_NAME`, "
            + "    d.DOCTOR_ID, du.NAME `DOCTOR_NAME`, "
            + "    a.SCHEDULE_DATE, a.START_TIME, "
            + "    s.APPOINTMENT_STATUS_ID, s.APPOINTMENT_STATUS_DESC, "
            + "    cb.USER_ID `CB_USER_ID`, cb.NAME `CB_NAME`, a.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.NAME `LMB_NAME`, a.LAST_MODIFIED_DATE "
            + "FROM ms_appointment a "
            + "LEFT JOIN ms_patient p ON a.PATIENT_ID=p.PATIENT_ID "
            + "LEFT JOIN ms_doctor d ON a.DOCTOR_ID=d.DOCTOR_ID "
            + "LEFT JOIN us_user du ON d.USER_ID=du.USER_ID "
            + "LEFT JOIN ms_appointment_status s ON a.APPOINTMENT_STATUS_ID=s.APPOINTMENT_STATUS_ID "
            + "LEFT JOIN us_user cb ON a.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON a.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    public int addAppointment(Appointment apt) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ms_appointment")
                .usingGeneratedKeyColumns("APPOINTMENT_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("PATIENT_ID", apt.getPatient().getId());
        params.put("DOCTOR_ID", apt.getDoctor().getId());
        params.put("SCHEDULE_DATE", apt.getScheduleDate());
        params.put("START_TIME", apt.getStartTime());
        params.put("APPOINTMENT_STATUS_ID", 1);
        User curUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_DATE", curDate);

        return si.executeAndReturnKey(params).intValue();

    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String sqlString = this.appointmentString + "AND a.APPOINTMENT_ID=:appointmentId";
        Map<String, Object> params = new HashMap<>();
        params.put("appointmentId", appointmentId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new AppointmentRowMapper());

    }

    @Override
    public int editAppointment(Appointment apt) {
        String sqlString = "UPDATE ms_appointment a SET a.START_TIME=:startTime, a.DOCTOR_ID=:doctorId, a.SCHEDULE_DATE=:scheduleDate,"
                + " a.LAST_MODIFIED_DATE=:curDate, a.LAST_MODIFIED_BY=:curUser WHERE a.APPOINTMENT_ID=:appointmentId";
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", apt.getStartTime());
        params.put("appointmentId", apt.getAppointmentId());
        params.put("doctorId", apt.getDoctor().getId());
        params.put("scheduleDate", apt.getScheduleDate());
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.IST));
        params.put("curUser", ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

}
