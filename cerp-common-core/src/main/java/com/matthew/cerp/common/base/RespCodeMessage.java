package com.matthew.cerp.common.base;

/**
 * 错误编码统一定义类
 * 
 * @author liujianzhu
 * @date 2016年3月22日 下午3:02:02
 *
 */
public enum RespCodeMessage {
	CERP_0000_MSG("0000","成功"),
	CERP_9999_MSG("9999","失败"),
	
	//登录权限相关
	CERP_UNKNOWN_ACCOUNT("9001","用户名或密码错误"),
	CERP_LOCKED_ACCOUNT("9002","账号锁定"),
	CERP_UNKNOWN_CAPTCHA("9003","验证码错误"),
	CERP_UNAUTHENTICATED("9004","未登录用户"),
	CERP_UNAUTHORIZATD("9401","无权访问当前资源")
	
	;
	
	private String code;

	private String message;

	private RespCodeMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
