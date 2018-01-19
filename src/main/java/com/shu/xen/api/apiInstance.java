package com.shu.xen.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.ModelMap;

import com.shu.cpa.utility.utility;
import com.txcourse.model.CourseVps;
import com.txcourse.service.VpsService;
import com.shu.cpa.utility.NetResult;

public class apiInstance {
	public static String[] toVPSA(String s) {
		if (s == null)
			return new String[0];
		s = s.replace("|", ";");
		String[] temps = s.split(";");
		List<String> rtn = new ArrayList<String>();
		for (String t : temps) {
			if (t == null)
				continue;
			t = t.trim();
			if (t.length() == 0)
				continue;
			try {
				rtn.add(t);
			} catch (Exception e) {
				return new String[0];
			}
		}
		return rtn.toArray(new String[rtn.size()]);
	}

	/**
	 * @创建vps
	 * @param vps
	 * @return成功返回Vps 对象
	 */
	public static NetResult CreateVps(Vps vps, int imageid) {
		NetResult r = new NetResult();
		System.err.println(vps.toJson());
		if (vps == null) {
			r.status = -1;
			r.result = "vps信息为空";
			return r;
		}
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		// 指定VPS 所属的用户编号
		if (vps.getUserid() != null && vps.getUserid() > 0L) {
			pair.add(new BasicNameValuePair("userid", "" + vps.getUserid()));
		} else {
			vps.setProblem("VPS 所属的用户编号有误");
		}
		// VPS 的vCPU 数量
		if (vps.getCpu() > 0L) {
			pair.add(new BasicNameValuePair("cpu", "" + vps.getCpu()));
		} else {
			vps.setProblem("VPS 的vCPU 数量有误");
		}

		// VPS 的内存大小(MB)
		if (vps.getRam() > 0L) {
			pair.add(new BasicNameValuePair("ram_max", "" + vps.getMaxMem()));
			pair.add(new BasicNameValuePair("ram_min", "" + vps.getRam()));
			// pair.add(new BasicNameValuePair("Ram", "" + vps.getMaxMem()));
		} else {
			vps.setProblem("VPS 的内存大小(MB)有误");
		}
		if (imageid > 0) {
			pair.add(new BasicNameValuePair("imageid", "" + imageid));
		}
		// VPS 的总硬盘容量(GB)
		if (vps.getDisk() > 0L) {
			pair.add(new BasicNameValuePair("Disk", "" + vps.getDisk()));
		} else {
			vps.setProblem("VPS 的总硬盘容量(GB)有误");
		}

		// VPS 主机的别名，5~50 个字母+数字组合
		if (vps.getVpsname() != null && vps.getVpsname().trim().matches("[[A-Z]|[a-z]|[0-9]]{5,50}")) {
			pair.add(new BasicNameValuePair("vpsname", "" + vps.getVpsname().trim()));
		} else {
			vps.setProblem("VPS 主机的别名，5~50 个字母+数字组合有误");
		}

		// 要安装的系统模板编号
		if (vps.getOsid() > 0) {
			pair.add(new BasicNameValuePair("osid", "" + vps.getOsid()));
		} else {
			vps.setProblem("安装的系统模板编号有误");
		}

		// 创建VPS 的目标服务器
		if (vps.getServerid() != null && vps.getServerid() > 0L) {
			pair.add(new BasicNameValuePair("serverid", "" + vps.getServerid().intValue()));
		} else {
			vps.setProblem("创建VPS 的目标服务器有误");
		}
		if (vps.getProblem() != null && vps.getProblem().length() > 0) {
			r.status = -1;
			r.result = vps.getProblem();
			return r;
		}

		// VPS 每月可用的流量大小(GB)，0 为不限( 默认值0)
		if (vps.getBandwidth() != null) {
			pair.add(new BasicNameValuePair("bandwidth", "" + vps.getBandwidth()));
		}
		// VPS 的端口大小(Mbps)，0 为不限( 默认值0)
		if (vps.getPort() != null) {
			pair.add(new BasicNameValuePair("port", "" + vps.getPort()));
		}
		// 指定VPS 的主Ip 地址，为空时由系统自动指定
		if (vps.getIp() != null && vps.getIp().length() > 8) {
			pair.add(new BasicNameValuePair("ip", "" + vps.getIp()));
		}
		// 指定VPS 的额外IP 地址
		if (vps.getAdditionalips() != null && vps.getAdditionalips().length() > 8) {
			pair.add(new BasicNameValuePair("additionalips", "" + vps.getAdditionalips()));
		}
		// 是否为共享IP，0 为独立IP，1 为共享IP( 默认值0)
		if (vps.getShareip() > 0) {
			pair.add(new BasicNameValuePair("shareip", "1"));
		}
		// 设置备份默认数
		pair.add(new BasicNameValuePair("backup_snapshot", "4"));
		pair.add(new BasicNameValuePair("backup_full", "4"));

		apiBase api = new apiBase();
		String statedata = null;
		try {
			// 返回数据成功格式statedata: 0|5297|192.168.1.222
			statedata = api.Request("vps_create", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null) {
			String[] strarr = toVPSA(statedata);
			for (int i = 0; i < strarr.length; i++) {
				System.err.println(strarr[i]);
			}
			if (strarr != null && strarr.length > 2 && strarr[0].equals("0")) {
				r.status = 0;
				vps.setIp(strarr[2]);
				vps.setVpsid(Long.parseLong(strarr[1]));
				r.result = vps;
			} else {
				r.status = -1;
				vps.setProblem(statedata);
				if (statedata != null && statedata.indexOf("Insufficient server IPs") > -1) {
					statedata = statedata.replace("Insufficient server IPs", "云主机没有足够的IP");
					vps.setProblem(statedata);
				}
				r.result = vps.getProblem();
			}
		}
		return r;
	}

	//
	// 获取vps状态
	// 成功返回Vps 对象
	public static NetResult GetVpsStat(Vps vps) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		return getVps(vps);
	}

