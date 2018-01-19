package com.txcourse.DAO;

import java.util.List;

import com.txcourse.model.Course;

/**
 * @author :liq
 * @version 创建时间：2017年11月29日 上午9:38:56 类说明 教师端的sql操作
 */

public interface TeacherCourseDAO {
	/**
	 * 新增课程
	 * 
	 * @param course 课程属性
	 * @return
	 */
	Boolean addCourser(Course course);
	

	/**
	 * 根据老师的id 查询课程
	 * 
	 * @param id教师id
	 * @return
	 */
	List<Course> findCourseByTeacherId(String id);

	/**
	 * 根据课程id查询课程详情
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	Course findCourseById(String id);
	
	/**
	 * 查询所有课程
	 * @return 
	 */
	List<Course> findAllCourse();
	
	/**
	 * 管理员开课
	 * @param Course
	 * @return
	 */
	boolean openCourse(Course course);
	
	/**
	 * 管理员关课
	 * @param Course
	 * @return
	 */
	boolean closeCourse(Course course);
	
	/**
	 * 管理员审核课程
	 * @param course：待审核的课程，id属性必须正确，将courseStatus字段置1
	 * @return true表示修改成功，false表示修改失败
	 */
	boolean authorizeCourse(Course course);

}
