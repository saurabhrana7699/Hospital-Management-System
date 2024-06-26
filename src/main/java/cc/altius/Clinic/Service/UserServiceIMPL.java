/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Dao.UserDao;
import cc.altius.Clinic.Model.User;
import cc.altius.Clinic.Service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class UserServiceIMPL implements UserService, UserDetailsService{
    
    @Autowired
    private UserDao userdao;

    @Override
    public List<User> getUserList() {
      return this.userdao.getUserList();
    }

    @Override
    public int addUser(User user) {
    return this.userdao.addUser(user);
    }

    @Override
    public User getUserById(int userId) {
    return this.userdao.getUserByUserId(userId);
    }

    @Override
    public int editUser(User user) {
    return this.userdao.editUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userdao.loadUserByUsername(username);
    if(user ==null){
        
    throw new UsernameNotFoundException("User Not Found");
    
    }
    else {
        return user;
    }

    
    }  
}