	public static NetResult GetVpsRunningStat(String vpsid) {
		apiBase api = new apiBase();
		NetResult r = new NetResult();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vpsid));
		String statedata = null;
		try {
			// 返回数据成功格式statedata:
			// setupfailed|{"bwusage":"0.000","vos":"CentOS 6
			// 32bit","backuptask":"0","ostemplate":""}
			statedata = api.Request("vps_state", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("|") >= 0) {
			statedata = statedata.substring(0, statedata.indexOf("|"));
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	// 运行状态@setupfailed：系统安装发生错误！@running:正在运行中@halted:关机状态@setup:正在安装配置中@-1:主机未找到
	// 成功返回RunningStat 对象
	public static NetResult GetVpsRunningStat(Vps vps) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		String statedata = null;
		try {
			// 返回数据成功格式statedata:
			// setupfailed|{"bwusage":"0.000","vos":"CentOS 6
			// 32bit","backuptask":"0","ostemplate":""}
			statedata = api.Request("vps_state", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("|") >= 0) {
			statedata = statedata.substring(0, statedata.indexOf("|"));
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	public static NetResult vps_opt(String vpsid, String opt_state) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vpsid));
		String statedata = null;
		try {
			statedata = api.Request(opt_state, pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		System.err.println("statedata:" + statedata);
		if (statedata.contains("0")) {
			r.status = 0;
			r.result = "操作成功";
			return r;
		}
		if (statedata != null && statedata.indexOf("0|") >= -1) {
			r.status = statedata.indexOf("0|");
			r.result = statedata;
		}
		return r;
	}

	public static NetResult vps_opt(Vps vps, String opt_state) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		String statedata = null;
		try {
			statedata = api.Request(opt_state, pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		System.err.println("statedata:" + statedata);
		if (statedata.contains("0")) {
			r.status = 0;
			r.result = "操作成功";
			return r;
		}
		if (statedata != null && statedata.indexOf("0|") >= -1) {
			r.status = statedata.indexOf("0|");
			r.result = statedata;
		}
		return r;
	}

	//
	// 启动vps
	// 成功返回Vps 对象
	public static NetResult StartVps(Vps vps) {
		return vps_opt(vps, "vps_start");
	}

	//
	// 关闭vps
	// 成功返回Vps 对象
	public static NetResult ShutDown(Vps vps) {
		return vps_opt(vps, "vps_hshutdown");
	}

	public static NetResult ShutDown(String vps) {
		return vps_opt(vps, "vps_hshutdown");
	}

	//
	// 重启vps
	// 成功返回Vps 对象
	public static NetResult RestartVps(Vps vps) {
		return vps_opt(vps, "vps_hreboot");
	}

	//
	// 重装vps
	// 成功返回Vps 对象
	public static NetResult ReInstallVps(Vps vps) {
		return null;
	}

	//
	// 获取vps操作日志
	// 成功返回List<String> 对象
	public static void GetVpsLogs(Vps vps) {
		NetResult r1 = apiInstance.Task_list(vps, 0, 0);
		String sJson = (String) r1.result;
		JSONArray jsonArray = new JSONArray(sJson);
		int iSize = jsonArray.length();
		System.out.println("Size:" + iSize);
		for (int i = 0; i < iSize; i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			System.out.println("[i]tid=" + jsonObj.get("tid"));
			System.out.println("[时间]=" + jsonObj.get("ttime"));
			JSONObject action = (JSONObject) jsonObj.get("tinfo");
			System.out.println("[操作 ]:" + action.get("task_action"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			// 转换解析成中文
			String msg1 = (String) action.get("task_action");
			if (((String) action.get("task_action")).equals("create_step_1")) {
				msg1 = "创建云主机";
			}
			if (msg1.equals("create_step_2")) {
				msg1 = "创建云主机成功！";
			}
			if (msg1.equals("reload_step_1")) {
				msg1 = "重装系统！";
			}
			if (msg1.equals("reload_step_2")) {
				msg1 = "重装系统成功！";
			}
			if (msg1.equals("change_password")) {
				msg1 = "修改密码";
			}
		}

		// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// String date = sdf.format(new Date(now*1000));
		// long now=Long.parseLong((String) jsonObj.get("ttime"));
		// String date = sdf1.format(new Date(now*1000L));
	}

	// public static NetResult GetVpsLogs(Vps vps)
	// {
	// return null;
	// }

	//
	// 备份vps
	// 成功返回备份ID（int）
	// bktype:false 快照备份， true 完整备份
	public static NetResult BackupVps(Vps vps, String bkname, int type) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		if (bkname == null) {
			r.status = -1;
			r.result = "备份名称为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		pair.add(new BasicNameValuePair("subaction", "backup"));
		pair.add(new BasicNameValuePair("bktype", type + ""));
		pair.add(new BasicNameValuePair("bkname", bkname));
		String statedata = null;
		try {
			statedata = api.Request("vm_backup", pair, new BasicResponseHandler());
			System.out.println("备份返回的数据：" + statedata);
			r.result = statedata;
			System.out.println(statedata);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		// if (statedata != null && statedata.indexOf("0|") > -1) {subSequence
		System.out.println("statedata=" + statedata + "");
		System.out.println(statedata.charAt(0) + "状态");
		if (statedata != null && statedata.charAt(0) == '0') {
			r.status = 0;
			System.out.println("返回的结果打印" + r.result);
		}
		if (statedata.charAt(0) != '0') {
			r.status = -1;
			r.result = statedata.subSequence(3, statedata.length() - 1);
		}
		System.out.println("备份状态为：" + r.status);
		return r;
	}

	//
	// 还原vps
	// 成功返回Vps对象
	public static NetResult RestoreVps(Vps vps, int backupId) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		pair.add(new BasicNameValuePair("subaction", "revert"));
		pair.add(new BasicNameValuePair("bkid", backupId + ""));
		String statedata = null;
		try {
			statedata = api.Request("vps_backup", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("0|") >= -1) {
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	//
	// CESHI 查询备份
	public static NetResult findback(String vpsid) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vpsid));
		pair.add(new BasicNameValuePair("subaction", "list"));
		String statedata = null;
		try {
			statedata = api.Request("vps_backup", pair, new BasicResponseHandler());
			System.out.println(statedata);
			r.result = statedata;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (!utility.EmptyString(statedata) && statedata.length() > 10 && statedata.charAt(0) == '[') {
			r.status = 0;
			System.out.println(statedata);
		} else {
			r.status = -1;
			r.result = statedata;
		}
		return r;
	}

	//
	// 删除ups备份
	// 成功返回0
	public static NetResult DelVpsBackup(Vps vps, int backupId) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		pair.add(new BasicNameValuePair("subaction", "del"));
		pair.add(new BasicNameValuePair("bkid", backupId + ""));
		String statedata = null;
		try {
			statedata = api.Request("vps_backup", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("0|") >= -1) {
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	//
	// 删除vps
	// 成功返回0
	public static NetResult DelVps(Vps vps) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		String statedata = null;
		try {
			statedata = api.Request("vps_delete", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("0|") >= -1) {
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	//
	// 更新vps
	// 成功返回vps对象
	public static NetResult UpdateVps(Vps vps) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		boolean bo = false;
		// 指定VPS 所属的用户编号
		if (vps.getUserid() != null && vps.getUserid() > 0L) {
			pair.add(new BasicNameValuePair("userid", "" + vps.getUserid()));
			bo = true;
		}
		// VPS 的vCPU 数量
		if (vps.getCpu() > 0L) {
			pair.add(new BasicNameValuePair("Cpu", "" + vps.getCpu()));
			pair.add(new BasicNameValuePair("cpu", "" + vps.getCpu()));
			bo = true;
		}
		// VPS 的内存大小(MB)
		if (vps.getRam() > 256L && vps.getMaxMem() > 256) {
			pair.add(new BasicNameValuePair("Ram", "" + vps.getMaxMem()));
			pair.add(new BasicNameValuePair("ram_max", "" + vps.getMaxMem()));
			pair.add(new BasicNameValuePair("ram_min", "" + vps.getRam()));
			bo = true;
		}
		// VPS 的总硬盘容量(GB)
		if (vps.getDisk() > 5L) {
			pair.add(new BasicNameValuePair("Disk", "" + vps.getDisk()));
			bo = true;
		}
		// VPS 主机的别名，5~50 个字母+数字组合
		if (vps.getVpsname() != null && vps.getVpsname().trim().length() > 5 && vps.getVpsname().trim().length() < 50) {
			pair.add(new BasicNameValuePair("vpsname", "" + vps.getVpsname().trim()));
			bo = true;
		}
		// VPS 状态： -3 超流量停机 -2 超流量运行 -1 欠费停机 0 安装中 1 锁定 2 正常
		if (vps.getStatus() != null) {
			pair.add(new BasicNameValuePair("status", "" + vps.getStatus()));
			bo = true;
		}
		// VPS 每月可用的流量大小(GB)，0 为不限( 默认值0)
		if (vps.getBandwidth() != null) {
			pair.add(new BasicNameValuePair("bandwidth", "" + vps.getBandwidth()));
			bo = true;
		}
		// VPS 的端口大小(Mbps)，0 为不限( 默认值0)
		if (vps.getPort() != null) {
			pair.add(new BasicNameValuePair("port", "" + vps.getPort()));
			bo = true;
		}
		// 指定VPS 的主Ip 地址，为空时由系统自动指定
		if (vps.getIp() != null) {
			pair.add(new BasicNameValuePair("ip", "" + vps.getIp()));
			bo = true;
		}
		// 指定VPS 的额外IP 地址
		if (vps.getAdditionalips() != null) {
			pair.add(new BasicNameValuePair("additionalips", "" + vps.getAdditionalips()));
			bo = true;
		}
		// 是否有更新的信息
		if (!bo) {
			r.status = -1;
			r.result = "暂无可更新的信息";
		}
		String statedata = null;
		try {
			statedata = api.Request("vps_update", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("0|") > -1) {
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	// 重装方式： 0全盘格式化， 1只格式化系统盘
	// 更换vps操作系统
	// 成功返回Vps 对象
	public static NetResult ChangeVpsOS1(Vps vps, int osId, int rot) {
		NetResult r = new NetResult();
		if (vps.getVpsid() == null || vps.getVpsid() < 1L || osId < 1 || osId <= 1) {
			r.status = -1;
			r.result = "Vpsid信息为空或操作系统有误";
			return r;
		}
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		pair.add(new BasicNameValuePair("osid", "" + osId));
		if (rot == 0) {
			pair.add(new BasicNameValuePair("rot", "0"));
		} else {
			pair.add(new BasicNameValuePair("rot", "1"));
		}
		pair.add(new BasicNameValuePair("mothod", "0"));

		String statedata = null;
		try {
			apiBase api = new apiBase();
			statedata = api.Request("vm_reload", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		System.err.println("重装返回:" + statedata);
		if (statedata != null && statedata.indexOf("-1|") < 0 && statedata.length() > 1) {
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	//
	// 获取VPS详情
	// 成功返回Vps 对象
	public static NetResult getVps(Vps vps) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		String statedata = null;
		try {
			statedata = api.Request("vps_info", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}

		if (statedata != null && statedata.indexOf("-1|") < 0 && statedata.length() > 1) {
			JSONArray jsonList = new JSONArray(statedata);
			if (jsonList != null && jsonList.length() == 1) {
				r.result = getVps(jsonList.getJSONObject(0), vps);
			}
		}
		return r;
	}

	/*
	 * @param chartype 图表数据类型{1:CPU,2:空闲内存，3：磁盘读写，4：网卡流量}
	 * 
	 * @param chartinterval
	 * 图表数据采样间隔时间{5:近10分钟内数据,60:近120分钟内数据，3600：近24小时内数据，86400：近1个月内数据
	 * ，25920000：近3个月内数据 }
	 */// 成功返回格式：一个基于 JQChar 图表插件的 Javascript 脚本文件 (.js)
	public static NetResult Vps_stat(Vps vps, int chartype, int chartinterval) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		if (chartype == 1 || chartype == 2 || chartype == 3 || chartype == 4) {
			pair.add(new BasicNameValuePair("chartype", chartype + ""));
		} else {
			r.status = -1;
			r.result = "图表数据类型有误";
			return r;
		}
		if (chartinterval == 5 || chartinterval == 60 || chartinterval == 3600 || chartinterval == 86400
				|| chartinterval == 2592000) {
			pair.add(new BasicNameValuePair("chartinterval", chartinterval + ""));
		} else {
			r.status = -1;
			r.result = "图表数据采样间隔时间有误";
			return r;
		}

		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		String statedata = null;
		try {
			statedata = api.Request("vps_stat", pair, new BasicResponseHandler());
			if (statedata != null && statedata.indexOf("-1|") < 0) {
				r.status = 0;
				r.result = statedata;
			} else {
				r.status = -1;
				r.result = statedata;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * @ttype:类型:1:创建VPS,2:重装VPS,6:一般任务(默认：0)
	 * @return
	 */
	/**
	 * @param vps
	 * @param taskId
	 *            :进程Id(默认：0)
	 * @param ttype类型
	 *            :1:创建VPS,2:重装VPS,3:系统任务,4:备份VPS,5:还原VPS,6:一般任务(默认：0)
	 * @return操作记录，云主机的创建记录等等进程进度
	 * @tstatus:0:等待执行,1:正在执行,2:正在验证,3:成功完成,4:发生错误,5:执行进度
	 */
	public static NetResult Task_list(Vps vps, int taskId, int ttype) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", ""));
		pair.add(new BasicNameValuePair("taskId", taskId + "3463"));
		pair.add(new BasicNameValuePair("ttype", ttype + ""));
		String statedata = null;
		try {
			statedata = api.Request("task_list", pair, new BasicResponseHandler());
			if (statedata != null && statedata.indexOf("-1|") < 0) {
				r.status = 0;
				if (utility.EmptyString(statedata)) {
					statedata = "[]";
				}
				r.result = statedata;
			} else {
				r.status = -1;
				r.result = statedata;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		return r;
	}

	//重置密码， windows需传新密码，linux传null
	public static NetResult repass(Vps vps, String newPwd) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		if (newPwd != null) {
			pair.add(new BasicNameValuePair("username", "Administrator"));
			pair.add(new BasicNameValuePair("password", newPwd));
		}
		String statedata = null;
		try {
			statedata = api.Request("vps_resetps", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		System.out.println(statedata);
		System.out.println(statedata.indexOf("|"));
		String test = "1a2b3c,456";
		System.out.println(test.indexOf(","));
		if (statedata != null && statedata.length() > 10) {

			r.status = -1;
			r.result = statedata;
			statedata = statedata.substring(0, statedata.indexOf("|"));
			System.out.println(statedata);
		} else {
			r.status = 0;
			r.result = "修改成功！";
		}
		return r;
	}

	/**
	 * @param 返回vps信息
	 * @return
	 */
	public static Vps getVps(JSONObject jsonb, Vps vps) {
		if (jsonb != null) {
			vps.setVpsid(jsonb.optLong("vid"));
			vps.setCpu(jsonb.optInt("vcpu"));
			vps.setIp(jsonb.optString("vip"));
			vps.setVmac(jsonb.optString("vmac"));
			vps.setRam(jsonb.optInt("vram"));
			vps.setDisk(jsonb.optInt("vdisk"));
			vps.setDpass(jsonb.optString("dpass"));
			vps.setAdditionalips(jsonb.optString("vadditionalips"));
			vps.setBandwidth(jsonb.optLong("vbandwidth"));
			vps.setPort(jsonb.optLong("vport"));
			vps.setOsname(jsonb.optString("vos"));
			vps.setVpsname(jsonb.optString("vname"));
			vps.setServerid(jsonb.optLong("sid"));
			vps.setStatus(jsonb.optInt("vstatus"));
			vps.setUserid(jsonb.optLong("uid"));
			vps.setConsole(jsonb.optString("console"));
			return vps;
		}
		return null;
	}

	// 读取模板
	public static NetResult getmodel(int serverid) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("serverid", "" + serverid));
		String statedata = null;
		try {
			statedata = api.Request("server_os_templates", pair, new BasicResponseHandler());
			System.out.println(statedata);
			r.result = statedata;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("0|") > -1) {
			r.status = 0;

			System.out.println(statedata);
		}
		return r;
	}

	public static NetResult getmodel(int serverid, int osid) {
		NetResult r = new NetResult();
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("serverid", "" + serverid));
		pair.add(new BasicNameValuePair("osid", "" + 2));
		pair.add(new BasicNameValuePair("osids", "" + 2));
		String statedata = null;
		try {
			statedata = api.Request("server_os_templates", pair, new BasicResponseHandler());
			System.out.println(statedata);
			r.result = statedata;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if (statedata != null && statedata.indexOf("0|") > -1) {
			r.status = 0;

			System.out.println(statedata);
		}
		return r;
	}

	public static NetResult VNC(Vps vps, String con) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		pair.add(new BasicNameValuePair("subaction", con));
		String statedata = null;
		try {
			statedata = api.Request("vps_console", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		if ((statedata != null && statedata.indexOf("-1|") < 0 && statedata.length() > 1)) {
			r.status = -1;
			r.result = statedata;
			statedata = statedata.substring(0, statedata.indexOf("|"));
			System.out.println(statedata);
		} else {
			r.status = 0;
			r.result = statedata;
		}
		return r;
	}

	// vps_console
	public static NetResult vps_console(Vps vps, String str) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		pair.add(new BasicNameValuePair("subaction", str));
		String statedata = null;
		try {
			statedata = api.Request("vps_console", pair, new BasicResponseHandler());
			if (statedata != null && statedata.indexOf("-1|") < 0) {
				r.status = 0;
				if (utility.EmptyString(statedata)) {
					statedata = "[]";
				}
				r.result = statedata;
			} else {
				r.status = -1;
				r.result = statedata;
			}
		} catch (ClientProtocolException e) {
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		return r;
	}

	// * @param ttype类型
	// * :1:创建VPS,2:重装VPS,3:系统任务,4:备份VPS,5:还原VPS,6:一般任务(默认：0)
	// * @return操作记录，云主机的创建记录等等进程进度
	// * @tstatus:0:等待执行,1:正在执行,2:正在验证,3:成功完成,4:发生错误,5:执行进度
	// 获取任务进程
	public static NetResult vps_task(Vps vps) {
		NetResult r = new NetResult();
		if (vps == null || vps.getVpsid() == null || vps.getVpsid() < 1L) {
			r.status = -1;
			r.result = "Vpsid信息为空";
			return r;
		}
		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		// pair.add(new BasicNameValuePair("vpsid", vps.getVpsid().toString()));
		System.out.println("到这一步了没");
		pair.add(new BasicNameValuePair("taskid", "3459"));
		// pair.add(new BasicNameValuePair("ttype",""));
		// pair.add(new BasicNameValuePair("serverid","4"));
		// pair.add(new BasicNameValuePair("tstatus",""));
		String statedata = null;
		try {
			statedata = api.Request("listtask_list", pair, new BasicResponseHandler());
			if (statedata != null && statedata.indexOf("-1|") < 0) {
				r.status = 0;
				if (utility.EmptyString(statedata)) {
					statedata = "[]";
				}
				r.result = statedata;
			} else {
				r.status = -1;
				r.result = statedata;
			}
		} catch (ClientProtocolException e) {
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		return r;
	}

	public static NetResult getLogs() {

		NetResult r1 = apiInstance.Task_list(null, 0, 0);
		return r1;
	}

	public static NetResult Task_list() {
		NetResult r = new NetResult();

		apiBase api = new apiBase();
		List<NameValuePair> pair = new ArrayList<NameValuePair>();

		pair.add(new BasicNameValuePair("serverid", "3"));
		String statedata = null;
		try {
			statedata = api.Request("task_list", pair, new BasicResponseHandler());
			if (statedata != null && statedata.indexOf("-1|") < 0) {
				r.status = 0;
				if (utility.EmptyString(statedata)) {
					statedata = "[]";
				}
				r.result = statedata;
			} else {
				r.status = -1;
				r.result = statedata;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			r.status = -1;
			r.result = e.getMessage();
			e.printStackTrace();
		}
		return r;
	}

	public static void main(String[] args) {
//		Vps vps = new Vps();
//		vps.setVpsid(13996l);
//		// NetResult r=repass(vps);
//		NetResult r = getVps(vps);
//
//		System.err.println(r.result);
		
		//获取所有模板信息
//		NetResult r = apiInstance.getmodel(44);
//		com.alibaba.fastjson.JSONArray ja = (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(r);
//		System.out.println(ja.toString());
		
		//获取单个VPS信息
//		Vps vps = new Vps();
//		vps.setVpsid(16223L);
//		NetResult r = getVps(vps);
//		System.out.println(com.alibaba.fastjson.JSONObject.toJSON(r).toString());
		
		//关机，开机，重启，重置密码，删除
//		Vps vps = new Vps();
//		vps.setVpsid(16223L);
//		NetResult r = repass(vps);
//		System.out.println(com.alibaba.fastjson.JSONObject.toJSON(r).toString());
		
//		r = getVps(vps);
//		System.out.println(com.alibaba.fastjson.JSONObject.toJSON(r).toString());
//		for (int i = 0; i < 10; i++) {
//			CourseVps vps = new CourseVps();
//			vps.setCpu(2);// VPS 的vCPU 数量
//			vps.setMaxMem(4096);// VPS 的内存大小(MB)  最大
//			vps.setDisk(40);// VPS 的总硬盘容量(GB)
//			vps.setVpsname("testtxCourse" + i);// VPS 主机的别名，5~50 个字母+数字组合
//			vps.setOsid(25);// 要安装的系统模板编号
//			NetResult result = VpsService.createCourseVps(vps);
//			System.out.println(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
//		}
		
		
	}
}
