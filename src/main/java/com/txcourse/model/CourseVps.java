package com.txcourse.model;
 

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONStringer;

import com.shu.xen.api.Vps;



@Entity
@Table(name = "CourseVps")
public class CourseVps {
	@Id
	@Column(name = "id")
	private String id;
	public CourseVps ensureId() {
		this.setId(UUID.randomUUID().toString());return this;
	}
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	@Column(name = "uid")
	private String uid;
	
	public String getUid() {
		return uid;
	}

	public CourseVps setUid(String uid) {
		this.uid = uid;return this;
	}

	public CourseVps setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getId() {
		return id;
	}

	public CourseVps setId(String id) {
		this.id = id;
		return this;
	}

	public CourseVps(){
		super();
		ensureId();
	}
	/**
	 * 
	 */
	private Long vpsid;
	/**
	 * VPS 所属的用户编号
	 */
	private Long userid;
	/**
	 * VPS 的vCPU 数量
	 */
	private int cpu;
	/**
	 * VPS 的内存大小(MB)
	 */
	private int ram;
	/**
	 * VPS 的总硬盘容量(GB)
	 */
	private int disk;
	/**
	 * VPS 每月可用的流量大小(GB)，0 为不限
	 */
	private Long bandwidth;
	/**
	 * VPS 的端口大小(Mbps)，0 为不限
	 */
	private Long port;
	/**
	 * VPS 主机的别名，5~50 个字母+数字组合
	 */
	private String vpsname;
	/**
	 * 要安装的系统模板编号
	 */
	private int osid;
	/**
	 * 要安装的系统模板编号名称
	 */
	private String osname;
	/**
	 * 创建VPS 的目标服务器
	 */
	private Long serverid;
	/**
	 * 创建VPS 的目标服务器所属组
	 */
	private Long servergroupsId;
	/**
	 * VPS 状态： -3 超流量停机 -2 超流量运行 -1 欠费停机 0 安装中 1 锁定 2 正常
	 */
	private Integer status;
	/**
	 * 下次浏量计算重置日期
	 */
	private String bwdate;
	/**
	 * 指定VPS 的主Ip 地址，为空时由系统自动指定
	 */
	private String ip;
	/**
	 * 指定VPS 的额外IP 地址
	 */
	private String additionalips;
	/**
	 * 已使用的流量总计
	 */
	private Double bwusage;
	/**
	 * 是否为共享IP，0 为独立IP，1 为共享IP
	 */
	private int shareip;
	/**
	 * 密码
	 */
	private String dpass;
	/**
	 * MAC地址
	 */
	private String vmac;

	/**
	 * 控制台 console
	 */
	private String console;

	/**
	 * 问题 console
	 */
	private String problem;
	
	
	//+++++++++++++++++++++++++++++++++++
	
	/*
	 * 运行状态 
	 * */
	public static int STAT_RUNNING=0; //正在运行
	public static int STAT_DOWN=1;       //停机
	public static int STAT_STARTING=2;//正在启动
	public static int STAT_DIE=3;             //死机
	public static int STAT_INSTALL=4;    //正在安装
	public static int STAT_BACKUP=5;    //正在备份
	public static int STAT_RESTOR=6;    //正在还原
	
	private int  runningStat;
	
	/*
	 * vps 当前使用状态 0-1
	 * 
	 * */
	private float cpuStat;			//cpu使用量
	private float memStat;      //内存使用量
	private float diskStat;       //硬盘使用量

	
	
	//++++++++++++++++++++++++++++++++++++++
	public String getOsname() {
		return osname;
	}

	public void setOsname(String osname) {
		this.osname = osname;
	}

	public String getDpass() {
		return dpass;
	}

	public void setDpass(String dpass) {
		this.dpass = dpass;
	}

	public String getVmac() {
		return vmac;
	}

	public void setVmac(String vmac) {
		this.vmac = vmac;
	}

	public Long getVpsid() {
		return vpsid;
	}

	public void setVpsid(Long vpsid) {
		this.vpsid = vpsid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public Long getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Long bandwidth) {
		this.bandwidth = bandwidth;
	}

