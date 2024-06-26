/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.RowMapper;

import cc.altius.Clinic.Model.Appointment;
import cc.altius.Clinic.Model.IdAndLabel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class AppointmentRowMapper implements RowMapper<Appointment> {

    @Override
    public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Appointment(
                rs.getInt("APPOINTMENT_ID"),
                new IdAndLabel(rs.getString("PATIENT_ID"), rs.getString("PATIENT_NAME")),
                new IdAndLabel(rs.getString("DOCTOR_ID"), rs.getString("DOCTOR_NAME")),
                rs.getString("SCHEDULE_DATE"),
                rs.getString("START_TIME"),
                new IdAndLabel(rs.getString("APPOINTMENT_STATUS_ID"), rs.getString("APPOINTMENT_STATUS_DESC")),
                new IdAndLabel(rs.getString("CB_USER_ID"), rs.getString("CB_NAME")),
                rs.getTimestamp("CREATED_DATE"),
                new IdAndLabel(rs.getString("LMB_USER_ID"), rs.getString("LMB_NAME")),
                rs.getTimestamp("LAST_MODIFIED_DATE") );

    }

}
