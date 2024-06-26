/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.RowMapper;

import cc.altius.Clinic.Model.IdAndLabel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class IdAndLabelRowMapper  implements RowMapper<IdAndLabel>{

    @Override
    public IdAndLabel mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new IdAndLabel(rs.getString("ID"),rs.getString("LABEL"));
    }

    
    
    
}
