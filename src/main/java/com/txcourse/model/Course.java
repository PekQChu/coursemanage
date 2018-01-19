package com.txcourse.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "Course")
public class Course {
	
	public Course() {
		ensureId();
		courseStatus = 0;
		studentNum = 0;
	}
	
	@PrePersist
	// 生成String类型的id
	private Course ensureId() {
		this.setId(UUID.randomUUID().toString());return this;
	}
	
	@Id
	@Column(name = "id")
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//课程名称
	private String courseName;
	
	//课程号-教师号
	private String courseSequence;
	
	//上课时间
	/*
	{
		startTime:2017-9-1
		endTime:2017-9-2
		classTime:[
		           {
		        	   classStarTime:xxxx
		        	   classEndTime
		           }
		           ]
	}*/
	private String courseTime;
	
	//学生名单路径
	private String studentListUrl;
	
	//计算资源类型：0表示VPS，1表示容器
	private Integer resourceType;
	
	//模板id
	private String templateId;
	
	//预设积分
	private Double courseScore;
	
	//课程内容
	@Column(name = "courseContent",length=99999)
	private String courseContent;
	
	//老师ID
	private String teacherId;
	
	//课程状态:0未审核，1审核通过，2课程关闭， 3课程开启
	private Integer courseStatus;
	
	//当前上课人数
	private Integer studentNum;
	
	//课程容量
	private Integer maxStudentNum;
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseSequence() {
		return courseSequence;
	}

	public void setCourseSequence(String courseSequence) {
		this.courseSequence = courseSequence;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public String getStudentListUrl() {
		return studentListUrl;
	}

	public void setStudentListUrl(String studentListUrl) {
		this.studentListUrl = studentListUrl;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Double getCourseScore() {
		return courseScore;
	}

	public void setCourseScore(Double courseScore) {
		this.courseScore = courseScore;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(Integer courseStatus) {
		this.courseStatus = courseStatus;
	}

	public Integer getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(Integer studentNum) {
		this.studentNum = studentNum;
	}

	public Integer getMaxStudentNum() {
		return maxStudentNum;
	}

	public void setMaxStudentNum(Integer maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
	}
	
	
}
