/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Model.Patient;
import java.util.List;

/**
 *
 * @author altius
 */
public interface PatientService {
    
     public int addPatient(Patient patient);
    
    public List<Patient> findPatient(String searchString);
    
    public List<Patient> listPatient();
    
    public Patient getPatientById(int patientId);
    
    public int editPatient(Patient patient);
    
}
