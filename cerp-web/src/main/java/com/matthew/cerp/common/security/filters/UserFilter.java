package com.matthew.cerp.common.security.filters;

import com.matthew.cerp.common.base.RespCodeMessage;
import com.matthew.cerp.common.util.CerpUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 扩展增加对Ajax请求的支持
 * 
 * @author liujianzhu
 * @date 2016年3月22日 下午2:47:44
 *
 */
public class UserFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            // If principal is not null, then the user is known and should be allowed access.
            return subject.getPrincipal() != null;
        }
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// ajax操作
		if (CerpUtil.isAjaxHttp(request)) {
			saveRequest(request);
			//如果未登录
			if(getSubject(request, response).isAuthenticated())
				CerpUtil.sendJson(request, response, RespCodeMessage.CERP_UNAUTHORIZATD);
			else
				CerpUtil.sendJson(request, response, RespCodeMessage.CERP_UNAUTHENTICATED);
		}
		else{
			saveRequestAndRedirectToLogin(request, response);
		}
		return false;
	}

}
