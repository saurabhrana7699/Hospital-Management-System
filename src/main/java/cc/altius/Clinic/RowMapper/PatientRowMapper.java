/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.RowMapper;

import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PatientRowMapper implements RowMapper<Patient> {

    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Patient(rs.getInt("PATIENT_ID"), rs.getString("NAME"),
                rs.getString("MOBILE"), rs.getNString("EMAIL_ID"),
                rs.getInt("AGE"), new IdAndLabel(rs.getString("GENDER"), rs.getString("GENDER").equals("M")
                ? "Male" : "Female"));
        
    }

}
