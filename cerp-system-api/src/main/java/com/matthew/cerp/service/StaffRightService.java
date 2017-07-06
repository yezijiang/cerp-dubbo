package com.matthew.cerp.service;


import com.matthew.cerp.entity.StaffRight;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 14:22
 */
public interface StaffRightService {

    List<StaffRight> qryStaffRight(String staffId);

    void regisStaffRight(Map<String, Object> param);

    void delStaffRight(String staffId);

    void batchInsertStaffRight(String staffId, String[] rightIds);
}
