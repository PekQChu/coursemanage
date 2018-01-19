package com.txcourse.DAO;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.txcourse.model.CourseVps;

/** 
* @author :liq 
* @version 创建时间：2017年12月6日 上午10:56:22 
* 类说明 
*/
public interface CourseVpsDAO {
   //根据ip查询CourseVps
	CourseVps findCourseVpsByIp(String ip);
	boolean save(CourseVps cvps);
	boolean delete(CourseVps cvps);
	boolean deleteById(String id);
	List<CourseVps> findAll();
	CourseVps findCourseVpsByVpsid(Long vpsid);
	/*
	 * 通过课程id 查询所有的学生云机
	 */
	JSONArray findByCid(String cid);
}
