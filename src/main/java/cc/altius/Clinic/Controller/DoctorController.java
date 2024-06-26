/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Controller;

import cc.altius.Clinic.Model.DoctorSchedule;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.Patient;
import cc.altius.Clinic.Model.PropertyEditor.IdAndLabelPropertyEditor;
import cc.altius.Clinic.Model.ScheduleTime;
import cc.altius.Clinic.Service.DoctorService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.LinkedList;
import java.util.List;
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
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IdAndLabel.class, new IdAndLabelPropertyEditor());
    }

    @RequestMapping(value = "addDoctorSchedule.htm", method = RequestMethod.GET)
    public String showAddDoctorSchedule(ModelMap model) {
        model.addAttribute("doctorList", this.doctorService.getDoctorList());
        DoctorSchedule ds = new DoctorSchedule();
        ds.setScheduleDate(DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        model.addAttribute("doctorSchedule", ds);
        return "addDoctorSchedule";
    }
    
    

     @RequestMapping(value = "addDoctorSchedule.htm", method = RequestMethod.POST)
    public String addPatient(@ModelAttribute("doctorSchdule") DoctorSchedule doctorSchedule, @RequestParam(value = "scheduleJson", required = true) String scheduleJson, ModelMap model) {
        try {
            System.out.println("cc.altius.Clinic.Controller.DoctorController.addPatient()");
            Gson gson = new Gson();
            List<ScheduleTime> sheduleTimeList = gson.fromJson(scheduleJson, new TypeToken<LinkedList<ScheduleTime>>() {
            }.getType());
            doctorSchedule.setScheduleTimeList(sheduleTimeList);
            this.doctorService.addDoctorSchedule(doctorSchedule);
            System.out.println(doctorSchedule);
            return "redirect:addDoctorSchedule.htm?msg=Schedule update successfully";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("doctorList",this.doctorService.getDoctorList());
            model.addAttribute("defaultDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            model.addAttribute("doctorSchedule", new DoctorSchedule());
            model.addAttribute("msg", "Failed to add Doctor schedule - " + e.getMessage());
            return "addDoctorSchedule";
        }
    }

}
