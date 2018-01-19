package com.txcourse.service;

import java.awt.Window.Type;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.hibernate.engine.jdbc.spi.TypeSearchability;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.NetResult;
import com.shu.xen.api.Vps;
import com.shu.xen.api.apiInstance;
import com.txcourse.model.CourseVps;

public class VpsService {
	private static final int SERVERID = 3;
	private static final long USERID = 1L;
	
	public static CourseVps getVps(Long vpsid) {
		if (vpsid == null) {
			return null;
		}
		NetResult r = getVpsInfo(vpsid);
		if (r.status != 0) {
			return null;
		}
		Vps vps = (Vps)r.result;
		String runningState = (String) apiInstance.GetVpsRunningStat(String.valueOf(vpsid)).result;
		vps.setState(runningState);
		return CourseVps.parseVps(vps);
//		System.out.println(JSONObject.toJSONString(r.result.toString()));
//		JSONObject vpsJo = JSONObject.parseObject(JSONObject.toJSONString(r.result.toString()));
//		CourseVps vps = new CourseVps();
//		vps.setAdditionalips(vpsJo.getString("additionalips"));
//		vps.setBandwidth(vpsJo.getLong("bandwidth"));
//		vps.setConsole(vpsJo.getString("console"));
//		vps.setCpu(vpsJo.getIntValue("cpu"));
//		vps.setCpuStat(vpsJo.getFloatValue("cpuStat"));
//		vps.setDisk(vpsJo.getIntValue("disk"));
//		vps.setDiskStat(vpsJo.getFloatValue("diskStat"));
//		vps.setDpass(vpsJo.getString("dpass"));
//		vps.setIp(vpsJo.getString("ip"));
//		vps.setMemStat(vpsJo.getFloatValue("memStat"));
//		vps.setOsid(vpsJo.getIntValue("osid"));
//		vps.setOsname(vpsJo.getString("osname"));
//		vps.setPort(vpsJo.getLong("port"));
//		vps.setRam(vpsJo.getIntValue("ram"));
//		vps.setRunningStat(vpsJo.getIntValue("runningStat"));
//		vps.setServerid(vpsJo.getLong("serverid"));
//		vps.setShareip(vpsJo.getIntValue("shareip"));
//		vps.setStatus(vpsJo.getInteger("status"));
//		vps.setUserid(vpsJo.getLong("userid"));
//		vps.setVmac(vpsJo.getString("vmac"));
//		vps.setVpsid(vpsJo.getLong("vpsid"));
//		vps.setVpsname(vpsJo.getString("vpsname"));
//		vps.setMaxMem(vpsJo.getIntValue("maxMem"));
//		vps.setState(vpsJo.getString("state"));
//		return vps;
	}
	
	
	public static NetResult getVpsOsName(Long vpsid) {
		NetResult r = new NetResult();
		if (vpsid == null) {
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		r = getVpsInfo(vpsid);
		if (r.status != 0) {
			return null;
		}
		r.status = 0;
		r.result = CourseVps.parseVps((Vps)r.result).getOsname();
		return r;
	}
	
	
	//创建云主机
	/**
	 * long 
	 * @param CourseVps
	 * @return
	 */
	
	public static Long createCourseVps(CourseVps v) {
		Vps vps = v.toVps();
		vps.setServerid((long)SERVERID);
		vps.setUserid(USERID);// 指定VPS 所属的用户编号
		vps.setRam(4096);// VPS 的内存大小(MB)最小值
		NetResult r = new NetResult();
		r = apiInstance.CreateVps(vps, 0);
		if (r.status == 0) {
			Vps newVps = (Vps)r.result;
			return newVps.getVpsid();
		}
//		JSONObject vpsJo = JSON.parseObject(r.result.toString());
//		Long vpsid = Long.parseLong(vpsJo.getString("vpsid"));
		return 0L;
	}
	
	//获取所有模板
	public static NetResult getTemplates() {
		return apiInstance.getmodel(SERVERID);
	}
	
	//重置Linux云主机密码
	public static NetResult resetLinuxVpsPwd(Long vpsid) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.repass(vps, null);
	}
	
	//重置Windows云主机密码
	public static NetResult resetWindowsVpsPwd(Long vpsid, String newPwd) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		} else if (newPwd == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "新密码不能为空";
			return r;
		} else if (newPwd.length() < 6) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "新密码长度不能小于6位";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.repass(vps, newPwd);
	}
	
	//重启云主机
	public static NetResult restartVps(Long vpsid) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.RestartVps(vps);
	}
	
	//开启云主机
	public static NetResult startVps(Long vpsid) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.StartVps(vps);
	}
	
	//关闭云主机
	public static NetResult shutdownVps(Long vpsid) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.ShutDown(vps);
	}
	
	//销毁云主机
	public static NetResult destroyVps(Long vpsid) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.DelVps(vps);
	}
	
	//获取云主机信息
	public static NetResult getVpsInfo(Long vpsid) {
		if (vpsid == null) {
			NetResult r = new NetResult();
			r.status = -1;
			r.result = "vpsid不能为空";
			return r;
		}
		Vps vps = new Vps();
		vps.setVpsid(vpsid);
		return apiInstance.getVps(vps);
	}
}
