/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Model.DoctorCalendar;
import cc.altius.Clinic.Model.DoctorIdAndScheduleDate;
import cc.altius.Clinic.Model.DoctorSchedule;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.ScheduleTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author altius
 */
public interface DoctorDao {
    
     public List<IdAndLabel> getDoctorList();
    
    public DoctorSchedule getDoctorSchedule(int doctorId , String  scheduleDate);
    
    public int addDoctorSchedule(DoctorSchedule doctorSchdule);
    
    public List<ScheduleTime> getDoctorScheduleList(DoctorIdAndScheduleDate inputParams);
    
    public List<String> getDrAvailableSots(DoctorIdAndScheduleDate doctorSchedule);
    
    public List<DoctorCalendar> getDoctorCalendar(DoctorIdAndScheduleDate doctorSchedule);
    
   
    
    
}
