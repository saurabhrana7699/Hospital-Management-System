/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.RowMapper;

import cc.altius.Clinic.Model.DoctorSchedule;
import cc.altius.Clinic.Model.IdAndLabel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class DoctorScheduleRowMapper implements RowMapper<DoctorSchedule>{

    @Override
    public DoctorSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
       return new DoctorSchedule(
          new IdAndLabel(  rs.getString("DOCTOR_ID"),rs.getString("DOCTOR_NAME")),
       rs.getString("SCHEDULE_DATE"),null);   
    
    }
    
    
    
}

	