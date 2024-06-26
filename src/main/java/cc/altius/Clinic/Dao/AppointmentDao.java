/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Model.Appointment;

/**
 *
 * @author altius
 */
public interface AppointmentDao {
    
    public int addAppointment(Appointment apt);
    
    public Appointment getAppointmentById(int appointmentId);
    
    public int editAppointment(Appointment apt);
    
}
