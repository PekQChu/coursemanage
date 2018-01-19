package com.txcourse.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspApplicationContext;
import javax.sound.midi.Synthesizer;
import javax.xml.transform.sax.SAXTransformerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.NetResult;
import com.shu.model.User;
import com.txcourse.DAO.CourseDAO;
import com.txcourse.DAO.CourseVpsDAO;
import com.txcourse.DAO.MessageDAO;
import com.txcourse.DAO.TeacherCourseDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAOImpl.CourseDAOImpl;
import com.txcourse.DAOImpl.CourseVpsDAOImpl;
import com.txcourse.DAOImpl.MessageDAOImpl;
import com.txcourse.DAOImpl.TeacherCourseDAOImpl;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.model.Course;
import com.txcourse.model.CourseVps;
import com.txcourse.model.Message;
import com.txcourse.model.UserCourse;
import com.txcourse.service.TeacherCourseService;
import com.txcourse.service.UserCourseService;
import com.txcourse.service.VpsService;
import com.txcourse.serviceImpl.TeacherCourseServiceImpl;
import com.txcourse.serviceImpl.UserCourseServiceImpl;

/**
 * @author :liq
 * @version 创建时间：2017年11月29日 上午9:12:44 类说明 教师端的Controller
 */
@Controller
@RequestMapping("/teacherCourse")
public class TeacherCourseController {
	private UserDAO userDao = new UserDAOImpl();
	private CourseDAO courseDao = new CourseDAOImpl();
	private UserCourseDAO ucDao = new UserCourseDAOImpl();
	private TeacherCourseDAO tDao = new TeacherCourseDAOImpl();
	private TeacherCourseService tService = new TeacherCourseServiceImpl();
	private UserCourseService uService = new UserCourseServiceImpl();
	private CourseVpsDAO couVpsDao = new CourseVpsDAOImpl();
	private MessageDAO meDao = new MessageDAOImpl();
	private NetResult result = new NetResult();

	/**
	 * 跳转新建课程页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddCoursePage")
	public String toAddCoursePage(HttpSession session, Model model) {
		model.addAttribute("templates", VpsService.getTemplates().result);
		// 跳转页面
		return "teacher/addcoursepage";
	}

	/**
	 * 新建课程
	 * 
	 * @param course
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addCoursePage", method = RequestMethod.POST)
	public @ResponseBody Object addCourse(Course course, HttpSession session, Model model) {
		// 获取教师 工号
		User user = (User) session.getAttribute(User.CUR_USER);
		// 获取上传的文档路径
		course.setStudentListUrl((String) session.getAttribute("url"));
		System.out.println((String) session.getAttribute("url"));
		// 将老师工号存入课程中
		course.setTeacherId(user.getId());
		String status = tService.addCourse(course);
		if (status.equals("创建课程成功")) {
			result.status = 0;
			result.result = status;
		} else {
			result.status = -1;
			result.result = status;
		}
		return result;
	}

	/**
	 * 获取所有vps模板
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllTemplate", method = RequestMethod.POST)
	public void getAllTemplate(Model model) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		NetResult templates = VpsService.getTemplates();
		CourseVps courVps = new CourseVps();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (templates != null) {
			JSONArray jaTemp = JSON.parseArray(templates.result.toString());
			for (int i = 0; i < jaTemp.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject joTemp = (JSONObject) jaTemp.get(i);
				map.put("oid", (String) joTemp.get("oid"));
				map.put("osname", (String) joTemp.get("oid"));
				list.add(map);
			}
		}
		if (list != null && list.size() > 0) {
			model.addAttribute("os", list);
		}

	}

	/**
	 * 跳转我的课程页面
	 * 
	 * @return
	 */
	@RequestMapping("/toteacherCoursesPage")
	public String toteacherCoursesPage(HttpSession session) {
		// 跳转页面
		return "teacher/teacherCoursesPage";
	}

