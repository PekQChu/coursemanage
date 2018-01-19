package com.txcourse.DAOImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.NetResult;
import com.shu.model.VpsTemplate;
import com.txcourse.DAO.VpsTemplateDAO;
import com.txcourse.service.VpsService;

/** 
* @author :liq 
* @version 创建时间：2017年11月29日 下午2:07:52 
* 类说明 查询模板id实现类
*/
public class VpsTemplateDAOImpl implements VpsTemplateDAO {
	
	private JSONArray vpsTemplates = null;
	
	/**
	 * 根据模板id 查询模板详情
	 */
	@Override
	public String findOSNameByOid(String id) {
		if (vpsTemplates == null) {
			NetResult r = VpsService.getTemplates();
			vpsTemplates = JSON.parseArray(r.result.toString());
		}
		for (int i = 0; i < vpsTemplates.size(); i++) {
			JSONObject jo = (JSONObject) vpsTemplates.get(i);
			if (jo.get("oid").toString().equals(id)) {
				return jo.getString("oname");
			}
		}
		return "";
	}

}
