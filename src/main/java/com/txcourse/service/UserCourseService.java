package com.txcourse.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

/** 
* @author :liq 
* @version 创建时间：2017年11月30日 下午2:23:49 
* 类说明  UserCourseService
*/
@Service
public interface UserCourseService {
	/**
	 * 根据课程id查询学生详情  已选课
	 * @param id 课程id
	 * @return
	 */
     JSONArray findUserByCourserId(String id);
     /**
 	 * 根据课程id查询学生详情  未选课
 	 * @param id 课程id
 	 * @return
 	 */
     JSONArray findUserUnChooseByCourserId(String id);
     /**
      *  查询 该课程下全部学生
      * @param  课程id
      * @return
      */
     JSONArray findAllUserByCourserId(String id);
     
}
