package com.shu.xen.api;

public class VpsServer {
	/**
	 * 服务器Id
	 */
	private Long sid;
	/**
	 * 服务器分组id
	 */
	private Long did;
	/**
	 * 服务器名字
	 */
	private String sname;
	/**
	 * 服务器Ip
	 */
	private String sip;
	/**
	 * 最大cpu核心数
	 */
	private Long maxcpu;
	/**
	 * 最大支持内存数（MB）
	 */
	private Long maxram;
	/**
	 * 最大支持存储空间数（GB）
	 */
	private Long maxdisk;
	/**
	 * 空闲内存数（MB
	 */
	private Long allowram;
	/**
	 * 空闲存储空间数（GB）
	 */
	private Long allowdisk;
	/**
	 * 服务器状态
	 */
	private String sstatus;

	/* 机柜编号 */
	private String rack;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public Long getMaxcpu() {
		return maxcpu;
	}

	public void setMaxcpu(Long maxcpu) {
		this.maxcpu = maxcpu;
	}

	public Long getMaxram() {
		return maxram;
	}

	public void setMaxram(Long maxram) {
		this.maxram = maxram;
	}

	public Long getMaxdisk() {
		return maxdisk;
	}

	public void setMaxdisk(Long maxdisk) {
		this.maxdisk = maxdisk;
	}

	public Long getAllowram() {
		return allowram;
	}

	public void setAllowram(Long allowram) {
		this.allowram = allowram;
	}

	public Long getAllowdisk() {
		return allowdisk;
	}

	public void setAllowdisk(Long allowdisk) {
		this.allowdisk = allowdisk;
	}

	public String getSstatus() {
		return sstatus;
	}

	public void setSstatus(String sstatus) {
		this.sstatus = sstatus;
	}

	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}
}
