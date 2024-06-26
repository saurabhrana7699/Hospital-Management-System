/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Model.User;
import java.util.List;

/**
 *
 * @author altius
 */
public interface UserService {

    public List<User> getUserList();

    public int addUser(User user);

    public User getUserById(int userId);

    public int editUser(User user);

}
