/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.RowMapper;

import cc.altius.Clinic.Model.ScheduleTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ScheduleTimeRowMapper  implements RowMapper<ScheduleTime>{

    @Override
    public ScheduleTime mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new ScheduleTime(rs.getString("START_TIME"),rs.getString("STOP_TIME"));
      
    }
    
    
    
}
