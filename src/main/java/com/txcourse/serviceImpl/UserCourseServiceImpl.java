package com.txcourse.serviceImpl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.model.User;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.model.UserCourse;
import com.txcourse.service.UserCourseService;

/**
 * @author :liq
 * @version 创建时间：2017年11月30日 下午2:24:41 类说明
 */
public class UserCourseServiceImpl implements UserCourseService {
	private UserCourseDAO userCourseDao = new UserCourseDAOImpl();
	private UserDAO uDao = new UserDAOImpl();

	/**
	 * 根据课程id查询学生详情  以选课
	 */
	public JSONArray findUserByCourserId(String id) {
		JSONArray ja = new JSONArray();
		
		List<UserCourse> userList = userCourseDao.findUserChooseByCourseId(id);
		if (userList != null && userList.size() > 0) {
			for (UserCourse userCourse : userList) {
				JSONObject jo = new JSONObject();
				// 根据id查询用户姓名
				User user = uDao.findUserById(userCourse.getStudentId());
				jo.put("id", user.getId());
				jo.put("uid", user.getUid());
				jo.put("username", user.getUserName());
				// 实验状态，是否上机，根据是否创云机来判断，0表示未上机，1表示已上机
				if (userCourse.getExperimentStatus() == 0) {
					jo.put("experimentStatus", "未上机");
				} else {
					jo.put("experimentStatus", "已上机");
				}
				if (userCourse.getScore()!=null) {
					jo.put("score", userCourse.getScore());
				}else {
					jo.put("score","--");
				}
				
				jo.put("worktime", userCourse.getWorkTime());
				ja.add(jo);
			}
			
		}else {
			ja=null;
		}
		return ja;
	}
	/**
	 * 根据课程id查询学生详情  未选课
	 */
	@Override
	public JSONArray findUserUnChooseByCourserId(String id) {
		JSONArray ja = new JSONArray();
		List<UserCourse> userList = userCourseDao.findUserUnChooseByCourseId(id);
		if (userList != null && userList.size() > 0) {
			for (UserCourse userCourse : userList) {
				JSONObject jo = new JSONObject();
				// 根据id查询用户姓名
				User user = uDao.findUserById(userCourse.getStudentId());
				jo.put("uid", user.getUid());
				jo.put("username", user.getUserName());
				// 实验状态，是否上机，根据是否创云机来判断，0表示未上机，1表示已上机
				if (userCourse.getExperimentStatus() == 0) {
					jo.put("experimentStatus", "--");
				} else {
					jo.put("experimentStatus", "--");
				}
				jo.put("score", "--");
				jo.put("worktime", userCourse.getWorkTime());
				ja.add(jo);
			}
			
		}else {
			ja=null;
		}
		return ja;	
	}
	/**
	 *  根据课程id   查询全部学生
	 */
	public JSONArray findAllUserByCourserId(String id) {
		JSONArray ja = new JSONArray();
		
		List<UserCourse> userList = userCourseDao.findUserUnChooseByCourseId(id);
		if (userList != null && userList.size() > 0) {
			for (UserCourse userCourse : userList) {
				JSONObject jo = new JSONObject();
				// 根据id查询用户姓名
				User user = uDao.findUserById(userCourse.getStudentId());
				jo.put("uid", user.getUid());
				jo.put("username", user.getUserName());
				// 实验状态，是否上机，根据是否创云机来判断，0表示未上机，1表示已上机
				if (userCourse.getExperimentStatus() == 0) {
					jo.put("experimentStatus", "以上机");
				} else {
					jo.put("experimentStatus", "未上机");
				}
				if (userCourse.getScore()!=null) {
					jo.put("score", userCourse.getScore());
				}else {
					jo.put("score","--");
				}
				jo.put("worktime", userCourse.getWorkTime());
				ja.add(jo);
			}
				
		}else {
			ja=null;
		}
		return ja;	
	}
	
}
