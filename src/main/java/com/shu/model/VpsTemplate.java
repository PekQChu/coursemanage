package com.shu.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity 
@Table(name = "VpsTemplate")
public class VpsTemplate {
	public static int TEMPLATE=0;
	public static int BACKTEMPLATE=1;
	public VpsTemplate() {  
        super();  
        ensureId();
        create_time=new Date();
        isableuse=1;
        type=TEMPLATE;
        cpu=1;
        mem=1024;
        
    }
	private VpsTemplate ensureId() {
		this.setId(UUID.randomUUID().toString());
		return this;
	}
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "sid",  nullable = true)  
	private long sid;
//	对应xen里面的模板ID
	@Column(name = "osid",  nullable = true)  
	private int osid;
//	操作系统名
	@Column(name = "osname", length = 100, nullable = true)  
	private String osname;
//	内置软件
	@Column(name = "software", length = 100, nullable = true)  
	private String software;
//	模板名称
	@Column(name = "templatetitle", length = 100, nullable = true)  
	private String templatetitle;
//	CPU个数
	@Column(name = "cpu",  nullable = true)  
	private int cpu;
//	内存大小
	@Column(name = "mem",  nullable = true)  
	private int mem;
	@Column(name = "type",  nullable = true)  
	private int type;
//	存储大小
	@Column(name = "dis",  nullable = true)  
	private long dis;
	//使用权限  0学生不可用||1学生可用
	@Column(name = "isableuse",  nullable = true)  
	private int isableuse;
//	创建时间
	@Column(name="createtime", insertable = true, updatable = true, nullable = false)
	@Temporal(value=TemporalType.TIMESTAMP)  
	private Date create_time;
//	创建人ID
	@Column(name = "creatid", length = 100, nullable = true)  
	private String creatid;
	@Column(name = "backId", length = 100, nullable = true)  
	private int backId;
////	使用者级别

	//	简介
	@Column(name = "description", nullable = true)
	private String description ;
	public String getTemplatetitle() {
		return templatetitle;
	}
	public VpsTemplate setTemplatetitle(String templatetitle) {
		this.templatetitle = templatetitle;return this;
	}
	public String getDescription() {
		return description;
	}
	public static String getTypeStr(int type) {
		if(type==0){
			return "系统模板";
		}else{
			return "备份模板";
		}
	}
	public String getTypeStr() {
		return getTypeStr(type);
	}
	public VpsTemplate setDescription(String description) {
		this.description = description;return this;
	}
	public String getId() {
		return id;
	}
	public VpsTemplate setId(String id) {
		this.id = id;return this;
	}
	public int getOsid() {
		return osid;
	}
	public VpsTemplate setOsid(int osid) {
		this.osid = osid;return this;
	}
	public String getOsname() {
		return osname;
	}
	public VpsTemplate setOsname(String osname) {
		this.osname = osname;return this;
	}

	public int getCpu() {
		return cpu;
	}
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
	public VpsTemplate setCPU(int cPU) {
		cpu = cPU;return this;
	}
	public long getMem() {
		return mem;
	}
	public VpsTemplate setMem(int mem) {
		this.mem = mem;return this;
	}
	public long getDis() {
		return dis;
	}
	public VpsTemplate setDis(long dis) {
		this.dis = dis;return this;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public VpsTemplate setCreate_time(Date create_time) {
		this.create_time = create_time;return this;
	}
	public String getCreatid() {
		return creatid;
	}
	public VpsTemplate setCreatid(String id) {
		this.creatid = id;return this;
	}
	
	public int getIsableuse() {
		return isableuse;
	}
	public VpsTemplate setIsableuse(int isableuse) {
		this.isableuse = isableuse;return this;
	}

	public int getType() {
		return type;
	}
	public VpsTemplate setType(int type) {
		this.type = type;return this;
	}
	public int getBackId() {
		return backId;
	}
	public VpsTemplate setBackId(int backId) {
		this.backId = backId;return this;
	}
	public long getSid() {
		return sid;
	}
	public VpsTemplate setSid(long sid) {
		this.sid = sid;return this;
	}
	public String getSoftware() {
		return software;
	}
	public VpsTemplate setSoftware(String software) {
		this.software = software;return this;
	} 
	private int useSum;
	public int getUseSum() {
		return useSum;
	}
	public void setUseSum(int useSum) {
		this.useSum = useSum;
	}
	public VpsTemplate setUseSumADD() {
		this.useSum = useSum+1;return this;
	}
	
	
	
}
