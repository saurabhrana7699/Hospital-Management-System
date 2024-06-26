/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Dao.AppointmentDao;
import cc.altius.Clinic.Model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class AppointmentServiceImpl implements AppointmentService{
    
   @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public int addAppointment(Appointment apt) {
      return this.appointmentDao.addAppointment(apt);
    
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
      return this.appointmentDao.getAppointmentById(appointmentId);
    
    }

    @Override
    public int editAppointment(Appointment apt) {
      return this.appointmentDao.editAppointment(apt);
    }
   
   
    
    
    
}
