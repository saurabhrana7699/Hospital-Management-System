/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Controller;

import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.Patient;
import cc.altius.Clinic.Model.PropertyEditor.IdAndLabelPropertyEditor;
import cc.altius.Clinic.Service.MasterService;
import cc.altius.Clinic.Service.PatientService;
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
public class PatientController {

    @Autowired
    private PatientService patientSerivce;
    @Autowired
    private MasterService masterService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(IdAndLabel.class, new IdAndLabelPropertyEditor());
    }

    @RequestMapping(value = "addPatient.htm", method = RequestMethod.GET)
    public String showAddPatient(ModelMap model) {

        model.addAttribute("patient", new Patient());
        model.addAttribute("genderList", this.masterService.getGenderList());
        return "addPatient";
    }

    @RequestMapping(value = "addPatient.htm", method = RequestMethod.POST)
    public String addPatient(@ModelAttribute("patient") Patient patient, ModelMap model) {
        try {
            int patientId = this.patientSerivce.addPatient(patient);
            return "redirect:listPatient.htm?msg=Patient added successfully" + " " + patientId;
        } catch (Exception e) {
            model.addAttribute("patient", patient);
            model.addAttribute("genderList", this.masterService.getGenderList());
            model.addAttribute("msg", "Failed to add Patient - " + e.getMessage());
            return "addPatient";
        }
    }

    @RequestMapping(value = "listPatient.htm", method = RequestMethod.GET)
    public String listPatient(ModelMap model) {
        model.addAttribute("patientList", this.patientSerivce.listPatient());
        return "listPatient";
    }

    @RequestMapping(value = "searchPatient.htm", method = RequestMethod.GET)
    public String searchPatient(@RequestParam(value = "searchString", required = false, defaultValue = "") String searchString, ModelMap model) {
        System.out.println("searchString=" + searchString);
        if (!searchString.isBlank()) {
            model.addAttribute("searchString", searchString);
            List<Patient> patientList = this.patientSerivce.findPatient(searchString);
            System.out.println("patientList=" + patientList.size());
            model.addAttribute("patientList", patientList);

        }
        return "searchPatient";
    }

    @RequestMapping(value = "showEditPatient.htm", method = RequestMethod.POST)
    public String showEditPatient(@RequestParam(value = "patientId", required = true) int patientId, ModelMap model) {
        model.addAttribute("patient", this.patientSerivce.getPatientById(patientId));
        model.addAttribute("genderList", this.masterService.getGenderList());
        return "editPatient";
    }

    @RequestMapping(value = "editPatient.htm", method = RequestMethod.POST)
    public String editPatient(@ModelAttribute("patient") Patient patient, ModelMap model) {
        try {
            int rows = this.patientSerivce.editPatient(patient);
            if (rows == 0) {
                return "redirect:listPatient.htm?msg=Nothing to update" + " " + patient.getPatientId();
            } else {
                return "redirect:listPatient.htm?msg=Patient update successfully   " + patient.getPatientId();
            }
        } catch (Exception e) {
            model.addAttribute("patient", patient);
            model.addAttribute("genderList", this.masterService.getGenderList());
            model.addAttribute("msg", "Failed to update Patient - " + e.getMessage());
            return "editPatient";
        }

    }

}
