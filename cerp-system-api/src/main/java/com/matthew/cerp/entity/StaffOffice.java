package com.matthew.cerp.entity;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-06 14:08
 */
public class StaffOffice {

	private String loginAccount;
	private int jobId;
	private String jobName;
	private String remark;
	private String rsrvStr1;
	private String rsrvStr2;
	private String rsrvStr3;
	private Date rsrvStr4;
	private Timestamp rsrvStr5;
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRsrvStr1() {
		return rsrvStr1;
	}
	public void setRsrvStr1(String rsrvStr1) {
		this.rsrvStr1 = rsrvStr1;
	}
	public String getRsrvStr2() {
		return rsrvStr2;
	}
	public void setRsrvStr2(String rsrvStr2) {
		this.rsrvStr2 = rsrvStr2;
	}
	public String getRsrvStr3() {
		return rsrvStr3;
	}
	public void setRsrvStr3(String rsrvStr3) {
		this.rsrvStr3 = rsrvStr3;
	}
	public Date getRsrvStr4() {
		return rsrvStr4;
	}
	public void setRsrvStr4(Date rsrvStr4) {
		this.rsrvStr4 = rsrvStr4;
	}
	public Timestamp getRsrvStr5() {
		return rsrvStr5;
	}
	public void setRsrvStr5(Timestamp rsrvStr5) {
		this.rsrvStr5 = rsrvStr5;
	}
	
}
