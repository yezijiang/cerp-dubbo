package com.matthew.cerp.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.matthew.cerp.common.util.BrowserUtil;
import com.matthew.cerp.common.util.CerpUtil;
import com.matthew.cerp.common.util.CommonUtil;
import com.matthew.cerp.common.util.ContextHolderUtil;
import com.matthew.cerp.dao.SystemLogDao;
import com.matthew.cerp.entity.Staff;
import com.matthew.cerp.entity.TSLog;
import com.matthew.cerp.service.SystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 14:22
 */
@Service
public class SystemLogServiceImpl implements SystemLogService
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SystemLogServiceImpl.class);
    
    @Autowired
    private SystemLogDao systemLogDao;
    
    public void insertOperateLog(TSLog log)
    {
        try
        {
            systemLogDao.insertOperateLog(log);
        }
        catch (Exception e)
        {
            logger.error("记录操作异常日志失败:{}", e.getMessage());
        }
    }
    
    public void insertOperateFailLog(TSLog log)
    {
        try
        {
            systemLogDao.insertOperateLog(log);
        }
        catch (Exception e)
        {
            logger.error("记录操作日志失败:{}", e.getMessage());
        }
    }
    
    public void insertLoginLog(TSLog log){
        try
        {
            HttpServletRequest request = ContextHolderUtil.getRequest();
            Staff user = CerpUtil.getCurrentUser();
            if (null != request && null != user)
            {
                log.setStaffId(String.valueOf(user.getStaffId()));//员工编号
                log.setLoginAccount(user.getLoginAccount());//登陆账号
                log.setStaffName(user.getStaffName());//登陆账号名称
                
                log.setIp(CommonUtil.getIpAddr(request));//登陆IP
                log.setBroswer(BrowserUtil.checkBrowse(request));
                systemLogDao.insertLoginLog(log);
            }
        }
        catch (Exception e)
        {
            logger.error("记录登陆日志失败:{}", e.getMessage());
        }
    }
    
    public List<TSLog> queryLogData(Map<String, Object> params, PageBounds pageBounds)
    {
        List<TSLog> result = null;
        String type = String.valueOf(params.get("type"));
        if ("_login".equalsIgnoreCase(type))
        {
            result = systemLogDao.queryLoginLogData(params, pageBounds);
        }
        else if ("_operate".equalsIgnoreCase(type))
        {
            result = systemLogDao.queryOperateLogData(params, pageBounds);
        }
        return result;
    }
    
}
