/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Model.IdAndLabel;
import java.util.List;

/**
 *
 * @author altius
 */
public interface MasterService {
    
     public List<IdAndLabel> getSepcialityList();

    public List<IdAndLabel> getRoleList();

    public List<IdAndLabel> getGenderList();
    
}
