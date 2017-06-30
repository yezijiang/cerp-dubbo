package com.matthew.cerp.common.base;

/**
 * 常量定义类
 * 
 * @author liujianzhu
 * @date 2016年3月21日 上午11:18:30
 *
 */
public class CerpConstants {
	public final static String SUCCESSFUL_RESP_CODE = "0000";
	
	/** Session中保存的验证码属名 */
	public final static String SESSION_CAPTCHA_CODE = "SESSION_CAPTCHA_CODE";
	
	/**SHIRO中缓存权限信息KEY前缀*/
	public final static String SHIRO_AUTHORIZATION_PREFIX = "Shiro_Cache:";
	
	/**菜单根结点编码*/
	public final static String ROOT_MENU_CODE = "1";
	
	/**用户激活状态--未审核*/
	public static final String MMS_ACTIVE_STATE_NOTAPPROVE="0";
	/**用户激活状态--审核中*/
	public static final String MMS_ACTIVE_STATE_APPROVING="1";
	/**用户激活状态--已激活*/
	public static final String MMS_ACTIVE_STATE_ACTIVTION="2";
	/**用户激活状态--已激活停用*/
	public static final String MMS_ACTIVE_STATE_DISABLED="3";
	/**用户激活状态--审核未通过*/
	public static final String MMS_ACTIVE_STATE_NOTPASS="4";
	
	/**客户性质--连锁办商业公司*/
	public static final String CUSTOMER_TYPE_CHAIN_COMP = "1591";
	/**客户性质--综合性医院*/
	public static final String CUSTOMER_TYPE_INTEGRATE_HOSPITAL = "1584";
	/**客户性质--卫生院*/
	public static final String CUSTOMER_TYPE_HEALTH_HOSPITAL = "1585";
	/**客户性质--专科医院*/
	public static final String CUSTOMER_TYPE_SPECIAL_HOSPITAL = "1586";
	/**客户性质--个体医疗*/
	public static final String CUSTOMER_TYPE_PERSON_MEDICINE = "1587";
	/**客户性质--单体药店*/
	public static final String CUSTOMER_TYPE_SINGLE_PHARMACY = "1588";
	/**客户性质--连锁药店*/
	public static final String CUSTOMER_TYPE_CHAIN_DRUGSTORE = "1589";
	/**客户性质--商业公司*/
	public static final String CUSTOMER_TYPE_BUSINESS_COMPANY = "1668";
	
	/**证书类型--营业执照**/
	public static final String CERTIFICATE_BUSINESS = "BUSINESS";
	/**证书类型--食品流通许可证**/
	public static final String CERTIFICATE_FOOD = "FOOD";
	/**证书类型--GSP证书**/
	public static final String CERTIFICATE_GSP = "GSP";
	/**证书类型--法人委托书**/
	public static final String CERTIFICATE_LEGAL = "LEGAL";
	/**证书类型--母婴保健许可证**/
	public static final String CERTIFICATE_MOM = "MOM";
	/**证书类型--医疗执业许可证**/
	public static final String CERTIFICATE_PRACTICE = "PRACTICE";
	/**证书类型--收货人委托书**/
	public static final String CERTIFICATE_RECEIVER = "RECEIVER";
	/**证书类型--二类医疗器械凭证**/
	public static final String CERTIFICATE_SINSTRU = "SINSTRU";
	/**证书类型--特殊药品购买委托书**/
	public static final String CERTIFICATE_SPECIAL = "SPECIAL";
	/**证书类型--三类医疗器械凭证**/
	public static final String CERTIFICATE_TINSTRU = "TINSTRU";
	/**证书类型--药品经营许可证**/
	public static final String CERTIFICATE_TRADE = "TRADE";
	/**证书类型--GMP证书**/
	public static final String CERTIFICATE_GMP = "GMP";
	/**证书类型--组织机构信息**/
	public static final String CERTIFICATE_ORG = "ORG";
	/**证书类型--商品批件**/
	public static final String CERTIFICATE_PIJIAN = "PIJIAN";
	/**证书类型--质保协议**/
	public static final String CERTIFICATE_QUT = "QUT";



