package com.txcourse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.NetResult;
import com.shu.model.User;
import com.txcourse.DAO.CourseDAO;
import com.txcourse.DAO.CourseVpsDAO;
import com.txcourse.DAO.TeacherCourseDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAOImpl.CourseDAOImpl;
import com.txcourse.DAOImpl.CourseVpsDAOImpl;
import com.txcourse.DAOImpl.TeacherCourseDAOImpl;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.model.Course;
import com.txcourse.model.CourseVps;
import com.txcourse.model.UserCourse;
import com.txcourse.service.AdminCourseService;
import com.txcourse.service.VpsService;
import com.txcourse.serviceImpl.AdminCourseServiceImpl;

/**
 * @author :liq
 * @version 创建时间：2017年12月4日 上午10:50:42 类说明 管理员操作
 */
@Controller
@RequestMapping("/admin")
public class AdminCourseController {
	private UserDAO userDao = new UserDAOImpl();
	private TeacherCourseDAO tdao = new TeacherCourseDAOImpl();
	private AdminCourseService aService = new AdminCourseServiceImpl();
	private NetResult re = new NetResult();
	private UserCourseDAO uCour = new UserCourseDAOImpl();
	private CourseVpsDAO uVpsDao = new CourseVpsDAOImpl();
	private CourseDAO couDao = new CourseDAOImpl();

