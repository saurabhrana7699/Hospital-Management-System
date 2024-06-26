/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Controller;

import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.PropertyEditor.IdAndLabelPropertyEditor;
import cc.altius.Clinic.Model.User;
import cc.altius.Clinic.Service.MasterService;
import cc.altius.Clinic.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author altius
 */
@Controller
public class UserController {

    @Autowired
    private MasterService masterService;

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IdAndLabel.class, new IdAndLabelPropertyEditor());
    }

    @RequestMapping(value = "addUser.htm", method = RequestMethod.GET)
    public String showAddUser(ModelMap model) {
        model.addAttribute("roleList", this.masterService.getRoleList());
        model.addAttribute("specialityList", this.masterService.getSepcialityList());
        model.addAttribute("user", new User());
        return "addUser";
    }

    @RequestMapping(value = "addUser.htm", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, ModelMap model) {
        try {
            int userId = this.userService.addUser(user);
            return "redirect:listUser.htm?msg=User Id " + userId + " added successfully";
        } catch (Exception e) {
            model.addAttribute("roleList", this.masterService.getRoleList());
            model.addAttribute("specialityList", this.masterService.getSepcialityList());
            model.addAttribute("user", user);
            model.addAttribute("msg", "User could not be added - " + e.getMessage());
            return "addUser";
        }
    }

    @RequestMapping(value = "listUser.htm", method = RequestMethod.GET)
    public String listUser(ModelMap model) {
        model.addAttribute("userList", this.userService.getUserList());
        return "listUser";
    }

    @RequestMapping(value = "showEditUser.htm", method = RequestMethod.POST)
    public String showEditUser(@RequestParam(value = "userId", required = true) int userId, ModelMap model) {
        model.addAttribute("user", this.userService.getUserById(userId));
        model.addAttribute("roleList", this.masterService.getRoleList());
        model.addAttribute("specialityList", this.masterService.getSepcialityList());
        return "editUser";
    }

    @RequestMapping(value = "editUser.htm", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") User user, ModelMap model) {
        try {
            int rows = this.userService.editUser(user);
            if (rows == 0) {
                return "redirect:listUser.htm?msg=Nothing to update";
            } else {
                return "redirect:listUser.htm?msg=User successfully updated   "+user.getUserId();
            }
        } catch (Exception e) {
            model.addAttribute("user", user);
            model.addAttribute("roleList", this.masterService.getRoleList());
            model.addAttribute("specialityList", this.masterService.getSepcialityList());
            model.addAttribute("msg", "User could not be updated - " + e.getMessage());
            return "editUser";
        }
    }

}
