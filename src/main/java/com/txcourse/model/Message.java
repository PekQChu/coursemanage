package com.txcourse.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "Message")
public class Message {
	
	public Message() {
		msgTime = new Date();
		msgStatus = 0;
		ensureId();
	}
	
	@PrePersist
	// 生成String类型的id
	private Message ensureId() {
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
	
	//留言发送人
	private String sendUserId;
	
	//留言接收人
	private String receiveUserId;
	
	//留言内容
	@Column(name = "content",length=99999)
	private String content;
	
	//留言时间
	private Date msgTime;
	
	//留言状态，0表示未读， 1表示已读
	private Integer msgStatus;
	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

	public Integer getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
	
	
	
}