	/**
	 * 逻辑删除--正常
	 */
	public static final int DELETE_LOGIC_NORMAL = 0;
	/**
	 * 逻辑删除--删除
	 */
	public static final int DELETE_LOGIC_REMOVE = 1;

	/**
	 * 逻辑--否
	 */
	public static final String LOGIC_FALSE = "0";

	/**
	 * 逻辑--是
	 */
	public static final String LOGIC_TRUE = "1";

	
	/**证书主类型--客户**/
	public static final String MAIN_TYPE_CUST = "CUST";
	/**证书主类型--生产企业**/
	public static final String MAIN_TYPE_PROD = "PROD";
	/**证书主类型--供应商**/
	public static final String MAIN_TYPE_SUPP = "SUPP";
	/**证书主类型--用户**/
	public static final String MAIN_TYPE_USER = "USER";
	/**法人委托书对应--业务员**/
	public static final String MAIN_TYPE_MAN = "MAN";
	/**证书主类型--商品**/
	public static final String MAIN_TYPE_MERCHANDISE = "MERC";

	/**
	 *保存及提交状态
	 */

	public static final String STATE_SAVE = "SV";

	/*
	 *提交未审批
	 */
	public static final String STATE_SUBMIT = "C";

	/*
	 *审批中
	 */
	public static final String STATE_APPROVE = "I";


	/*
	 *应用场景:保存
	 */
	public static final String SCENE_SAVE = "0";

	/*
	 *应用场景:提交
	 */
	public static final String SCENE_SUBMIT = "1";


	/**证书主生效状态--生效**/
	public static final String CERTIFITY_EFFECT = "1";
	/**证书主生效状态--即将生效**/
	public static final String CERTIFITY_EFFECT_WILL = "2";
	/**证书主生效状态--失效**/
	public static final String CERTIFITY_EFFECT_LOSE = "3";

	/**商品状态--正常**/
	public static final String MERCHANDISE_STATE_NORMAL = "N";

	/**审批变量名称---在审批流程中标识审批通过(true)或不通过(false)**/
	public static final String AUDIT_VARIABLE_NAME = "isAuditPass";
	/**首营状态--未审批**/
	public static final String FIRST_SALE_STATE_NOT_AUDIT = "C";
	/**首营状态--审批中**/
	public static final String FIRST_SALE_STATE_AUDITING = "I";
	/**首营状态--审批通过**/
	public static final String FIRST_SALE_STATE_AUDIT_PASS = "N";
	/**首营状态--审批不通过(回退)**/
	public static final String FIRST_SALE_STATE_AUDIT_RETURN = "R";
	/**首营状态--作废**/
	public static final String FIRST_SALE_STATE_INVALID = "F";

	/**MQ接口错误类型*/
	public final static String MQ_SUCCESS = "0000";		//处理完成
	public final static String MQ_FAIL = "9999";		//处理异常出错
	public final static String MQ_ERROR_4444 = "4444"; //未处理
	
	/**地址类型---注册地址*/
	public static final String ADDRESS_TYPE_REGI = "2";
	/**地址类型---收货地址*/
	public static final String ADDRESS_TYPE_RECE = "3";
	
	/**审批分类---客户档案审批*/
	public static final String APPROVE_CUSTOMER_PROFILE = "customerFile";
	/**审批分类---客户档案审批*/
	public static final String APPROVE_MERCHANDISE_APPLYBILL_KEY = "GoodsFirstSaleBill";


	/**序列名称--商品编码*/
	public static final String SEQ_NAME_MERCHANDISE_CD = "MERCHANDISE_CD";

	/**业务日志操作类型--I-新增*/
	public static final String LOG_OPR_TYPE_ADD = "I";
	/**业务日志操作类型--U-修改*/
	public static final String LOG_OPR_TYPE_UPDATE = "U";
	/**业务日志操作类型--D-删除*/
	public static final String LOG_OPR_TYPE_DELETE = "D";
}
