/**
 * 
 */
package com.matthew.cerp.dao;


import com.alibaba.druid.sql.visitor.functions.Right;
import com.matthew.cerp.entity.RoleRight;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 16:51
 */
public interface RoleRightDao {
	
	/**
	 * 根据treeNodeId 查询构造树的节点
	 * @param NodeId
	 * @return
	 */
	List<RoleRight> queryRightByRoleId(String NodeId);
	
	List<String> getRightCodesByRoleId(String roleId);
	
	List<Right> getMenusByRoleId(String roleId);
	
	/**
	 * 查询所有权限节点，初始化权限Tree
	 * @param roleId
	 * @return
	 */
	List<RoleRight> queryAllRight(String roleId);
	
	/**
	 * 为角色赋权限
	 * @param roleRight
	 */

	void registRightToRole(RoleRight roleRight);
	
	/**
	 * 删除权限
	 * @param roleRightId
	 */
	//void deleteRoleById(int roleRightId);
	
	/**
	 * 为Role批量添加rights
	 * @param roleRights
	 */
	
	void insertRightRoles(List<RoleRight> roleRights);
	
	/**
	 * 根据角色Id批量删除该角色的所有绑定权限
	 * @param roleId
	 */
	void deleteRoleByRoleIds(String roleId);
}
