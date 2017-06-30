package com.matthew.cerp.entity;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-29 11:28
 */
public class TSLog implements Serializable {

    /**
     * TSLog.java
     */
    private static final long serialVersionUID = -2385051259881930354L;

    /**
     * @param operateType
     */
    public TSLog(String operateType) {
        this.operateType = operateType;
    }

    private long operateId;//操作编号

    private String staffId;//员工编号

    private String loginAccount;//登录账号

    private String operateCode;//操作编号，默认操作0(0:其他，1：新增，2：修改，3：删除，99：异常【系统预留】)

    private String operateContent;//操作内容

    private String operateDesc;//操作描述

    private String operateTime;//操作时间

    private String isSuccess;//是否执行成功:YES-成功 NO-失败

    private String executionTime;//执行时间

    private String ip;//登录IP

    private String broswer;//登录浏览器

    private String staffName;//登录用户名称

    private long loginId;//登录编码-登录日志用

    private String operateType;//操作类型:0-登陆 1-主动登出 2-超时退出-登录日志用

    public long getOperateId() {
        return operateId;
    }

    public void setOperateId(long operateId) {
        this.operateId = operateId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getOperateDesc() {
        return operateDesc;
    }

    public void setOperateDesc(String operateDesc) {
        this.operateDesc = operateDesc;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getLoginId() {
        return loginId;
    }

    public void setLoginId(long loginId) {
        this.loginId = loginId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getBroswer() {
        return broswer;
    }

    public void setBroswer(String broswer) {
        this.broswer = broswer;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return "TSLog [operateId=" + operateId + ", staffId=" + staffId + ", loginAccount=" + loginAccount
                + ", operateCode=" + operateCode + ", operateContent=" + operateContent + ", operateDesc=" + operateDesc
                + ", operateTime=" + operateTime + ", isSuccess=" + isSuccess + ", executionTime=" + executionTime
                + ", ip=" + ip + ", broswer=" + broswer + ", staffName=" + staffName + ", loginId=" + loginId
                + ", operateType=" + operateType + "]";
    }
}
