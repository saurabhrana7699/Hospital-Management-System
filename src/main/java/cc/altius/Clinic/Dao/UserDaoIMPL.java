/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Dao.UserDao;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.User;
import cc.altius.Clinic.RowMapper.UserRowMapper;
import cc.altius.Clinic.Service.MasterService;
import cc.altius.utils.PassPhrase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class UserDaoIMPL implements UserDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MasterService masterService;
    private List<IdAndLabel> specialityList;

    @Autowired
    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
        this.specialityList = this.masterService.getSepcialityList();
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String userSql = "SELECT u1.* FROM (SELECT "
            + "u.USER_ID, u.NAME, u.USERNAME, u.PASSWORD, u.ACTIVE, r.ROLE_ID, r.ROLE_DESC, d.SPECIALITY_IDS, " +
            "GROUP_CONCAT(rbf.BUSINESS_FUNCTION_ID) `BUSINESS_FUNCTION_IDS` "
            + "FROM us_user u "
            + "LEFT JOIN us_role r ON u.ROLE_ID=r.ROLE_ID "
            + "LEFT JOIN us_role_business_function rbf ON r.ROLE_ID=rbf.ROLE_ID "
            + "LEFT JOIN ms_doctor d ON u.USER_ID=d.USER_ID "
            + "group by u.USER_ID , d.SPECIALITY_IDS) AS u1 WHERE TRUE ";

    @Override
    public List<User> getUserList() {
        return this.jdbcTemplate.query(this.userSql, new UserRowMapper(this.specialityList));
    }

    @Override
    @Transactional
    public int addUser(User user) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("us_user").usingGeneratedKeyColumns("USER_ID");
        Map<String, Object> params = new HashMap<>();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        params.put("NAME", user.getName());
        params.put("USERNAME", user.getUsername());
        String password = PassPhrase.getPassword(8);
        System.out.println("Password:" + password);
        params.put("PASSWORD", encoder.encode(password));
        params.put("ROLE_ID", user.getRole().getId());
        params.put("ACTIVE", true);

        int userId = si.executeAndReturnKey(params).intValue();

        if (user.getRole().getId().equals("ROLE_DOCTOR")) {
            // Create Doctor
            SimpleJdbcInsert sd = new SimpleJdbcInsert(dataSource).withTableName("ms_doctor");
            params.clear();
            params.put("USER_ID", userId);
            params.put("SPECIALITY_IDS", user.getSpecialityList().stream().map(IdAndLabel::getId).collect(Collectors.joining(",")));
            sd.execute(params);
        }
        return userId;

    }

    @Override
    public User getUserByUserId(int userId) {
        String sqlString = this.userSql + "AND u1.USER_ID=:userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new UserRowMapper(this.specialityList));

    }

    @Override
    public int editUser(User user) {
        String sqlString = "UPDATE us_user u "
                + "LEFT JOIN ms_doctor d ON u.USER_ID=d.USER_ID "
                + "SET "
                + "u.NAME=:name, "
                + "u.USERNAME=:username, "
                + "d.SPECIALITY_IDS=:specialityIds "
                + "WHERE u.USER_ID=:userId";
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("username", user.getUsername());
        params.put("specialityIds", user.getSpecialityListIds());
        params.put("userId", user.getUserId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public User loadUserByUsername(String username) {
        String sqlString = this.userSql + " AND u1.USERNAME=:username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new UserRowMapper(this.specialityList));

    }

}
