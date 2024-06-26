/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Dao.PatientDao;
import cc.altius.Clinic.Model.Patient;
import cc.altius.Clinic.RowMapper.PatientRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class PatientDaoIMPL implements PatientDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    private String patientString = "SELECT p.PATIENT_ID, p.NAME, p.MOBILE, p.EMAIL_ID, p.AGE, p.GENDER FROM ms_patient p WHERE TRUE";

    @Override
    public int addPatient(Patient patient) {

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ms_patient").usingGeneratedKeyColumns("PATIENT_ID");
        Map<String, Object> params = new HashMap<>();

        params.put("NAME", patient.getName());
        params.put("MOBILE", patient.getMobile());
        params.put("EMAIL_ID", patient.getEmailId());
        params.put("AGE", patient.getAge());
        params.put("GENDER", patient.getGender().getId());

        return si.executeAndReturnKey(params).intValue();

    }

    @Override
    public List<Patient> findPatient(String searchString) {
        String sqlString = this.patientString + " AND p.NAME LIKE CONCAT('%',:searchString,'%')";
        Map<String, Object> params = new HashMap<>();
        params.put("searchString", searchString);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new PatientRowMapper());

    }

    @Override
    public List<Patient> listPatient() {
        return this.jdbcTemplate.query(this.patientString, new PatientRowMapper());

    }

    @Override
    public Patient getPatientById(int patientId) {
        String sqlString = this.patientString + " AND p.PATIENT_ID=:patientId";
        Map<String, Object> params = new HashMap<>();
        params.put("patientId", patientId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new PatientRowMapper());

    }

    @Override
    public int editPatient(Patient patient) {
        String sqlString = "UPDATE ms_patient p SET p.NAME=:name, p.MOBILE=:mobile, p.EMAIL_ID=:emailId, p.AGE=:age, p.GENDER=:gender WHERE p.PATIENT_ID=:patientId";
        Map<String, Object> params = new HashMap<>();
        params.put("name", patient.getName());
        params.put("mobile", patient.getMobile());
        params.put("emailId", patient.getEmailId());
        params.put("age", patient.getAge());
        params.put("gender", patient.getGender().getId());
        params.put("patientId", patient.getPatientId());

        return this.namedParameterJdbcTemplate.update(sqlString, params);

    }

}
