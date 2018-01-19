package com.txcourse.DAO;

import com.shu.model.VpsTemplate;

/** 
* @author :liq 
* @version 创建时间：2017年11月29日 下午2:06:06 
* 类说明  vps模板接口
*/
public interface VpsTemplateDAO {
  //根据模板id查询模板名称
	String findOSNameByOid(String id);
}
