package com.shu.xen.api;

import java.io.IOException;
import java.util.ArrayList;
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

import com.global.init.CMD;

public class apiBase {
	HttpClient httpclient;// 实例化提交实例，由于提交
	HttpPost post;// 实例化post实例，url是提交地址
	List<NameValuePair> pair;

	public static String API_URL = "api_url";
	public static String API_IDENTITY = "api_identity";
	public static String API_USERNAME = "api_username";
	public static String API_KEY = "api_key";
	String Url;
	String identity;
	String api_username;
	String api_key;

	public apiBase() {
		Url = CMD.XEN_API_URL;// "http://shu.51idc.com/process.aspx?c=api";
		identity = CMD.XEN_API_IDENTITY;// "system";
		api_username = CMD.XEN_API_USERNAME;// "d092de3651499deb640";
		api_key = CMD.XEN_API_KEY;// "asdasdak34j59080";
		httpclient = new DefaultHttpClient();// 实例化提交实例，由于提交
		post = new HttpPost(Url);// 实例化post实例，url是提交地址
		pair = new ArrayList<NameValuePair>();

		pair.add(new BasicNameValuePair("identity", identity));
		pair.add(new BasicNameValuePair("api_username", api_username));
		pair.add(new BasicNameValuePair("api_key", api_key));
		System.err.println(Url);
		System.err.println(identity);
		System.err.println(api_username);
		System.err.println(api_key);
	}

	public String Request(String action, List<NameValuePair> params, BasicResponseHandler handler)
			throws ClientProtocolException, IOException {
		pair.add(new BasicNameValuePair("action", action));
		pair.addAll(params);
		post.setEntity(new UrlEncodedFormEntity(pair, HTTP.UTF_8));// 设定提交数据的编码方式
		String vpsls = httpclient.execute(post, handler);// 提交数据并接受返回值
		System.err.println("vpsls:" + vpsls);
		return vpsls;
	}

