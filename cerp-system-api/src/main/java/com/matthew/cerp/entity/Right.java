/**
 * 
 */
package com.matthew.cerp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-29 11:49
 */
public class Right implements Serializable {
	private static final long serialVersionUID = 215217783270417047L;
	private String rightId;// 权限Id
	private String rightType;// 权限类型:0-功能菜单权限 1-数据权限
	private String rightName;// 权限名称
	private String rightCode;// 权限编码
	private String rightUrl; //菜单url
	private String rightDesc;// 权限描述
	private String parentRightId;// 父权限ID
	private String rightLevel;// 权限等级
	private String orderBy; // 排序
	private String deletedFlag;// 删除标识：0--有效 1-无效
	private String createBy;// 创建人
	private Date createTime;// 创建时间
	private String updateBy;// 更新人
	private Date updateTime;// 更新时间
	private String remark;// 备注信息
	private String rsrvStr1;// 扩展字段1
	private String rsrvStr2;// 扩展字段2
	private String rsrvStr3;// 扩展字段3
	private String rsrvStr4;// 扩展字段4
	private Date rsrvStr5;// 扩展字段5
	private List<Right> children;
	
	public List<Right> getChildren() {
		return children;
	}

	public void setChildren(List<Right> children) {
		this.children = children;
	}

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public String getRightDesc() {
		return rightDesc;
	}

	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}

	public String getRightUrl() {
		return rightUrl;
	}

	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}

	public String getParentRightId() {
		return parentRightId;
	}

	public void setParentRightId(String parentRightId) {
		this.parentRightId = parentRightId;
	}

	public String getRightLevel() {
		return rightLevel;
	}

	public void setRightLevel(String rightLevel) {
		this.rightLevel = rightLevel;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDeletedFlag() {
		return deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public Date getRsrvStr5() {
		return rsrvStr5;
	}

	public void setRsrvStr5(Date rsrvStr5) {
		this.rsrvStr5 = rsrvStr5;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Right right = (Right) o;

		if (rightId != null ? !rightId.equals(right.rightId) : right.rightId != null) return false;
		if (rightType != null ? !rightType.equals(right.rightType) : right.rightType != null) return false;
		if (rightName != null ? !rightName.equals(right.rightName) : right.rightName != null) return false;
		if (rightCode != null ? !rightCode.equals(right.rightCode) : right.rightCode != null) return false;
		if (rightUrl != null ? !rightUrl.equals(right.rightUrl) : right.rightUrl != null) return false;
		if (rightDesc != null ? !rightDesc.equals(right.rightDesc) : right.rightDesc != null) return false;
		if (parentRightId != null ? !parentRightId.equals(right.parentRightId) : right.parentRightId != null)
			return false;
		if (rightLevel != null ? !rightLevel.equals(right.rightLevel) : right.rightLevel != null) return false;
		if (orderBy != null ? !orderBy.equals(right.orderBy) : right.orderBy != null) return false;
		if (deletedFlag != null ? !deletedFlag.equals(right.deletedFlag) : right.deletedFlag != null) return false;
		if (createBy != null ? !createBy.equals(right.createBy) : right.createBy != null) return false;
		if (createTime != null ? !createTime.equals(right.createTime) : right.createTime != null) return false;
		if (updateBy != null ? !updateBy.equals(right.updateBy) : right.updateBy != null) return false;
		if (updateTime != null ? !updateTime.equals(right.updateTime) : right.updateTime != null) return false;
		if (remark != null ? !remark.equals(right.remark) : right.remark != null) return false;
		if (rsrvStr1 != null ? !rsrvStr1.equals(right.rsrvStr1) : right.rsrvStr1 != null) return false;
		if (rsrvStr2 != null ? !rsrvStr2.equals(right.rsrvStr2) : right.rsrvStr2 != null) return false;
		if (rsrvStr3 != null ? !rsrvStr3.equals(right.rsrvStr3) : right.rsrvStr3 != null) return false;
		if (rsrvStr4 != null ? !rsrvStr4.equals(right.rsrvStr4) : right.rsrvStr4 != null) return false;
		if (rsrvStr5 != null ? !rsrvStr5.equals(right.rsrvStr5) : right.rsrvStr5 != null) return false;
		return children != null ? children.equals(right.children) : right.children == null;

	}

	@Override
	public int hashCode() {
		int result = rightId != null ? rightId.hashCode() : 0;
		result = 31 * result + (rightType != null ? rightType.hashCode() : 0);
		result = 31 * result + (rightName != null ? rightName.hashCode() : 0);
		result = 31 * result + (rightCode != null ? rightCode.hashCode() : 0);
		result = 31 * result + (rightUrl != null ? rightUrl.hashCode() : 0);
		result = 31 * result + (rightDesc != null ? rightDesc.hashCode() : 0);
		result = 31 * result + (parentRightId != null ? parentRightId.hashCode() : 0);
		result = 31 * result + (rightLevel != null ? rightLevel.hashCode() : 0);
		result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
		result = 31 * result + (deletedFlag != null ? deletedFlag.hashCode() : 0);
		result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateBy != null ? updateBy.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		result = 31 * result + (remark != null ? remark.hashCode() : 0);
		result = 31 * result + (rsrvStr1 != null ? rsrvStr1.hashCode() : 0);
		result = 31 * result + (rsrvStr2 != null ? rsrvStr2.hashCode() : 0);
		result = 31 * result + (rsrvStr3 != null ? rsrvStr3.hashCode() : 0);
		result = 31 * result + (rsrvStr4 != null ? rsrvStr4.hashCode() : 0);
		result = 31 * result + (rsrvStr5 != null ? rsrvStr5.hashCode() : 0);
		result = 31 * result + (children != null ? children.hashCode() : 0);
		return result;
	}
}
