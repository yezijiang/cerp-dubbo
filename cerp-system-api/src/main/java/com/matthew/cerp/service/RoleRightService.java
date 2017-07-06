package com.matthew.cerp.service;


import com.matthew.cerp.entity.Right;
import com.matthew.cerp.entity.RoleRight;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 14:22
 */
public interface RoleRightService {
	/**
	 * 日志操作编码_默认
	 */
	public static final String LOG_OPER_CODE_DEFAULT = "0";
	/**
	 * 查询所有权限节点
	 * @param roleId
	 * @return
	 */

	List<RoleRight> queryAllRight(String roleId);
	List<String> getRightCodesByRoleId(String roleId);
	
	/**
	 * 根据角色编码获取菜单权限
	  * 
	  * @param roleId
	  * @return
	 */
	List<Right> getMenusByRoleId(String roleId);
	
	/**
	 * 根据角色Id，查询所有
	 * @param roleId
	 * @return
	 */
	
	/**
	 * 将字符串 类型的rights转成字符串数组类型的rightIds
	 *
	 * @param roleId
	 * @return
	 */
		List<RoleRight> queryRightByRoleId(String roleId);
	
	/**
	 * 将前端传来的String类型的roleId-->String[] --->并将创建的roleRights存到数据库
	 * @param roleId
	 * @return
	 */

	void saveRoleRight(String roleId, String rightIds);
	
	
	/**
	 * 为角色注册权限
	 * @param roleRight
	 */
	void registRight(RoleRight roleRight);
	
	/**
	 * 为role批量添加rights
	 * @param roleRights
	 */
	void insertRightRoles(List<RoleRight> roleRights);
	
	/**
	 * 批量删除
	 * @param roleId
	 */
	void deleteRightRoles(String roleId);
	
	
	/**
	 * 将List<String>--->List<int>
	 */
	
	List<Integer> transStingToInt(List<String> rightIds);
}
