/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author altius
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Appointment implements Serializable {

    @EqualsAndHashCode.Include
    private int appointmentId;
    private IdAndLabel patient;
    private IdAndLabel doctor;
    private String scheduleDate;
    private String startTime;
    private IdAndLabel appointmentStatus;
    private IdAndLabel createdBy;
    private Date createData;
    private IdAndLabel lastmodifiedBy;
    private Date lastModifiedDate;

}
