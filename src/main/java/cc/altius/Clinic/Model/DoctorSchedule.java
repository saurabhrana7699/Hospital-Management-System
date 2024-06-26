/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author altius
 */
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class DoctorSchedule implements Serializable{
    
    private IdAndLabel doctor;
    
    private String scheduleDate;
    
    private List<ScheduleTime> scheduleTimeList = new LinkedList<>();
    
    
    
}
