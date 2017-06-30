package com.matthew.cerp.system;

import com.matthew.cerp.common.base.CerpConstants;
import com.matthew.cerp.common.util.CerpUtil;
import com.matthew.cerp.entity.TSLog;
import com.matthew.cerp.service.SystemLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-28 15:57
 */
public class LoginController {
    private final static Logger log = LoggerFactory.getLogger(LoginController.class);

    private SystemLogService systemLogService;
    /**
     * 登录功能
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated())
            return "redirect:/";
        return "login2";
    }
    @RequestMapping(value="/default")
    public String toDefault(){
        return "/default";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        String captchaCode = request.getParameter("captchaCode");
        //判断验证码
        Session session = CerpUtil.getCurrentSession(request, response);
        String savedCaptchaCode = String.valueOf(session.getAttribute(CerpConstants.SESSION_CAPTCHA_CODE));
        if(StringUtils.isEmpty(captchaCode)
                || !captchaCode.toUpperCase().equals(savedCaptchaCode)) {
            model.addAttribute("error", "验证码错误.");
            return "/login2";
        }
        //
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            return "redirect:/";
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe("1".equals(rememberMe)? true:false);
            subject.login(token);
            //根据登录前的请求地址跳转
            WebUtils.redirectToSavedRequest(request, response, "/");
            //基于业务，正常登入后写日志 add by ZX 2016-03-21
            systemLogService.insertLoginLog(new TSLog("0"));
        }
        catch(AuthenticationException e) {
            log.error("登录失败",e);
            model.addAttribute("error", "用户名或密码错误.");
            //登录失败则保存当前请求
            //WebUtils.saveRequest(request);
            return "login2";
        }
        catch(IOException e){
            log.error("登录成功后跳转失败",e);
        }
        return null;
    }
}
