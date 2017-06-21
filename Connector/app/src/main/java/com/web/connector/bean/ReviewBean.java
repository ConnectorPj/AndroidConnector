package com.web.connector.bean;


public class ReviewBean {
	private int reviewNo;
	private String customerId;
	private String teacherId;
	private String studyId;
	private String customerName;
	private String teacherName;
	private String customerPicture;
	private String teacherPicture;
	private String studyProgressname;
	private String studyProgramlanguage;
	private String reviewContent;
	private int reviewRating;
	private String reviewRegdate;
	private int startRow;
	
	
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getReviewNo() {
		return reviewNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public String getCustomerPicture() {
		return customerPicture;
	}
	public String getTeacherPicture() {
		return teacherPicture;
	}
	public String getStudyProgressname() {
		return studyProgressname;
	}
	public String getStudyProgramlanguage() {
		return studyProgramlanguage;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public int getReviewRating() {
		return reviewRating;
	}
	public String getReviewRegdate() {
		return reviewRegdate;
	}
	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public void setCustomerPicture(String customerPicture) {
		this.customerPicture = customerPicture;
	}
	public void setTeacherPicture(String teacherPicture) {
		this.teacherPicture = teacherPicture;
	}
	public void setStudyProgressname(String studyProgressname) {
		this.studyProgressname = studyProgressname;
	}
	public void setStudyProgramlanguage(String studyProgramlanguage) {
		this.studyProgramlanguage = studyProgramlanguage;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public void setReviewRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}
	public void setReviewRegdate(String reviewRegdate) {
		this.reviewRegdate = reviewRegdate;
	}
	
}
