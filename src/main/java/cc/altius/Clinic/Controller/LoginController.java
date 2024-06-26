/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Controller;

import cc.altius.Clinic.Service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author altius
 */
@Controller
public class LoginController {
    
//    @Autowired
//    private MasterService masterService;
    
    
    @RequestMapping(value = "/")
    public String showRoot(){
        return "redirect:/index.htm";
    }
    
    @RequestMapping("/login.htm")
    public String showLoginPage(){
        return "login";
    }
    
    @RequestMapping("/index.htm")
    public String showIndex(){
        return "index";
    }
}
