package com.matthew.cerp.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 17:57
 */
public class StaffRight implements Serializable {
    private static final long serialVersionUID = 4176284342794348619L;

    private String staffRightId; //关系对ID
    private String staffId;
    private String rightId;
    private String remark;

    public String getStaffRightId() {
        return staffRightId;
    }

    public void setStaffRightId(String staffRightId) {
        this.staffRightId = staffRightId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getRightId() {
        return rightId;
    }

    public void setRightId(String rightId) {
        this.rightId = rightId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StaffRight{" +
                "staffRightId='" + staffRightId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", rightId='" + rightId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