	/**
	 * 展示 该老师 所有课程
	 * 
	 * @param session
	 *            根据session获取教师id
	 * @return json
	 */
	@RequestMapping(value = "/getTeacherCourses", method = RequestMethod.POST)
	public @ResponseBody Object getTeacherCourses(HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		JSONArray ja = tService.findCourseDaoByTheacherId(user.getId());
		if (ja != null && ja.size() > 0) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "课程为空";
		}
		return result;

	}

	/**
	 * 课程详情
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/toTCourseDetailPage", method = RequestMethod.POST)
	public @ResponseBody Object toTCourseDetailPage(@RequestParam("id") String id) {
		JSONArray ja = tService.findCourseByid(id);
		if (ja != null && ja.size() > 0) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "课程不存在";
		}
		return result;
	}

	/**
	 * 修改详情
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/UpdateCourseDetailPage", method = RequestMethod.POST)
	public @ResponseBody Object toTCourseDetailPage(@RequestParam String cid, String content) {
		if (content != null && !content.trim().equals("")) {
			Course course = courseDao.findCourseById(cid);
			course.setCourseContent(content);
			boolean con = courseDao.save(course);
			if (con) {
				result.status = 0;
				result.result = "修改成功";
			} else {
				result.status = -1;
				result.result = "修改失败";
			}
		} else {
			result.status = 1;
			result.result = "课程要求为空";
		}

		return result;
	}

	/**
	 * 跳转成员管理页面
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping("/tomemberManagePage")
	public String tomemberManage(@RequestParam("id") String id, HttpSession session) {
		session.setAttribute("cID", id);
		return "teacher/membermanage";
	}

	/**
	 * 渲染成员管理页面数据 默认 显示已选
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/getChoosenMember", method = RequestMethod.POST)
	public @ResponseBody Object getChooseMember(@RequestParam("id") String id) {
		JSONArray ja = uService.findUserByCourserId(id);
		System.out.println(ja.size());
		if (ja != null) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "无记录";
		}
		return result;
	}

	/**
	 * 渲染成员管理页面数据 未选课
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/getUnchoosenMember", method = RequestMethod.POST)
	public @ResponseBody Object getUnChooseMember(@RequestParam("id") String id) {
		JSONArray ja = uService.findUserUnChooseByCourserId(id);
		if (ja != null) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "无记录";
		}
		return result;
	}

	/**
	 * 成员管理页面 未选课批量通过
	 * 
	 * @param cid
	 *            课程id
	 * @param number
	 *            学生id
	 * @return
	 */
	@RequestMapping(value = "/addMembersToCourse", method = RequestMethod.POST)
	public @ResponseBody Object addMembers(String cid, String[] number) {
		int sucNum = 0;
		for (int i = 0; i < number.length; i++) {
			UserCourse course = ucDao.findBySidAndCid(cid, number[i]);
			if (course.getId() != null) {
				course.setChooseStatus(1);
				ucDao.save(course);
				sucNum++;
			}
		}
		if (sucNum++ > 0) {
			result.status = 0;
			result.result = "操作成功";
		} else {
			result.status = 0;
			result.result = "操作失败";
		}

		return result;
	}

	/**
	 * 根据课程id 查询该课程下所有学生
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/getAllStu", method = RequestMethod.POST)
	public @ResponseBody Object getAllStu(@RequestParam("id") String id) {
		JSONArray ja = uService.findAllUserByCourserId(id);
		if (ja != null) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "数据为空";
		}
		return result;
	}

	/**
	 * 下载模板
	 * 
	 * @return 地址
	 */
	@RequestMapping("/downloadTemplate")
	public String downloadTemplate() {
		return "";
	}

	/**
	 * 上传学生excel
	 * 
	 * @param multipartFile
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "uploadStuExcel")
	public @ResponseBody Object uploadStuExcel(MultipartFile multipartFile, HttpSession session,
			HttpServletRequest request) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			System.out.println(multipartFile.getOriginalFilename());
			String str = multipartFile.getOriginalFilename();
			String kuozhanname = str.substring(str.lastIndexOf(".") + 1);// 扩展名
			if (!kuozhanname.endsWith("xls")) {
				result.status = -1;
				result.result = "只支持.xls后缀名格式";
				return result;
			}
			// 文件名字
			String name = new Date().getTime() + str;
			try {
				// 保存文件到临时目录
				String savePath = request.getSession().getServletContext().getRealPath("/") + "upload/" + name;
				File saveFile = new File(savePath);
				// 如果路径不存在创建文件
				if (!saveFile.exists()) {
					saveFile.mkdirs();
				}
				// 永久保存
				multipartFile.transferTo(saveFile);
				session.setAttribute("url", savePath);
				result.status = 0;
				result.result = "上传成功";

			} catch (Exception e) {
				session.setAttribute("url", "");
				result.status = 1;
				result.result = "上传失败，联系管理员";
			}
		} else {
			session.setAttribute("url", "");
			result.status = -1;
			result.result = "(⊙o⊙)上传失败！";
		}
		return result;
	}

	/**
	 * 跳转实验管理页面
	 * 
	 * @param session
	 *            老师id
	 * @param cid
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "topracticeGovern")
	public String topracticeGovern(HttpSession session, @RequestParam String cid, Model model) {
		model.addAttribute("cid", cid);
		return "teacher/practicegovern";
	}

	/**
	 * 渲染实验管理页面 表格数据
	 * 
	 * @param session
	 *            老师id
	 * @param cid
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "getpracticeGovern", method = RequestMethod.POST)
	public @ResponseBody Object getpracticeGovern(HttpSession session, @RequestParam String cid) {
		User user = (User) session.getAttribute(User.CUR_USER);
		JSONArray ja = tService.getpracticeGovern(user.getId(), cid);
		if (ja == null) {
			result.status = -2;
			result.result = "数据为空";
		} else if (ja != null) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "数据异常，请联系管理员";

		}
		return result;
	}

	/**
	 * 重启云机
	 * 
	 * @param vpsid
	 * @return
	 */
	@RequestMapping(value = "/restartVps", method = RequestMethod.POST)
	public @ResponseBody Object restartVps(@RequestParam Long vpsid) {
		NetResult r = new NetResult();
		if (vpsid != null) {
			NetResult result = VpsService.restartVps(vpsid);
			System.out.println(result.status);
			if (result.status == 0) {
				CourseVps userVps = couVpsDao.findCourseVpsByVpsid(vpsid);
				userVps.setState("running");
				// 更改数据库中的状态
				boolean update = couVpsDao.save(userVps);
				if (update) {
					r.status = 0;
					r.result = "重启成功";
					return r;
				} else {
					r.status = -1;
					r.result = "重启失败";
					return r;
				}
			} else {
				r.status = -1;
				r.result = "重启失败";
				return r;
			}
		} else {
			r.status = -1;
			r.result = "主机不存在";
			return r;
		}
	}

	/**
	 * 刷新状态
	 */
	@RequestMapping(value = "/refreshExperimentStatus")
	public @ResponseBody Object getRefresh(@RequestParam Long vpsid) {
		NetResult re = new NetResult();
		if (vpsid != null) {
			CourseVpsDAOImpl courseVpsDao = new CourseVpsDAOImpl();
			CourseVps vps = VpsService.getVps(vpsid);
			CourseVps newvps = courseVpsDao.findCourseVpsByVpsid(Long.valueOf(vpsid));
			if (true) {
				if (vps.getState().equals("running")) {
					newvps.setState("running");
					courseVpsDao.save(newvps);
					re.status = 0;
					re.result = "正在运行";
				} else if (vps.getState().equals("halted")) {
					newvps.setState("halted");
					courseVpsDao.save(newvps);
					re.status = -1;
					re.result = "已关闭";
				} else if (vps.getState().equals("-1")) {
					newvps.setState("-1");
					courseVpsDao.save(newvps);
					re.status = -1;
					re.result = "安装中";
				}
			} else {
				re.status = 3;
				re.result = "数据异常";
			}
		} else {
			re.status = 1;
			re.result = "不存在";
		}
		return re;
	}

	/**
	 * 开机
	 * 
	 * @param vpsid
	 * @return
	 */
	@RequestMapping(value = "/openVps", method = RequestMethod.POST)
	public @ResponseBody Object openVps(@RequestParam Long vpsid) {
		NetResult r = new NetResult();
		if (vpsid != null) {
			NetResult result = VpsService.startVps(vpsid);
			if (result.status == 0) {
				CourseVps userVps = couVpsDao.findCourseVpsByVpsid(vpsid);
				userVps.setState("running");
				// 更改数据库中的状态
				boolean update = couVpsDao.save(userVps);
				if (update) {
					r.status = 0;
					r.result = "开机成功";
				} else {
					r.status = -1;
					r.result = "开机失败";
				}
			} else {
				r.status = -1;
				r.result = "开机失败";
			}
		} else {
			r.status = -1;
			r.result = "参数为空";
		}
		return r;

	}

	/**
	 * 关闭云机
	 * 
	 * @param vpsid
	 * @return
	 */
	@RequestMapping(value = "/shutdownVps", method = RequestMethod.POST)
	public @ResponseBody Object shutdownVps(@RequestParam Long vpsid) {
		NetResult r = new NetResult();
		if (vpsid != null) {
			NetResult result = VpsService.shutdownVps(vpsid);
			if (result.status == 0) {
				CourseVps userVps = couVpsDao.findCourseVpsByVpsid(vpsid);
				userVps.setState("halted");
				// 更改数据库中的状态
				boolean update = couVpsDao.save(userVps);
				if (update) {
					r.status = 0;
					r.result = "关机成功";
					return r;
				} else {
					r.status = -1;
					r.result = "关机失败";
					return r;
				}
			} else {
				r.status = -1;
				r.result = "关机失败";
				return r;
			}
		} else {
			r.status = -1;
			r.result = "主机不存在";
			return r;
		}

	}

	/**
	 * 批量关机
	 * 
	 * @param vpsid
	 *            string数组
	 * @return
	 */
	@RequestMapping(value = "/shutdownAllVps", method = RequestMethod.POST)
	public @ResponseBody Object shutdownAllVpsVps(@RequestParam("vpsids[]") Long[] vpsids) {
		// for (int i = 0; i < vpsids.length; i++) {
		// System.out.println("shutdown = " + vpsids[i]);
		// }
		if (vpsids.length > 0) {
			for (int i = 0; i < vpsids.length; i++) {
				NetResult shutdownVps = VpsService.shutdownVps(vpsids[i]);
				CourseVps courseVps = couVpsDao.findCourseVpsByVpsid(vpsids[i]);
				courseVps.setState("halted");
				couVpsDao.save(courseVps);
			}
			result.status = 0;
			result.result = "操作成功";
		} else {
			result.status = -1;
			result.result = "未选择";
		}
		return result;

	}

	/**
	 * 批量开机
	 * 
	 * @param vpsid
	 *            :string数组
	 * @return
	 */
	@RequestMapping(value = "/openAllVps", method = RequestMethod.POST)
	public @ResponseBody Object openAllVpsVps(@RequestParam("vpsids[]") Long[] vpsids) {
		// for (int i = 0; i < vpsids.length; i++) {
		// System.out.println("data = " + vpsids[i]);
		// }

		if (vpsids.length > 0) {
			for (int i = 0; i < vpsids.length; i++) {
				VpsService.startVps(vpsids[i]);
				CourseVps courseVps = couVpsDao.findCourseVpsByVpsid(vpsids[i]);
				courseVps.setState("running");
				couVpsDao.save(courseVps);
			}
			result.status = 0;
			result.result = "操作成功";
		} else {
			result.status = -1;
			result.result = "未选择";
		}
		return result;

	}

	/**
	 * 批量重启
	 * 
	 * @param vpsid
	 *            :string数组
	 * @return
	 */
	@RequestMapping(value = "/restartAllVps", method = RequestMethod.POST)
	public @ResponseBody Object restartAllVps(@RequestParam("vpsids[]") Long[] vpsids) {
		// for (int i = 0; i < vpsids.length; i++) {
		// System.out.println("restart = " + vpsids[i]);
		// }
		if (vpsids.length > 0) {
			for (int i = 0; i < vpsids.length; i++) {
				NetResult restartVps = VpsService.restartVps(vpsids[i]);
				CourseVps courseVps = couVpsDao.findCourseVpsByVpsid(vpsids[i]);
				courseVps.setState("running");
				couVpsDao.save(courseVps);
			}
			result.status = 0;
			result.result = "操作成功";
		} else {
			result.status = -1;
			result.result = "未选择";
		}
		return result;

	}

	/**
	 * 跳转课程详情页面
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping("/toCourseDetail")
	public String toCourseDetail(HttpSession session, @RequestParam("id") String id, Model model) {
		model.addAttribute("cid", id);
		return "teacher/coursedetail";
	}

	/**
	 * 跳转我的信息页面
	 * 
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping("/toMyMessage")
	public String toMyMessage(HttpSession session, Model model) {
		User user = (User) session.getAttribute(User.CUR_USER);
		model.addAttribute("teacherid", user.getId());
		return "teacher/mysessage";
	}

	/**
	 * 渲染我的信息页面 所有信息
	 * 
	 * @param
	 * 
	 * @return
	 */
	@RequestMapping("/getMyMessage")
	public @ResponseBody Object getMyMessage(HttpSession session) {
		JSONArray ja = new JSONArray();
		User user = (User) session.getAttribute(User.CUR_USER);
		List<Message> mes = meDao.findAllMessageByUid(user.getId());
		if (mes == null) {
			result.status = 0;
			result.result = ja;
			return result;
		}
		for (Message message : mes) {
			JSONObject jo = new JSONObject();
			User users = userDao.findUserById(message.getSendUserId());
			jo.put("id", message.getId());
			jo.put("name", users.getUserName());
			jo.put("uNum", users.getUid());
			jo.put("date", message.getMsgTime());
			jo.put("mes", message.getContent());
			if (message.getMsgStatus() == 0) {
				jo.put("state", "未读");
			} else {
				jo.put("state", "已读");
			}
			ja.add(jo);
		}
		if (ja != null) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "数据异常";
		}
		return result;
	}

	/**
	 * 信息详情内容
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/getMessage")
	public @ResponseBody Object getMessage(String id) {
		Message message = meDao.getMessageByid(id);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		User user = userDao.findUserById(message.getSendUserId());
		jo.put("message", message.getContent());
		jo.put("date", message.getMsgTime());
		jo.put("sendName", user.getUserName());
		ja.add(jo);
		result.status = 0;
		result.result = ja;
		return result;
	}

	/**
	 * 发生留言到生
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/sendMessage")
	public @ResponseBody Object getMessage(Message message, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		message.setMsgStatus(0);
		message.setSendUserId(user.getId());
		meDao.save(message);
		result.status = 0;
		result.result = "留言成功";
		return result;
	}

	/**
	 * 发生留言到生
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/delMessage")
	public @ResponseBody Object delMessage(Message message, HttpSession session) {
		meDao.delete(message.getId());
		result.status = 0;
		result.result = "删除留言成功";
		return result;
	}

	/**
	 * 修改状态
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/messageReaded")
	public @ResponseBody Object messageReaded(Message message, HttpSession session) {
		Message targetMsg = meDao.getMessageByid(message.getId());
		targetMsg.setMsgStatus(1);
		meDao.save(targetMsg);
		result.status = 0;
		result.result = "保存成功";
		return result;
	}
	/*
	 * @RequestMapping("/getMessage") public @ResponseBody Object
	 * getMessage(String id,HttpSession session) { Message message =
	 * meDao.getMessageByid(id); if(message == null){ result.status = 0;
	 * result.result = "保存成功"; } result.status = 0; result.result = message;
	 * return result; }
	 */

	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public @ResponseBody Object resetPwd(@RequestParam Long vpsid, String newPassword) {
		// NetResult r = new NetResult();
		// r.status = 1;
		// r.result = "password";
		// return r;
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
					// r.status = 0;
					// r.result = "win success";
					CourseVpsDAO cDao = new CourseVpsDAOImpl();
					CourseVps vps = cDao.findCourseVpsByVpsid(vpsid);
					vps.setDpass(newPassword);
					cDao.save(vps);
					// return r;
					return VpsService.resetWindowsVpsPwd(vpsid, newPassword);
				}
			} else {
				// r.status = 0;
				// r.result = "linux success";
				// return r;
				return VpsService.resetLinuxVpsPwd(vpsid);
			}
		} else {
			return r;
		}
	}
	
	@RequestMapping(value = "/checkscore", method = RequestMethod.POST)
	public  @ResponseBody Object checkscore(@RequestParam String id,@RequestParam String score){
		UserCourse userCourse = ucDao.findByid(id);
		if (userCourse!=null) {
			Double sc=Double.valueOf(score);
			userCourse.setScore(sc);
			ucDao.save(userCourse);
			result.status=0;
			result.result="打分成功";
		}else {
			result.status=-1;
			result.result="数据异常";
		}
		return null;
	}
}