	/**
	 * 渲染所有课程
	 * 
	 * @param model
	 *            数据返回
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
	public String getAllCourses(Model model, HttpSession session) {
		User use1r=(User)session.getAttribute(User.CUR_USER);
		System.err.println(use1r.getEmail());
		List<Course> list = tdao.findAllCourse();
		List<Map<String, String>> ml = new ArrayList<Map<String, String>>();
		if (list != null && list.size() > 0) {
			for (Course course : list) {
				Map<String, String> map = new HashMap<String, String>();
				User user = userDao.findUserById(course.getTeacherId());
				map.put("courseId", course.getId());
				map.put("courseName", course.getCourseName());
				map.put("courseSequence", course.getCourseSequence());
				if (course.getCourseStatus() == 3) {
					map.put("status", "课程进行中");
				} else if (course.getCourseStatus() == 2) {
					map.put("status", "课程关闭");
				} else if (course.getCourseStatus() == 1) {
					map.put("status", "审核通过");
				} else if (course.getCourseStatus() == 0) {
					map.put("status", "未审核");
				}
				map.put("name", user.getUserName());
				map.put("date", course.getCourseTime());
				ml.add(map);
			}
		}
		if (list == null) {
			model.addAttribute("AllCourse", new JSONArray());
			return "coursemanage";
		}
		model.addAttribute("AllCourse", JSONArray.toJSON(ml));
		return "coursemanage";
	}

	/**
	 * 审核按钮 渲染课程详情
	 * 
	 * @param model
	 * @param session
	 * @param courseId
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/authorizeCourse")
	public @ResponseBody Object getCourses(Model model, HttpSession session, @RequestParam String courseId) {
		JSONArray CourseByid = aService.findCourseByid(courseId);
		if (CourseByid != null) {
			re.status = 0;
			re.result = CourseByid;
		} else {
			re.status = -1;
			re.result = "数据为空";
		}
		return re;
	}

	/**
	 * 审核通过
	 * 
	 * @param model
	 * @param session
	 * @param courseId
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/passCourse")
	public @ResponseBody Object passCourses(@RequestParam String courseId) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		Course course = tdao.findCourseById(courseId);
		boolean con = tdao.authorizeCourse(course);
		if (con) {
			re.status = 0;
			re.result = "操作成功";
		} else {
			re.status = -1;
			re.result = "操作失败";
		}
		return re;
	}

	/**
	 * 审核不通过
	 * 
	 * @param courseId
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/vetoCourse")
	public @ResponseBody Object vetoCourses(@RequestParam String courseId) {
		return null;
	}

	/**
	 * 开课
	 * 
	 * @param courseId
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/openCourse")
	public @ResponseBody Object openCourse(@RequestParam String courseId) {
		boolean con = aService.openCourse(courseId);
		if (con == true) {
			re.status = 0;
			re.result = "开课成功";
		} else {
			re.status = -1;
			re.result = "开课失败";
		}
		return re;
	}

	/**
	 * 关课
	 * 
	 * @param courseId
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/closeCourse")
	public @ResponseBody Object closeCourse(@RequestParam String courseId) {
		boolean con = aService.closeCourse(courseId);
		if (con == true) {
			re.status = 0;
			re.result = "关课成功";
		} else {
			re.status = -1;
			re.result = "关课失败";
		}
		return re;
	}

	/**
	 * 通过课程号 查询学生
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getAllStudentVps")
	public @ResponseBody Object getAllStudentVps(@RequestParam String id) {
		if (id != null) {
			JSONArray CourseVps = uVpsDao.findByCid(id);
			if (CourseVps.size() > 0) {
				re.status = 0;
				re.result = CourseVps;
			} else {
				re.status = -1;
				re.result = "数据为空";
			}
		} else {
			re.status = -1;
			re.result = "数据异常，请联系管理员";
		}
		return re;
	}

	/**
	 * 开机
	 */
	@RequestMapping(value = "/bootExperimentVps")
	public @ResponseBody Object bootExperimentVps(@RequestParam String vpsid) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		Long vid = Long.valueOf(vpsid);
		if (vid != null && !vpsid.equals("0")) {
			NetResult result = VpsService.startVps(vid);
			// JSONArray status = (JSONArray) JSONArray.toJSON(result.status);
			// String con = status.toString();
			if (result.status == 0) {
				jo.put("result", "开机成功");
			} else {
				jo.put("result", "开机失败");
			}
		} else {
			jo.put("result", "主机不存在");
		}
		ja.add(jo);
		if (ja != null) {
			re.status = 0;
			re.result = ja;
		} else {
			re.status = -1;
			re.result = "数据异常，请联系管理员";
		}
		return re;
	}

	/**
	 * 关闭云机
	 * 
	 * @param vpsid
	 * @return
	 */
	@RequestMapping(value = "/shutdownVps", method = RequestMethod.POST)
	public @ResponseBody Object shutdownVps(@RequestParam String vpsid) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (vpsid != null && !vpsid.equals("0")) {
			Long vid = Long.valueOf(vpsid);
			NetResult result = VpsService.shutdownVps(vid);
			// JSONArray status = (JSONArray) JSONArray.toJSON(result.status);
			// String con = status.toString();
			if (result.status == 0) {
				jo.put("result", "关机成功");
			} else {
				jo.put("result", "关机失败");
			}
		} else {
			jo.put("result", "主机不存在");
		}
		ja.add(jo);
		if (ja != null) {
			re.status = 0;
			re.result = ja;
		} else {
			re.status = -1;
			re.result = "数据异常，请联系管理员";
		}
		return re;

	}

	/**
	 * 关闭云机
	 * 
	 * @param vpsid
	 * @return
	 */
	@RequestMapping(value = "/restartVps", method = RequestMethod.POST)
	public @ResponseBody Object restartVps(@RequestParam String vpsid) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (vpsid != null && !vpsid.equals("0")) {
			Long vid = Long.valueOf(vpsid);
			NetResult result = VpsService.restartVps(vid);
			// JSONArray status = (JSONArray) JSONArray.toJSON(result.status);
			// String con = status.toString();
			if (result.status == 0) {
				jo.put("result", "重启成功");
			} else {
				jo.put("result", "重启失败");
			}
		} else {
			jo.put("result", "主机不存在");
		}
		ja.add(jo);
		if (ja != null) {
			re.status = 0;
			re.result = ja;
		} else {
			re.status = -1;
			re.result = "数据异常，请联系管理员";
		}
		return re;

	}

	/**
	 * 刷新状态
	 */
	@RequestMapping(value = "/refreshExperimentStatus")
	public @ResponseBody Object getRefresh(@RequestParam String vpsid) {
		if (vpsid != null && !vpsid.equals("0")) {
			Long vid = Long.valueOf(vpsid);
			CourseVpsDAOImpl courseVpsDao = new CourseVpsDAOImpl();
			CourseVps vps = VpsService.getVps(vid);
			CourseVps newvps = courseVpsDao.findCourseVpsByVpsid(Long.valueOf(vpsid));
			if (true) {
				if (vps.getState().equals("running")) {
					newvps.setState("running");
					courseVpsDao.save(newvps);
					re.status = 0;
					re.result = "正在运行";
				}else if(vps.getState().equals("halted")){
					newvps.setState("halted");
					courseVpsDao.save(newvps);
					re.status = 0;
					re.result = "已关闭";
				}else if(vps.getState().equals("-1")){
					newvps.setState("-1");
					courseVpsDao.save(newvps);
					re.status = 0;
					re.result = "安装中";
				}
			} else {
				re.status = 3;
				re.result = "数据异常";
			}
		}else{
			re.status = 1;
			re.result = "不存在";
		}
		return re;
	}

	/**
	 * 批量关机
	 * 
	 * @param vpsid
	 *            string数组
	 * @return
	 */
	@RequestMapping(value = "/shutdownAllVps", method = RequestMethod.POST)
	public @ResponseBody Object shutdownAllVpsVps(@RequestParam String[] Vpsid) {
		if (Vpsid.length > 0) {
			for (int i = 0; i < Vpsid.length; i++) {
				Long id = Long.valueOf(Vpsid[i]);
				CourseVps courseVps = uVpsDao.findCourseVpsByVpsid(id);
				courseVps.setState("halted");
				uVpsDao.save(courseVps);
				NetResult shutdownVps = VpsService.shutdownVps(id);
			}
			re.status = 0;
			re.result = "操作成功";
		} else {
			re.status = -1;
			re.result = "操作失败";
		}

		return re;

	}

	/**
	 * 批量开机
	 * 
	 * @param vpsid
	 *            :string数组
	 * @return
	 */
	@RequestMapping(value = "/openAllVps", method = RequestMethod.POST)
	public @ResponseBody Object openAllVpsVps(@RequestParam String[] Vpsid) {
		if (Vpsid.length > 0) {
			for (int i = 0; i < Vpsid.length; i++) {
				Long id = Long.valueOf(Vpsid[i]);
				CourseVps courseVps = uVpsDao.findCourseVpsByVpsid(id);
				courseVps.setState("running");
				uVpsDao.save(courseVps);
				VpsService.startVps(id);
			}
			re.status = 0;
			re.result = "操作成功";
			return re;
		} else {
			re.status = 0;
			re.result = "操作失败，请联系刘鑫";
			return re;
		}
	}

	/**
	 * 批量重启
	 * 
	 * @param vpsid
	 *            :string数组
	 * @return
	 */
	@RequestMapping(value = "/restartAllVps", method = RequestMethod.POST)
	public @ResponseBody Object restartAllVps(@RequestParam String[] Vpsid) {
		if (Vpsid.length > 0) {
			for (int i = 0; i < Vpsid.length; i++) {
				Long id = Long.valueOf(Vpsid[i]);
				NetResult restartVps = VpsService.restartVps(id);

			}
			re.status = 0;
			re.result = "操作成功";
			return re;
		} else {
			re.status = 0;
			re.result = "操作失败，请联系刘鑫";
			return re;
		}

	}

	/**
	 * 批量回收
	 * 
	 * @param vpsid
	 *            :string数组
	 * @return
	 */
	@RequestMapping(value = "/destroyAllVps", method = RequestMethod.POST)
	public @ResponseBody Object destroyAllVps(@RequestParam String[] Vpsid) {
		if (Vpsid.length > 0) {
			for (int i = 0; i < Vpsid.length; i++) {
				Long id = Long.valueOf(Vpsid[i]);
				NetResult restartVps = VpsService.restartVps(id);
				//如果回收成功
				if (restartVps.status==0) {
					CourseVps courseVps = uVpsDao.findCourseVpsByVpsid(id);
					if (courseVps!=null) {
						//回收清空表数据
						boolean con = uVpsDao.delete(courseVps);
						if (con) {
							re.status = 0;
							re.result = "操作成功";
						}else{
							re.status = -1;
							re.result = "清出数据失败";
						}
					}else{
						re.status = -1;
						re.result = "courseVps为空，回收失败";
					}
				}else{
					re.status = -1;
					re.result = "回收失败";
				}
			}
			
			return re;
		} else {
			re.status = -1;
			re.result = "操作失败，请联系刘鑫";
			return re;
		}

	}
	/**
	 * 重置密码
	 * @param vpsid
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public @ResponseBody Object resetPwd(@RequestParam Long vpsid, String newPassword) {
//		NetResult r = new NetResult();
//		r.status = 1;
//		r.result = "password";
//		return r;
		NetResult r = VpsService.getVpsOsName(vpsid);
		if (r.status == 0) {
			String osname = (String) r.result;
			if (osname.contains("Windows")) {
				if (newPassword == null) {
					r.status = 1;
					r.result = "请输入密码";
					return r;
				} else if (newPassword.length() < 6) {
					r.status = 2;
					r.result = "密码长度不够，请重新输入";
					return r;
				} else {
//					r.status = 0;
//					r.result = "win success";
					UserCourse userCourse = uCour.findByVpsid(vpsid);
					userCourse.setPassword(newPassword);
					uCour.save(userCourse);
					CourseVpsDAO cDao = new CourseVpsDAOImpl();
					CourseVps vps = cDao.findCourseVpsByVpsid(vpsid);
					vps.setDpass(newPassword);
					cDao.save(vps);
//					return r;
					return VpsService.resetWindowsVpsPwd(vpsid, newPassword);
				}
			} else {
//				r.status = 0;
//				r.result = "linux success";
//				return r;
				return VpsService.resetLinuxVpsPwd(vpsid);
			}
		} else {
			return r;
		}
	}

}
