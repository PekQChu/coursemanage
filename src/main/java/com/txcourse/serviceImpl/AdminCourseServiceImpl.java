package com.txcourse.serviceImpl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.model.User;
import com.txcourse.DAO.StudentCourseDAO;
import com.txcourse.DAO.TeacherCourseDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAOImpl.StudentCourseDAOImpl;
import com.txcourse.DAOImpl.TeacherCourseDAOImpl;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.model.Course;
import com.txcourse.model.UserCourse;
import com.txcourse.service.AdminCourseService;
import com.txcourse.service.UserCourseService;

/**
 * @author :liq
 * @version 创建时间：2017年12月4日 下午12:45:13 类说明
 */
public class AdminCourseServiceImpl implements AdminCourseService {
	private TeacherCourseDAO tDao = new TeacherCourseDAOImpl();
	private UserCourseDAO uCouDao = new UserCourseDAOImpl();
	private UserDAO udao = new UserDAOImpl();
	private StudentCourseDAO stdo = new StudentCourseDAOImpl();

	/**
	 * 根据课程id 查询课程详情
	 * 
	 * @param id
	 * @return
	 */
	public JSONArray findCourseByid(String id) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (id == null && id.equals("")) {
			return ja = null;
		} else {
			Course course = tDao.findCourseById(id);
			jo.put("id", course.getId());
			jo.put("courseSequence", course.getCourseSequence());
			jo.put("courseName", course.getCourseName());
			jo.put("courseTime", course.getCourseTime());
			jo.put("courseContent", course.getCourseContent());
			ja.add(jo);
			return ja;
		}
	}
	
	

	/**
	 * 开课
	 */
	public boolean openCourse(String id) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		Course course = tDao.findCourseById(id);
		// 开课
		
		boolean con = tDao.openCourse(course);

		return con;
	}

	/**
	 * 关课
	 */
	public boolean closeCourse(String id) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		Course course = tDao.findCourseById(id);
		// 关课
		
		boolean con = tDao.closeCourse(course);
		return con;
	}

	/**
	 * 通过课程id 查询已经选课的学生详情
	 */
	public JSONArray findStuByCid(String id) {
		JSONArray ja = new JSONArray();
		List<UserCourse> StuList = uCouDao.findUserChooseByCourseId(id);
		if (StuList != null && StuList.size() > 0) {
			for (UserCourse userCourse : StuList) {
				JSONObject jo = new JSONObject();
				// 根据学生id查询学生姓名
				User user = udao.findUserById(userCourse.getStudentId());
				jo.put("id", userCourse.getId());
				jo.put("uid", userCourse.getStudentId());
				jo.put("studentName", user.getUserName());
				// 实验状态，是否上机，根据是否创云机来判断，0表示未上机，1表示已上机
				if (userCourse.getVpsid() == 0) {
					jo.put("experimentStatus", "未上机");
				} else {
					jo.put("experimentStatus", "已上机");
				}
				Course cou = tDao.findCourseById(userCourse.getCourseId());
				jo.put("courseName", cou.getCourseName());
				User userT = udao.findUserById(cou.getTeacherId());
				jo.put("teacherName", userT.getUserName());
				if (userCourse.getIp()!=null) {
					jo.put("ip", userCourse.getIp());
				}else {
					jo.put("ip", "");
				}
				if (userCourse.getPassword()!=null) {
					jo.put("password", userCourse.getPassword());
				}else {
					jo.put("password","");
				}
				
				ja.add(jo);
			}
			
		} else {
			ja = null;
		}
		return ja;

	}

}
