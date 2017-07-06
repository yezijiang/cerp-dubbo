package com.matthew.cerp.entity;

 /**员工角色信息类
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 14:08
 */
public class StaffRole {

	private int staffRoleId;//员工权限编号
	private String staffId;//员工编号
	private String roleId;//角色编号
	private String remark;//备注
	private String rsrvStr1;//扩展字段1
	private String rsrvStr2;//扩展字段2
	private String rsrvStr3;//扩展字段3
	private String rsrvStr4;//扩展字段4
	private String rsrvStr5;//扩展字段5
	
	public int getStaffRoleId() {
		return staffRoleId;
	}
	public void setStaffRoleId(int staffRoleId) {
		this.staffRoleId = staffRoleId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRsrvStr1() {
		return rsrvStr1;
	}
	public void setRsrvStr1(String rsrvStr1) {
		this.rsrvStr1 = rsrvStr1;
	}
	public String getRsrvStr2() {
		return rsrvStr2;
	}
	public void setRsrvStr2(String rsrvStr2) {
		this.rsrvStr2 = rsrvStr2;
	}
	public String getRsrvStr3() {
		return rsrvStr3;
	}
	public void setRsrvStr3(String rsrvStr3) {
		this.rsrvStr3 = rsrvStr3;
	}
	public String getRsrvStr4() {
		return rsrvStr4;
	}
	public void setRsrvStr4(String rsrvStr4) {
		this.rsrvStr4 = rsrvStr4;
	}
	public String getRsrvStr5() {
		return rsrvStr5;
	}
	public void setRsrvStr5(String rsrvStr5) {
		this.rsrvStr5 = rsrvStr5;
	}
	
	
}
