package com.shu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RunVpsRecord")
public class RunVpsRecord {
	
	public RunVpsRecord(){
		super();
		type=0;
		startTime=new Date();
		createTime=new Date();
		cpu=1;
		mem=512;
		gpu=0;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "createTime", nullable = true)
	private Date createTime;
	@Column(name = "startTime", nullable = true)
	private Date startTime;
	@Column(name = "endTime", nullable = true)
	private Date endTime;
	@Column(name = "uid", nullable = true)
	private String uid;
	@Column(name = "useMoney", nullable = true)
	private double useMoney;
	private int storage;
	private int mem;
	private int cpu;
	private int gpu;
//	0 vps 1-container 2-disk 3-objectStorage
	private int type;
	public String getTypeStr(){
		if(type==0){
			return  "云主机扣费";
		}else if(type==1){
			return  "容器扣费";
		}else if(type==2){
			return  "云硬盘扣费";
		}else if(type==3){
			return  "对象存储扣费";
		}else{
			return "类型错误!";
		}
	}
//	资源ID
	private String sourceId;
	public Long getId() {
		return id;
	}
	public RunVpsRecord setId(Long id) {
		this.id = id;return this;
	}
	public Date getStartTime() {
		return startTime;
	}
	public RunVpsRecord setStartTime(Date startTime) {
		this.startTime = startTime;return this;
	}
	public Date getEndTime() {
		return endTime;
	}
	public RunVpsRecord setEndTime(Date endTime) {
		this.endTime = endTime;return this;
	}
	public String getUid() {
		return uid;
	}
	public RunVpsRecord setUid(String uid) {
		this.uid = uid;return this;
	}
	public double getUseMoney() {
		return useMoney;
	}
	public RunVpsRecord setUseMoney(double useMoney) {
		this.useMoney = useMoney;return this;
	}
	public int getStorage() {
		return storage;
	}
	public RunVpsRecord setStorage(int storage) {
		this.storage = storage;return this;
	}
	public int getMem() {
		return mem;
	}
	public RunVpsRecord setMem(int mem) {
		this.mem = mem;return this;
	}
	public int getCpu() {
		return cpu;
	}
	public RunVpsRecord setCpu(int cpu) {
		this.cpu = cpu;return this;
	}
	public String getSourceid() {
		return sourceId;
	}
	public String getVpsid() {
		return sourceId;
	}
	public RunVpsRecord setVpsid(String vpsid) {
		this.sourceId = vpsid;return this;
	}
	public RunVpsRecord setSourceid(String sourceId) {
		this.sourceId = sourceId;return this;
	}
	public int getType() {
		return type;
	}
	public RunVpsRecord setType(int type) {
		this.type = type;return this;
	}
	public String getSourceId() {
		return sourceId;
	}
	public RunVpsRecord setSourceId(String sourceId) {
		this.sourceId = sourceId;return this;
	}
	public int getGpu() {
		return gpu;
	}
	public RunVpsRecord setGpu(int gpu) {
		this.gpu = gpu;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
