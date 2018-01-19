package com.txcourse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.txcourse.model.UserCourse;

/** 
* @author :liq 
* @version 创建时间：2017年12月4日 下午12:44:48 
* 类说明 
*/
@Service
public interface AdminCourseService {
	/**
	 * 根据id  渲染详情数据
	 * @param id 课程id
	 * @return
	 */
	JSONArray findCourseByid(String id);
	/**
	 * 管理员审核 开课
	 * @param id
	 * @return
	 */
	boolean openCourse(String id);
	/**
	 * 管理员审核  关课
	 * @param id
	 * @return
	 */
	boolean closeCourse(String id);
	/**
	 * 通过课程id 查询已经选课的学生
	 * @param id
	 * @return
	 */
	JSONArray findStuByCid(String id);
} 
