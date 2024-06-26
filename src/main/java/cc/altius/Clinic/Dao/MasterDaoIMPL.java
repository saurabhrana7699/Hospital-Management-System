/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Dao.MasterDao;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.RowMapper.IdAndLabelRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class MasterDaoIMPL implements MasterDao {

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
    public List<IdAndLabel> getSpecilityList() {
        return this.jdbcTemplate.query("SELECT s.SPECIALITY_ID `ID` , s.SPECIALITY_DESC `LABEL` FROM ms_speciality s", new IdAndLabelRowMapper());

    }

    @Override
    public List<IdAndLabel> getRoleList() {
        return this.jdbcTemplate.query("SELECT r.ROLE_ID `ID`, r.ROLE_DESC `LABEL` FROM us_role r", new IdAndLabelRowMapper());
    }

}
