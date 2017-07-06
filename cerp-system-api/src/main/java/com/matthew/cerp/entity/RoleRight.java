package com.matthew.cerp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-29 11:49
 */
public class RoleRight implements Serializable {

	private static final long serialVersionUID = -3572012779032420153L;
	
	private String id;
	private String name;
	private String rightCode;
	private String rightType;
	private String rightLevel;
	private String parentRightId;
	private String roleId; //批量添加是roleId
	private String rightId;//批量添加是rightId
	private String reamark;//批量添加是remark
	//private boolean checked;
	private List<RoleRight> children;
	
	public String getNodeId() {
		return id;
	}
	
//	public boolean isChecked() {
//		return checked;
//	}

//	public void setChecked(boolean checked) {
//		this.checked = true;
//	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRightCode() {
		return rightCode;
	}
	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRightId() {
		return rightId;
	}
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	public String getReamark() {
		return reamark;
	}
	public void setReamark(String reamark) {
		this.reamark = reamark;
	}
	public String getRightLevel() {
		return rightLevel;
	}
	public void setRightLevel(String rightLevel) {
		this.rightLevel = rightLevel;
	}
	public String getParentRightId() {
		return parentRightId;
	}
	public void setParentRightId(String parentRightId) {
		this.parentRightId = parentRightId;
	}
	public List<RoleRight> getChildren() {
		return children;
	}
	public void setChildren(List<RoleRight> children) {
		this.children = children;
	}
	
}
