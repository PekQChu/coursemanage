package com.txcourse.DAO;

import java.util.List;

import com.txcourse.model.Message;

/** 
* @author :liq 
* @version 创建时间：2017年12月9日 下午5:27:40 
* 类说明 
*/

public interface MessageDAO {
	/**
	 * 根据接收者id 查询所有信息
	 * @param id 接收者id
	 * @return
	 */
     List<Message> findAllMessageByUid(String id);
     /**
      * 根据信息id  查询信息详情
      * @param id
      * @return
      */
     Message  getMessageByid(String id);
     
     void save(Message m);
     
     void delete(String id);
     
     int getNewMsgNum(String id);
     
}
