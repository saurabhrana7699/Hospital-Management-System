/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Dao.MasterDao;
import cc.altius.Clinic.Dao.PatientDao;
import cc.altius.Clinic.Model.Patient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class PatientServiceIMPL implements PatientService {

    @Autowired
    private MasterDao masterDao;

    @Autowired
    private PatientDao patidentDao;

    @Override
    public int addPatient(Patient patient) {
        return this.patidentDao.addPatient(patient);

    }

    @Override
    public List<Patient> findPatient(String searchString) {
        return this.patidentDao.findPatient(searchString);

    }

    @Override
    public List<Patient> listPatient() {
        return this.patidentDao.listPatient();
    }

    @Override
    public Patient getPatientById(int patientId) {
        return this.patidentDao.getPatientById(patientId);
    }

    @Override
    public int editPatient(Patient patient) {

        return this.patidentDao.editPatient(patient);
    }

}
