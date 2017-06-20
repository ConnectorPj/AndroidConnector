package com.web.connector.bean;

import java.util.Calendar;

public class BucketBean {
	private String bucketNo;
	private String customerId;
	private String studyId;
	private Calendar bucketDate;
	public String getBucketNo() {
		return bucketNo;
	}
	public void setBucketNo(String bucketNo) {
		this.bucketNo = bucketNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public Calendar getBucketDate() {
		return bucketDate;
	}
	public void setBucketDate(Calendar bucketDate) {
		this.bucketDate = bucketDate;
	}
	
	
	
}
