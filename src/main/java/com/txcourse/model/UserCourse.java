package com.txcourse.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "UserCourse")
public class UserCourse {
	
	public UserCourse() {
		ensureId();
		chooseStatus = 0;
		experimentStatus = 0;
		workTime = 0.0;
		score = 0.0;
		vpsid = 0L;
	}
	
	@PrePersist
	// 生成String类型的id
	private UserCourse ensureId() {
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
	
	//user的id 学号
	private String studentId;
	
	//课程id
	private String courseId;
	
	//选课状态, 0未选课，1已选课
	private Integer chooseStatus;
	
	//实验状态，是否上机，根据是否创云机来判断，0表示未上机，1表示已上机
	private Integer experimentStatus;
	
	//工作量
	private Double workTime;
	
	//成绩
	private Double score;
	
	//实验报告路径
	private String reportUrl;
	
	//ip
	private String ip;
	
	//密码
	private String password;
	
	private Long vpsid;
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Integer getChooseStatus() {
		return chooseStatus;
	}

	public void setChooseStatus(Integer chooseStatus) {
		this.chooseStatus = chooseStatus;
	}

	public Integer getExperimentStatus() {
		return experimentStatus;
	}

	public void setExperimentStatus(Integer experimentStatus) {
		this.experimentStatus = experimentStatus;
	}


	public Double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getVpsid() {
		return vpsid;
	}

	public void setVpsid(Long vpsid) {
		this.vpsid = vpsid;
	}

	
	
	
}
