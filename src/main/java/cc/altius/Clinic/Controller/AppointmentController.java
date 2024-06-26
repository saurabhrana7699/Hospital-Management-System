/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Controller;

import cc.altius.Clinic.Model.Appointment;
import cc.altius.Clinic.Model.DoctorIdAndScheduleDate;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.PropertyEditor.IdAndLabelPropertyEditor;
import cc.altius.Clinic.Service.AppointmentService;
import cc.altius.Clinic.Service.DoctorService;
import cc.altius.Clinic.Service.MasterService;
import cc.altius.Clinic.Service.PatientService;
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
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private PatientService patientService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IdAndLabel.class, new IdAndLabelPropertyEditor());
    }

    @RequestMapping(value = "addAppointment.htm", method = RequestMethod.GET)
    public String showAddAppointment(ModelMap model) {
        model.addAttribute("doctorList", this.doctorService.getDoctorList());
        model.addAttribute("patientList", this.patientService.listPatient());
        model.addAttribute("availableSlots", new LinkedList<>());
        model.addAttribute("appointment", new Appointment());

        return "addAppointment";

    }

    @RequestMapping(value = "addAppointment.htm", method = RequestMethod.POST)
    public String addAppointment(@ModelAttribute("appointment") Appointment appointment, ModelMap model) {
        try {
            int appointmentId = this.appointmentService.addAppointment(appointment);
            return "redirect:index.htm?msg=Appointment added successfully";
        } catch (Exception e) {
            model.addAttribute("doctorList", this.doctorService.getDoctorList());
            model.addAttribute("patientList", this.patientService.listPatient());
            model.addAttribute("appointment", new Appointment());
            model.addAttribute("msg", "Appointment could not be added - " + e.getMessage());
            return "addAppointment";
        }
    }

    @RequestMapping(value = "addAppointmentFromCalendar.htm", method = RequestMethod.POST)
    public String showAddaddAppointmentFromCalendar(
            @RequestParam(value = "doctorId", required = true) String doctorId,
            @RequestParam(value = "scheduleDate", required = true) String scheduleDate,
            @RequestParam(value = "timeSlot", required = true) String timeSlot, ModelMap model
    ) {
        System.out.println("scheduleDate=" + scheduleDate);
        System.out.println("timeSlot=" + timeSlot);
        model.addAttribute("doctorList", this.doctorService.getDoctorList());
        model.addAttribute("patientList", this.patientService.listPatient());

        List<String> availableSlots = this.doctorService.getDrAvailableSots(new DoctorIdAndScheduleDate(Integer.parseInt(doctorId), scheduleDate));
        System.out.println("availableSlots = " + availableSlots);
        model.addAttribute("availableSlots", availableSlots);

        Appointment apt = new Appointment();
        apt.setDoctor(new IdAndLabel(doctorId, ""));
        apt.setScheduleDate(scheduleDate);
        apt.setStartTime(timeSlot);
        model.addAttribute("appointment", apt);
        return "addAppointment";
    }



    @RequestMapping(value = "viewDoctorCalendar.htm", method = RequestMethod.GET)
    public String viewDoctorCalendar(ModelMap model) {
        model.addAttribute("doctorList", this.doctorService.getDoctorList());
//        System.out.println(this.doctorService.getDoctorList());
        System.out.println("==============================================================================");
        return "viewDoctorCalendar";
    }

    @RequestMapping(value = "showEditAppointment.htm", method = RequestMethod.POST)
    public String showEditAppointment(@RequestParam(value = "appointmentId", required = true) int appointmentId, ModelMap model) {
        model.addAttribute("doctorList", this.doctorService.getDoctorList());
        Appointment apt = this.appointmentService.getAppointmentById(appointmentId);
        List<String> availableSlots = new LinkedList<>();
        availableSlots.add(apt.getStartTime());
        availableSlots.addAll(this.doctorService.getDrAvailableSots(new DoctorIdAndScheduleDate(Integer.parseInt(apt.getDoctor().getId()), apt.getScheduleDate())));
        model.addAttribute("availableSlots", availableSlots);
        model.addAttribute("appointment", apt);
        return "editAppointment";
    }

    @RequestMapping(value = "editAppointment.htm", method = RequestMethod.POST)
    public String editAppointment(@ModelAttribute("appointment") Appointment appointment, ModelMap model) {
        try {
            int appointmentId = this.appointmentService.editAppointment(appointment);
            return "redirect:viewDoctorCalendar.htm?msg=Appointment updated successfully";
        } catch (Exception e) {
            model.addAttribute("doctorList", this.doctorService.getDoctorList());
            model.addAttribute("appointment", appointment);
            model.addAttribute("msg", "Appointment could not Updated - " + patientService);
//            System.out.println();
            e.printStackTrace();
            return "editAppointment";
        }
    }

}
