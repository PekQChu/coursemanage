package com.shu.cpa.utility;

import javax.sound.midi.SysexMessage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class IpPortServer {
	public static String frpServer = "106.15.102.141";
	public static String apiServerUrl = "http://10.58.140.233:5000/";
	public static int maxPort=9999;//设置最大端口限制
	public static int minPort=8500;//设置最小端口限制
	
	public static NetResult getIpPortStatus(int outPort){
		NetResult r=new NetResult();
		try {
			String rString=HttpRequest.sendGet("http://"+frpServer+":7500/api/proxies", "");
			try {
				JSONObject o=JSONObject.parseObject(rString);
				if(o.getIntValue("code")==0){
					r.status=0;
					JSONArray arr=o.getJSONArray("proxies");
					for(int i=0;i<arr.size();i++){
						o=arr.getJSONObject(i);
						if(o.getIntValue("listen_port")==outPort){
							r.result=o;
							return r;
						}
					}
					r.status=-1;
					r.result="未找到相关端口";
					return r;
				}else{
					r.status=-1;
					r.result="获取状态异常";
					return r;
				}
			} catch (Exception e) {
				// TODO: handle exception
				r.status = -1;
				r.result = "调用API异常："+rString;
				return r;
			}
		} catch (Exception e) {
			r.status = -1;
			r.result = "服务器异常："+e.getMessage();
			return r;
		}
	}
	public static NetResult deleteIpPort(int outPort){
//		如何判断用户
		NetResult r=new NetResult();
		if(outPort>=minPort&&outPort<=maxPort){
			try {
				String rString=HttpRequest.sendGet(apiServerUrl+"deletePort", "outPort="+outPort+"");
				System.err.println(rString);
				try {
					JSONObject o=JSONObject.parseObject(rString);
					if(o.getIntValue("status")==0){
						r.status=0;
						r.result=o.get("msg");
						return r;
					}else{
						r.status=-1;
						r.result=o.get("msg");
						return r;
					}
				} catch (Exception e) {
					// TODO: handle exception
					r.status = -1;
					r.result = "调用API异常："+rString;
					return r;
				}
			} catch (Exception e) {
				r.status = -1;
				r.result = "服务器异常："+e.getMessage();
				return r;
			}
		}else{
			r.status=-1;
			r.result="外部端口超过规定范围!";
		}
		return r;
	}
	public static NetResult addIpPort(int inPort,String inIp,int outPort){
		NetResult r=new NetResult();
		if(outPort>=minPort&&outPort<=maxPort){
			if(isIp(inIp)){
				try {
					String rString=HttpRequest.sendGet(apiServerUrl+"addPort", "ip="+inIp+"&port="+inPort+"&outPort="+outPort+"");
					try {
						JSONObject o=JSONObject.parseObject(rString);
						if(o.getIntValue("status")==0){
							r.status=0;
							r.result=o.get("msg");
							return r;
						}else{
							r.status=-1;
							r.result=o.get("msg");
							return r;
						}
					} catch (Exception e) {
						// TODO: handle exception
						r.status = -1;
						r.result = "调用API异常："+rString;
						return r;
					}
				} catch (Exception e) {
					r.status = -1;
					r.result = "服务器异常："+e.getMessage();
					return r;
				}
			}else{
				r.status=-1;
				r.result="输入IP格式非法!";
				return r;
			}
		}
		else{
			r.status=-1;
			r.result="外部端口超过规定范围!";
			return r;
		}
	}
	public static boolean isIp(String ip){//判断是否是一个IP
		boolean b = false;
		ip = trimSpaces(ip);
		if(ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
		String s[] = ip.split("\\.");
		if(Integer.parseInt(s[0])<255)
		if(Integer.parseInt(s[1])<255)
		if(Integer.parseInt(s[2])<255)
		if(Integer.parseInt(s[3])<255)
		b = true;
		}
		return b;
		}
		public static String trimSpaces(String ip){//去掉IP字符串前后所有的空格
		while(ip.startsWith(" ")){
		ip= ip.substring(1,ip.length()).trim();
		}
		while(ip.endsWith(" ")){
		ip= ip.substring(0,ip.length()-1).trim();
		}
		return ip;
		}
		public static void main(String[] args) {
			
			System.err.println(getIpPortStatus(8501).result);
//			System.err.println(addIpPort(3389, "10.1.132.4", 9996).result);
//			System.err.println(deleteIpPort(8887).result);
		}
}
