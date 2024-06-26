/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Model.DoctorCalendar;
import cc.altius.Clinic.Model.DoctorIdAndScheduleDate;
import cc.altius.Clinic.Model.DoctorSchedule;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.ScheduleTime;
import java.util.List;

/**
 *
 * @author altius
 */
public interface DoctorService {

    public List<IdAndLabel> getDoctorList();

    public DoctorSchedule getDoctorSchedule(int doctorId, String scheduleDate);

    public int addDoctorSchedule(DoctorSchedule doctorSchedule);

    public List<ScheduleTime> getDoctorScheduleList(DoctorIdAndScheduleDate inputParams);

    public List<String> getDrAvailableSots(DoctorIdAndScheduleDate doctorSchedule);

    public List<DoctorCalendar> getDoctorCalendar(DoctorIdAndScheduleDate doctorSchedule);

}
