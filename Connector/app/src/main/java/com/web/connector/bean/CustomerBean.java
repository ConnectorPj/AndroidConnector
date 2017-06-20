package com.web.connector.bean;

import java.util.ArrayList;
import java.util.Calendar;

//DTO
public class CustomerBean {

	private String customerId;
	private String customerPw;
	private String customerName;
	private String customerCellphone;
	private String customerProfile;
	private int customerNo;
	private String customerGender;
	private Calendar customerRegdate;

	
	ArrayList<PurchaseBean> purchaseList;
	ArrayList<BucketBean> bucketList;

	
	
	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public ArrayList<PurchaseBean> getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(ArrayList<PurchaseBean> purchaseList) {
		this.purchaseList = purchaseList;
	}

	public ArrayList<BucketBean> getBucketList() {
		return bucketList;
	}

	public void setBuskcetList(ArrayList<BucketBean> bucketList) {
		this.bucketList = bucketList;
	}


	public int getCustomerNo() {
		return customerNo;
	}

	public Calendar getCustomerRegdate() {
		return customerRegdate;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public void setCustomerRegdate(Calendar customerRegdate) {
		this.customerRegdate = customerRegdate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerPw() {
		return customerPw;
	}

	public void setCustomerPw(String customerPw) {
		this.customerPw = customerPw;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCellphone() {
		return customerCellphone;
	}

	public void setCustomerCellphone(String customerCellphone) {
		this.customerCellphone = customerCellphone;
	}

	public String getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(String customerProfile) {
		this.customerProfile = customerProfile;
	}

}
