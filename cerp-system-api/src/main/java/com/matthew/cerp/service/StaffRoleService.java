package com.matthew.cerp.service;


import com.matthew.cerp.entity.StaffRole;

import java.util.List;
/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 14:22
 */
public interface StaffRoleService {

	List<StaffRole> queryByStaffId(String staffId);
	
	void deleteByStaffId(String staffId);
	
	int batchInsertStaffRoles(List<StaffRole> staffRoleList);
	
	/**
	 * 根据用户编码获取对应角色信息
	  * 
	  * @param staffId
	  * @return	角色名称
	 */
	List<String> getRolesByStaffId(String staffId);

}
