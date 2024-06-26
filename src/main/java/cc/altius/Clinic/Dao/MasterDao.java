/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Dao;

import cc.altius.Clinic.Model.IdAndLabel;
import java.util.List;

/**
 *
 * @author altius
 */
public interface MasterDao {

    public List<IdAndLabel> getSpecilityList();

    public List<IdAndLabel> getRoleList();

}