	public Long getPort() {
		return port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	public String getVpsname() {
		return vpsname;
	}

	public void setVpsname(String vpsname) {
		this.vpsname = vpsname;
	}

	public int getOsid() {
		return osid;
	}

	public void setOsid(int osid) {
		this.osid = osid;
	}

	public Long getServerid() {
		return serverid;
	}

	public void setServerid(Long serverid) {
		this.serverid = serverid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBwdate() {
		return bwdate;
	}

	public void setBwdate(String bwdate) {
		this.bwdate = bwdate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAdditionalips() {
		return additionalips;
	}

	public void setAdditionalips(String additionalips) {
		this.additionalips = additionalips;
	}

	public Double getBwusage() {
		return bwusage;
	}

	public void setBwusage(Double bwusage) {
		this.bwusage = bwusage;
	}

	public int getShareip() {
		return shareip;
	}

	public void setShareip(int shareip) {
		this.shareip = shareip;
	}

	public String toJson() {
		JSONStringer json = new JSONStringer();
		toJson(json);
		return json.toString();
	}

	public void toJson(JSONStringer json) {
		json.object();
		json.key("id").value(getVpsid());
		json.key("vpsname").value(getVpsname());
		json.key("ip").value(getIp());
		json.key("Cpu").value(getCpu());
		json.key("Ram").value(getRam());
		json.key("Disk").value(getDisk());
		json.key("_opt").value("");// TODO 数据的操作权限
		json.key("_opt2").value("");// TODO 数据的操作权限
		json.endObject();
	}

	public static String toJson(List<CourseVps> all) {
		JSONStringer json = new JSONStringer();
		json.object();
		
		json.key("rows").array();
		for (CourseVps item : all) {
			item.toJson(json);
		}
		json.endArray();
		json.endObject();
		return json.toString();
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public Long getServergroupsId() {
		return servergroupsId;
	}

	public void setServergroupsId(Long servergroupsId) {
		this.servergroupsId = servergroupsId;
	}

	public int getRunningStat() {
		return runningStat;
	}

	public void setRunningStat(int runningStat) {
		this.runningStat = runningStat;
	}
	private int  maxMem;
	public int getMaxMem() {
		return maxMem;
	}

	public CourseVps setMaxMem(int maxMem) {
		this.maxMem = maxMem;return this;
	}
	private String name;
	private String state;
	
	public String getState() {
		if(state==null)return "";
		return state;
	}

	public CourseVps setState(String state) {
		this.state = state;return this;
	}

	public String getName() {
		return name;
	}

	public CourseVps setName(String name) {
		this.name = name;return this;
	}

	public float getCpuStat() {
		return cpuStat;
	}

	public void setCpuStat(float cpuStat) {
		this.cpuStat = cpuStat;
	}

	public float getMemStat() {
		return memStat;
	}

	public void setMemStat(float memStat) {
		this.memStat = memStat;
	}

	public float getDiskStat() {
		return diskStat;
	}

	public void setDiskStat(float diskStat) {
		this.diskStat = diskStat;
	}
	
	public Vps toVps() {
		Vps vps = new Vps();
		vps.setId(id);
		vps.setAdditionalips(additionalips);
		vps.setBandwidth(bandwidth);
		vps.setBwdate(bwdate);
		vps.setBwusage(bwusage);
		vps.setConsole(console);
		vps.setCpu(cpu);
		vps.setCpuStat(cpuStat);
		vps.setCreateTime(createTime);
		vps.setDisk(disk);
		vps.setDiskStat(diskStat);
		vps.setDpass(dpass);
		vps.setIp(ip);
		vps.setMemStat(memStat);
		vps.setOsid(osid);
		vps.setOsname(osname);
		vps.setPort(port);
		vps.setProblem(problem);
		vps.setRam(ram);
		vps.setRunningStat(runningStat);
		vps.setServergroupsId(servergroupsId);
		vps.setServerid(serverid);
		vps.setShareip(shareip);
		vps.setStatus(status);
		vps.setUserid(userid);
		vps.setVmac(vmac);
		vps.setVpsid(vpsid);
		vps.setVpsname(vpsname);
		vps.setMaxMem(maxMem);
		vps.setUid(uid);
		vps.setName(name);
		vps.setState(state);
		return vps;
	}
	
	public static CourseVps parseVps(Vps vps) {
		CourseVps cv = new CourseVps();
		cv.setId(vps.getId());
		cv.setAdditionalips(vps.getAdditionalips());
		cv.setBandwidth(vps.getBandwidth());
		cv.setBwdate(vps.getBwdate());
		cv.setBwusage(vps.getBwusage());
		cv.setConsole(vps.getConsole());
		cv.setCpu(vps.getCpu());
		cv.setCpuStat(vps.getCpuStat());
		cv.setCreateTime(vps.getCreateTime());
		cv.setDisk(vps.getDisk());
		cv.setDiskStat(vps.getDiskStat());
		cv.setDpass(vps.getDpass());
		cv.setIp(vps.getIp());
		cv.setMemStat(vps.getMemStat());
		cv.setOsid(vps.getOsid());
		cv.setOsname(vps.getOsname());
		cv.setPort(vps.getPort());
		cv.setProblem(vps.getProblem());
		cv.setRam(vps.getRam());
		cv.setRunningStat(vps.getRunningStat());
		cv.setServergroupsId(vps.getServergroupsId());
		cv.setServerid(vps.getServerid());
		cv.setShareip(vps.getShareip());
		cv.setStatus(vps.getStatus());
		cv.setUserid(vps.getUserid());
		cv.setVmac(vps.getVmac());
		cv.setVpsid(vps.getVpsid());
		cv.setVpsname(vps.getVpsname());
		cv.setMaxMem(vps.getMaxMem());
		cv.setUid(vps.getUid());
		cv.setName(vps.getName());
		cv.setState(vps.getState());
		return cv;
	}
}
