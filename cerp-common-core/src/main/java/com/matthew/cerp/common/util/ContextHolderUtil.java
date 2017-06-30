package com.matthew.cerp.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 上下文工具类
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 13:41
 */
public class ContextHolderUtil {
    /**
     * 方法名称 :getRequest
     * 创  建 人 :ZX
     * 创建时间 :2016-3-17 下午7:04:36
     * 方法描述 :SpringMvc下获取request
     *   参   数  :@return
     *   结   果  :HttpServletRequest
     *   异   常  :@throws
     */
    public static HttpServletRequest getRequest()
    {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }

    /**
     * 方法名称 :getRequest
     * 创  建 人 :ZX
     * 创建时间 :2016-3-17 下午7:04:36
     * 方法描述 :SpringMvc下获取session
     *   参   数  :@return
     *   结   果  :HttpServletRequest
     *   异   常  :@throws
     */
    public static HttpSession getSession()
    {
        HttpSession session = getRequest().getSession();
        return session;
    }
}
