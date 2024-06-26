/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.RowMapper;

import cc.altius.Clinic.Model.DoctorCalendar;
import cc.altius.Clinic.Model.IdAndLabel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class DoctorCalendarRowMapper implements RowMapper<DoctorCalendar> {

    @Override
    public DoctorCalendar mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        DoctorCalendar dc = new DoctorCalendar();

        dc.setTimeSlot(rs.getString("TIME_SLOT"));
        dc.setAppointmentId(rs.getInt("APPOINTMENT_ID"));

        if (rs.wasNull()) {

            dc.setAppointmentId(null);
            dc.setPatient(null);
        } else {
            dc.setPatient(new IdAndLabel(rs.getString("PATIENT_ID"), rs.getString("NAME")));
        }
        return dc;

    }
}
