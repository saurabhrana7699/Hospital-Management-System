/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Dao.DoctorDao;
import cc.altius.Clinic.Model.DoctorCalendar;
import cc.altius.Clinic.Model.DoctorIdAndScheduleDate;
import cc.altius.Clinic.Model.DoctorSchedule;
import cc.altius.Clinic.Model.IdAndLabel;
import cc.altius.Clinic.Model.ScheduleTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class DoctorServiceIMPL implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public List<IdAndLabel> getDoctorList() {
        return this.doctorDao.getDoctorList();
    }

    @Override
    public DoctorSchedule getDoctorSchedule(int doctorId, String scheduleDate) {
        return this.doctorDao.getDoctorSchedule(doctorId, scheduleDate);
    }

    @Override
    public int addDoctorSchedule(DoctorSchedule doctorSchedule) {
        return this.doctorDao.addDoctorSchedule(doctorSchedule);
    }

    @Override
    public List<ScheduleTime> getDoctorScheduleList(DoctorIdAndScheduleDate inputParams) {

        return this.doctorDao.getDoctorScheduleList(inputParams);
    }

    @Override
    public List<String> getDrAvailableSots(DoctorIdAndScheduleDate doctorSchedule) {
        return this.doctorDao.getDrAvailableSots(doctorSchedule);
    }

    @Override
    public List<DoctorCalendar> getDoctorCalendar(DoctorIdAndScheduleDate doctorSchedule) {
        return this.doctorDao.getDoctorCalendar(doctorSchedule);
    }

}
