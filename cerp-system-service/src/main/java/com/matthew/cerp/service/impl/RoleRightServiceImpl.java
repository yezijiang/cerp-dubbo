/**
 * 
 */
package com.matthew.cerp.service.impl;


import com.matthew.cerp.common.annotation.SystemLog;
import com.matthew.cerp.common.cache.RedisMgr;
import com.matthew.cerp.common.util.CerpUtil;
import com.matthew.cerp.common.util.Constants;
import com.matthew.cerp.dao.RoleRightDao;
import com.matthew.cerp.entity.Right;
import com.matthew.cerp.entity.RoleRight;
import com.matthew.cerp.service.RoleRightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 16:22
 */
@Service
public class RoleRightServiceImpl implements RoleRightService {
	private static Logger logger= LoggerFactory.getLogger(RoleRightServiceImpl.class);

	@Autowired
	private RoleRightDao roleRightDao;
	
	@Autowired
	private RedisMgr redisMgr;

	/**
	 * 根据角色Id获取权限列表，即查询某个角色的所有权限
	 */
	public List<RoleRight> queryRightByRoleId(String roleId) {
		return this.roleRightDao.queryRightByRoleId(roleId);
	}

	@SystemLog(description = "查询角色权限",operateCode = LOG_OPER_CODE_DEFAULT)
	public List<String> getRightCodesByRoleId(String roleId) {
		return roleRightDao.getRightCodesByRoleId(roleId);
	}
	

	@SystemLog(description = "查询权限菜单",operateCode = Constants.LOG_OPER_CODE_DEFAULT)
	public List<Right> getMenusByRoleId(String roleId) {
		List<Right> menus = new ArrayList<Right>();
		//先从缓存中获取数据
		try{
			menus = redisMgr.getList("T_SYS_ROLE_RIGHT", "roleId,rightType,deletedFlag",roleId+",0,0", new RedisMgr.ExtractHandler<Right>() {
						public Right extract(Map<String, String> map) {
							Right right = new Right();
							right.setRightId(map.get("RIGHT_ID"));
							right.setRightType(map.get("RIGHT_TYPE"));
							right.setRightName(map.get("RIGHT_NAME"));
							right.setRightCode(map.get("RIGHT_CODE"));
							right.setRightUrl(map.get("RIGHT_URL"));
							right.setRightDesc(map.get("RIGHT_DESC"));
							right.setParentRightId(map.get("PARENT_RIGHT_ID"));
							right.setRightLevel(map.get("RIGHT_LEVEL"));
							right.setOrderBy(map.get("ORDER_BY"));
							right.setDeletedFlag(map.get("DELETED_FLAG"));
							return right;
						}
					});
		}catch(Exception e){
			logger.error("错误",e);
		}
		return menus;
	}

	/**
	 * 为角色注册权限
	 */
	@SystemLog(description = "更改角色权限",operateCode = Constants.LOG_OPER_CODE_INSERT)
	public void registRight(RoleRight roleRight) {
		CerpUtil.clearRoleRight();
		this.roleRightDao.registRightToRole(roleRight);
	}

	/**
	 * 查询所有权限节点，构造整个权限树结构
	 */
	@SystemLog(description = "查询角色权限",operateCode = Constants.LOG_OPER_CODE_DEFAULT)
	public List<RoleRight> queryAllRight(String roleId) {
		List<RoleRight> treeNodes = this.roleRightDao.queryAllRight(roleId);
		generaChildNodes(treeNodes);
		return treeNodes;
	}

	public void generaChildNodes(List<RoleRight> treeNodes) {
		if (treeNodes.size() == 0 || treeNodes == null)
			return;
		for (RoleRight treeNode : treeNodes) {
			List<RoleRight> childNodes = this.roleRightDao.queryAllRight(treeNode.getId());
			generaChildNodes(childNodes);
			treeNode.setChildren(childNodes);
		}
	}

	@SystemLog(description = "删除角色权限",operateCode = Constants.LOG_OPER_CODE_UPDATE)
	public void deleteRightRoles(String roleId) {
		this.roleRightDao.deleteRoleByRoleIds(roleId);
	}

	@SystemLog(description = "保存角色权限",operateCode = Constants.LOG_OPER_CODE_INSERT)
	public void saveRoleRight(String roleId, String rightIds) {
		CerpUtil.clearRoleRight();
		String[] rightId = rightIds.split(",");
		List<RoleRight> roleRights = new ArrayList<RoleRight>();
		RoleRight roleRight;
		for (int i = 0; i < rightId.length; i++) {
			roleRight = new RoleRight();
			roleRight.setRoleId(roleId);
			roleRight.setRightId(rightId[i]);
			roleRights.add(roleRight);
		}
		roleRightDao.deleteRoleByRoleIds(roleId);
		insertRightRoles(roleRights);
	}
	@SystemLog(description = "批量保存角色权限",operateCode = Constants.LOG_OPER_CODE_INSERT)
	public void insertRightRoles(List<RoleRight> roleRights) {
		CerpUtil.clearRoleRight();
		if (roleRights.size() == 0 || roleRights == null)
			return;
		this.roleRightDao.insertRightRoles(roleRights);
	}

	public List<Integer> transStingToInt(List<String> rightIds) {
		List<Integer> rightNewIds = new ArrayList<Integer>();
		for (String rightId : rightIds) {
			Integer i = Integer.valueOf(rightId);
			rightNewIds.add(i);
		}
		return rightNewIds;
	}
}