	// 返回可开vps 的母机
	public Long getServerId(Vps v, Long servergroupsId) {
		List<VpsServer> listser = server_info(servergroupsId, null, null);
		if (listser != null && listser.size() > 0) {
			for (VpsServer vs : listser) {
				if (vs != null && vs.getSid() != null) {
					if (vs.getMaxcpu() >= v.getCpu() && vs.getAllowram() >= (v.getRam())
							&& vs.getAllowdisk() >= (v.getDisk())) {
						System.err.print("vpslsIPvs.getSid():" + vs.getSid());
						// 查询母机是否有可用IP
						apiBase api = new apiBase();
						List<String> ip_List = api.ip_List(0L, vs.getSid(), 0L);
						if (ip_List != null && ip_List.size() > 0) {
							return vs.getSid();
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param vpsid
	 *            ：返回指定 vpsid所有的 IP 数据
	 * 
	 * @param serverid
	 *            {服务器编号 不为空时；当参数 vpsid为空时，返回服务器所有 IP; 地当参数 vpsid为 0时，返回服务器所有 空闲 IP}
	 * @param ipid
	 *            不为 0时， 返回指定编号的 IP 数
	 * @return返回IP
	 */
	public List<String> ip_List(Long vpsid, Long serverid, Long ipid) {
		if (serverid == null && vpsid == null) {
			return null;
		}
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("ipid", "" + ipid));
		if (vpsid != null) {
			pair.add(new BasicNameValuePair("vpsid", "" + vpsid));
		}
		if (serverid != null) {
			pair.add(new BasicNameValuePair("serverid", "" + serverid));
		}
		String vpsls = null;
		try {
			vpsls = Request("ip_list", pair, new BasicResponseHandler());
			if (vpsls != null && vpsls != "0" && vpsls.indexOf("-1|") < 0) {
				JSONArray jsonList = new JSONArray(vpsls);
				if (jsonList != null && jsonList.length() > 0) {
					List<String> ip_List = new ArrayList<String>();
					for (int i = 0; i < jsonList.length(); i++) {
						JSONObject jsonb = jsonList.getJSONObject(i);
						if (jsonb != null) {
							String vid = jsonb.optString("vid");
							if ("0".equals(vid)) {
								ip_List.add(jsonb.optString("ip"));
							}
						}
					}
					return ip_List;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	//

	public List<String> ip_List1(Long serverid, Long blockid, String ip) {
		if (serverid == null) {
			return null;
		}
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("serverid", "" + serverid));
		if (blockid != null) {
			pair.add(new BasicNameValuePair("blockid", "" + blockid));
		}
		if (ip != null) {
			pair.add(new BasicNameValuePair("ip", "" + ip));
		}
		String vpsls = null;
		try {
			vpsls = Request("ip_list1", pair, new BasicResponseHandler());
			if (vpsls != null && vpsls != "0" && vpsls.indexOf("-1|") < 0) {
				JSONArray jsonList = new JSONArray(vpsls);
				if (jsonList != null && jsonList.length() > 0) {
					List<String> ip_List1 = new ArrayList<String>();
					for (int i = 0; i < jsonList.length(); i++) {
						JSONObject jsonb = jsonList.getJSONObject(i);
						if (jsonb != null) {
							String vid = jsonb.optString("vid");
							if ("0".equals(vid)) {
								ip_List1.add(jsonb.optString("ip"));
							}
						}
					}
					return ip_List1;
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	//
	/**
	 * @param servergroups
	 *            ：服务器组Id
	 * @param serverid
	 *            :服务器Id
	 * @param quantity
	 *            ：查询的数量
	 * @return返回服务器
	 */
	public List<VpsServer> server_info(Long servergroups, Long serverid, Long quantity) {
		if (servergroups == null && serverid == null && quantity == null) {
			return null;
		}
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		if (servergroups != null) {
			pair.add(new BasicNameValuePair("servergroups", "" + servergroups));
		}
		if (serverid != null) {
			pair.add(new BasicNameValuePair("serverid", "" + serverid));
		}
		if (quantity != null) {
			pair.add(new BasicNameValuePair("quantity", "" + quantity));
		}
		String vpsls = null;
		try {
			vpsls = Request("server_info", pair, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vpsls != null && vpsls.trim().length() > 0 && vpsls.indexOf("-1|") < 0) {
			System.err.println("服务器分组:" + vpsls);
			JSONArray jsonList = new JSONArray(vpsls);
			if (jsonList != null && jsonList.length() > 0) {
				List<VpsServer> server_info = new ArrayList<VpsServer>();
				for (int i = 0; i < jsonList.length(); i++) {
					server_info.add(getVpsServer(jsonList.getJSONObject(i)));
				}
				return server_info;
			}
		} else {
			System.err.println("服务器分组:" + vpsls);
		}
		return null;
	}

	/**
	 * @param jsonb返回vps服务器信息
	 * @return
	 */
	public VpsServer getVpsServer(JSONObject jsonb) {
		if (jsonb != null) {
			VpsServer vpsser = new VpsServer();
			vpsser.setSname(jsonb.optString("sname"));
			vpsser.setSid(jsonb.optLong("sid"));
			vpsser.setDid(jsonb.optLong("did"));
			vpsser.setSip(jsonb.optString("sip"));
			vpsser.setSstatus(jsonb.optString("sstatus"));
			vpsser.setMaxcpu(jsonb.optLong("maxcpu"));
			vpsser.setMaxram(jsonb.optLong("maxram"));
			vpsser.setMaxdisk(jsonb.optLong("maxdisk"));
			vpsser.setAllowram(jsonb.optLong("allowram"));
			vpsser.setAllowdisk(jsonb.optLong("allowdisk"));

			vpsser.setRack(jsonb.optString("rack"));
			return vpsser;
		}
		return null;
	}

	public static void main(String[] args) {
		// apiBase x1=new apiBase();
		// List<VpsServer> s=x1.server_info(null, 44l, null);
		// x1=new apiBase();
		// List<VpsServer> s1=x1.server_info(null, 41l, null);
	}
}
