/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.User;
import java.util.List;

/**
 *
 * @author altius
 */
public interface UserDao {

    public List<User> getUserList();

    public int addUser(User user);

    public User getUserByUserId(int userId);

    public int editUser(User user);

    public User loadUserByUsername(String username);
    
    

}
