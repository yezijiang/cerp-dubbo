package com.matthew.cerp.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.matthew.cerp.entity.Staff;
import com.matthew.cerp.entity.StaffOffice;

import java.util.List;
import java.util.Map;
/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 14:22
 */
public interface StaffService {
	Staff findById(String staffId);

	void registerStaff(Staff staff);

	void updateStaff(Staff staff);

	void deleteStaff(String staffId);

	/**
	  * 根据登录账号获取账户信息
	  * 
	  * @param loginAccount
	  * @return
	 */
	Staff findByLoginAccount(String loginAccount);
	
	/**
	  * 判断登陆账号是否重复
	  * 
	  * @param loginAccount
	  * @return
	 */
	boolean isExistsLoginAccount(String loginAccount);
	
	/**
	  * 判断某部门是否已存在部门主管
	  * 
	  * @param organizeId
	  * @return
	 */
	boolean isExistsCharge(String organizeId);
	/**
	  * 分页查询
	  * @param params
	  * @param pageBounds
	  * @return
	 */
	List<Staff> queryByPage(Map<String, Object> params, PageBounds pageBounds);
	
	public void insertStaffOffice(StaffOffice staffOffice);
}
