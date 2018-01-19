package com.txcourse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.txcourse.model.Course;

/** 
* @author :liq 
* @version 创建时间：2017年11月29日 下午1:45:07 
* 类说明  教师端service
*/
@Service
public interface TeacherCourseService {
    /**
     * 根据教师id查询课程
     * @param id 教师id
     * @return json数组
     */
	JSONArray findCourseDaoByTheacherId(String id);
	/**
	 * 新建课程
	 * @param course 课程属性
	 * @return int类型 
	 */
	String  addCourse(Course course);
	/**
	 * 根据课程id查询课程详情
	 * @param id 课程id
	 * @return
	 */
	JSONArray findCourseByid(String id);
	/**
	 * 渲染实验管理 表格数据
	 * @param tid 教师id
	 * @param cid 课程id
	 * @return
	 */
	JSONArray getpracticeGovern(String tid,String cid);
	/**
	 * 老师批量通过选课
	 * @param num 学生id
	 * @return
	 */
	JSONArray teacherPass(Integer[]num);
	/**
	 * 老师获取全部留言
	 * @param receiveUserId
	 * @return
	 */
	JSONArray getMessage(String receiveUserId);
}
