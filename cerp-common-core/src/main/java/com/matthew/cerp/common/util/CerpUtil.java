package com.matthew.cerp.common.util;

import com.alibaba.fastjson.JSON;
import com.matthew.cerp.common.base.CerpConstants;
import com.matthew.cerp.common.base.RespCodeMessage;
import com.matthew.cerp.common.cache.RedisMgr;
import com.matthew.cerp.common.springmvc.SpringContextHolder;
import com.matthew.cerp.entity.Staff;
import com.yaoyaohao.framework.redis.ShardedJedisClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 获取公用数据，包括当前用户信息、Session会话数据等
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 13:43
 */
public class CerpUtil {

    private final static Logger logger = LoggerFactory.getLogger(CerpUtil.class);

    /**
     * 获取当前会话用户信息
     *
     * @return
     */
    public static Staff getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            return (Staff)subject.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前用户的会话信息
     *
     * @param request
     * @param response
     * @return
     */
    public static Session getCurrentSession(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession(true);
    }

    /**
     * 权限判断
     * @param permissions	多个权限编码，对应DB表中的right_code字段
     * @return 参数中权限都有时返回true，反之false
     */
    public static boolean checkPermissions(String... permissions) {
        boolean hasPerms = false;
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.checkPermissions(permissions);
            hasPerms = true;
        }catch(AuthorizationException e) {
            hasPerms = false;
        }
        return hasPerms;
    }

    /**
     * 判断是否有角色权限
     *
     * @param roleIdentifier	角色编码
     * @return
     */
    public static boolean hasRole(String roleIdentifier) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(roleIdentifier);
    }

    /**
     * 判断是否有参数角色，结果以boolean数组形式返回
     *
     * @param roleIdentifiers
     * @return
     */
    public static boolean[] hasRoles(String... roleIdentifiers) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRoles(CollectionUtils.asList(roleIdentifiers));
    }

    /**
     * 判断是否拥有全部参数角色
     *
     * @param roleIdentifiers
     * @return
     */
    public static boolean hasAllRoles(String... roleIdentifiers) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasAllRoles(CollectionUtils.asList(roleIdentifiers));
    }

    /**
     * 判断请求是否是ajax，通过请求头X-Requested-With = XMLHttpRequest来判断
     * @param request
     * @return
     */
    public static boolean isAjaxHttp(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        if ("XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"))) {
            return true;// it's ajax
        }
        return false;
    }

    /**
     * 以json形式返回响应
     * @param response
     * @param message
     * @throws IOException
     */
    public static void sendJson(ServletResponse response, Object message){
        try{
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(message));
            out.flush();
            out.close();
        }catch(IOException e) {
            logger.error("ServletResponse返回JSON响应报错", e);
        }
    }

    //{"code":"0000",status:"success","message":"","requireUrl":""}
    public static void sendJson(ServletRequest request, ServletResponse response, RespCodeMessage respCodeMessage, String... expMessages){
        Map<String, Object> params = new HashMap<String, Object>();
        String code = respCodeMessage.getCode();
        params.put("code", code);
        String message = respCodeMessage.getMessage();
        if(expMessages != null) {
            for(String msg : expMessages) {
                message += msg;
            }
        }
        params.put("message", message);
        params.put("status", (CerpConstants.SUCCESSFUL_RESP_CODE.equals(code)? "success":"failure"));
        //
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if(savedRequest != null) {
            params.put("requestUrl", savedRequest.getRequestUrl());
        }
        //
        try{
            response.setContentType("text/plain;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(params));
            out.flush();
            out.close();
        }catch(IOException e) {
            logger.error("ServletResponse返回JSON响应报错", e);
        }
    }

    /**
     *
     * 清除shiro缓存的赋权信息
     *
     */
    public static void clearCachedAuthorization(){
        try{
            ShardedJedisClient jedisClient = SpringContextHolder.getBean(ShardedJedisClient.class);
            Set<byte[]> keys = jedisClient.bKeys(CerpConstants.SHIRO_AUTHORIZATION_PREFIX+"*");
            if (!CollectionUtils.isEmpty(keys)){
                for (byte[] key : keys){
                    jedisClient.del(key);
                }
            }
        }catch (Throwable t){
            logger.error("调用clearCachedAuthorization方法清除赋权信息出错",t);
        }
    }


    /**
     * 清除shiro缓存的角色权限信息
     */
    public static void clearRoleRight() {
        clearCachedAuthorization();
        try{
            //清除缓存中的role_right信息
            RedisMgr redisMgr = SpringContextHolder.getBean(RedisMgr.class);
            if(redisMgr == null)
                return;
            redisMgr.clearTable("T_SYS_ROLE_RIGHT");
            redisMgr.clearTable("T_SYS_RIGHT");

        }catch(Throwable t) {
            logger.error("调用clearCachedAuthorization清除缓存中的role_right信息出错", t);
        }
    }

    /**
     * 清除shiro缓存的用户角色信息
     */
    public static void clearCacheStafffRole(){
        clearCachedAuthorization();
        try{
            RedisMgr redisMgr = SpringContextHolder.getBean(RedisMgr.class);
            if (redisMgr == null) return;
            redisMgr.clearTable("T_SYS_STAFF_ROLE");

        }catch (Throwable t){
            logger.error("调用clearCacheStafffRole清除staffRole信息出错",t);

        }

    }
}
