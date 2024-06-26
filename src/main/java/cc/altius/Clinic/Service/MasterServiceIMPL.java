/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.Clinic.Service;

import cc.altius.Clinic.Dao.MasterDao;
import cc.altius.Clinic.Model.IdAndLabel;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class MasterServiceIMPL implements MasterService {

    private final List<IdAndLabel> genderList;

    @Autowired
    private MasterDao masterDao;

    public MasterServiceIMPL() {
        this.genderList = new LinkedList<>();
        genderList.add(new IdAndLabel("M", "Male"));
        genderList.add(new IdAndLabel("F", "Female"));

    }

    @Override
    public List<IdAndLabel> getSepcialityList() {
        return this.masterDao.getSpecilityList();
    }

    @Override
    public List<IdAndLabel> getRoleList() {
        return this.masterDao.getRoleList();
    }

    @Override
    public List<IdAndLabel> getGenderList() {
        return this.genderList;
    }

}
