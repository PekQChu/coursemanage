package com.txcourse.controller;

import java.io.File;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.annotations.Param;
import org.json.HTTP;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.NetResult;
import com.shu.model.User;
import com.txcourse.DAO.CourseDAO;
import com.txcourse.DAO.CourseVpsDAO;
import com.txcourse.DAO.MessageDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAOImpl.CourseDAOImpl;
import com.txcourse.DAOImpl.CourseVpsDAOImpl;
import com.txcourse.DAOImpl.MessageDAOImpl;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.model.Course;
import com.txcourse.model.CourseVps;
import com.txcourse.model.Message;
import com.txcourse.model.UserCourse;
import com.txcourse.service.StudentCourseService;
import com.txcourse.service.UserCourseService;
import com.txcourse.service.VpsService;
import com.txcourse.serviceImpl.StudentCourseServiceImpl;
import com.txcourse.serviceImpl.UserCourseServiceImpl;

/**
 * @author :liq
 * @version- 创建时间：2017年11月30日 下午4:23:17 类说明 学生端 控制
 */
@Controller
@RequestMapping("/studentCourse")
public class StudentCourseController {
	private NetResult result = new NetResult();
	private StudentCourseService stuService = new StudentCourseServiceImpl();
	private MessageDAO medao = new MessageDAOImpl();
	private CourseDAO courseDao = new CourseDAOImpl();
	private UserCourseDAO usercourseDao=new UserCourseDAOImpl();
	private UserDAO udao = new UserDAOImpl();
	private MessageDAO meDao = new MessageDAOImpl();
	private CourseVpsDAO courseVpsDao = new CourseVpsDAOImpl();

	/**
	 * 跳转学生端 我的课程页面
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/toStudentCourse")
	public String toStudentCoursePage(HttpSession session) {
		return "student/studentcourses";
	}

	/**
	 * 学生端 我的所有课程
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getStudentCourses")
	public @ResponseBody Object getMyCourses(HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		JSONArray ja = stuService.findCourseByStuId(user.getId());
		if (ja != null && ja.size() > 0) {
			result.status = 0;
			result.result = ja;
		} else {
			result.status = -1;
			result.result = "无数据";
		}
		return result;

	}

	/**
	 * 学生端 展示所有课程
	 * 
	 * @param type
	 *            搜索条件类型 0空 1 课程名 2 任课老师
	 * @param sql
	 *            参数
	 * @return
	 */
	@RequestMapping(value = "/getAllCourses")
	public @ResponseBody Object getAllCourses(@RequestParam Integer type, String sql) {
		JSONArray ja = stuService.findAllCourse(type, sql);
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
	 * 跳转到 课程详情 页面
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	@RequestMapping(value = "/toCourseDetial")
	public String toCourseDetial(String id, HttpSession session) {
		session.setAttribute("cid", id);
		return "student/mycourse";
	}

	/**
	 * 渲染课程详情数据
	 * 
	 * @param id
	 *            课程id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getCourseDetial")
	public @ResponseBody Object getCourseDetial(HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		String id = (String) session.getAttribute("cid");
		JSONArray ja = stuService.findCourseByID(id, user.getId());
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
	 * 加入课程
	 * 
	 * @param id
	 *            课程id
	 * @param session
	 *            学生session
	 * @return
	 */
	@RequestMapping(value = "/joinCourse")
	public @ResponseBody Object joinCourse(@RequestParam String id, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Course course = courseDao.findCourseById(id);
		if (course.getCourseStatus()==3) {
			Integer num = course.getStudentNum() + 1;
			Integer ja = stuService.joinCourse(id, user.getId());
			if (ja == 0) {
				course.setStudentNum(num + 1);
				courseDao.save(course);
				result.status = 0;
				result.result = "加入成功";
			} else if (ja == 1) {
				result.status = 1;
				result.result = "加入失败";
			} else if (ja == -1) {
				result.status = -1;
				result.result = "课程已加入过";
			}
		}else {
			result.status = 1;
			result.result = "课程尚未开启";
		}
		return result;
	}

	/**
	 * 刷新状态
	 */
	@RequestMapping(value = "/refreshExperimentStatus")
	public @ResponseBody Object getRefresh(@RequestParam String vpsid) {
		if (vpsid != null && !vpsid.equals("0")) {
			Long vid = Long.valueOf(vpsid);
			CourseVps vps = VpsService.getVps(vid);
			CourseVps newvps = courseVpsDao.findCourseVpsByVpsid(Long.valueOf(vpsid));
			if (true) {
				System.out.println("state = " + vps.getState());
				if (vps.getState().equals("running")) {
					newvps.setState("running");
					courseVpsDao.save(newvps);
					result.status = 0;
					result.result = "正在运行";
				} else if (vps.getState().equals("-1")) {
					newvps.setState("-1");
					courseVpsDao.save(newvps);
					result.status = 1;
					result.result = "安装中";
				} else if (vps.getState().equals("halted")) {
					newvps.setState("halted");
					courseVpsDao.save(newvps);
					result.status = -1;
					result.result = "已关闭";
				} else {
					System.out.println(vps.getState());
					result.status = 3;
					result.result = "数据异常";
				}
			} else {
				result.status = 2;
				result.result = "主机不存在";
			}
		} else {
			result.status = 2;
			result.result = "主机不存在";
		}
		return result;
	}

	/**
	 * 学生上传实验报告
	 * 
	 * @param session
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadReport")
	public @ResponseBody Object uploadReport(@RequestParam String id, HttpSession session,
			@RequestParam MultipartFile file, HttpServletRequest request) {
		System.out.println(id);
		User user = (User) session.getAttribute(User.CUR_USER);
		id=(String) session.getAttribute("cid");
		// User user2=new User();
		// user2.setId("5b016930-7cc6-420b-a26b-7c2059a0faa9");
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 保存文件到临时目录
				System.out.println("id = " + id);
				System.out.println("fileName = " + file.getOriginalFilename());
				String path = request.getSession().getServletContext().getRealPath("/") + "upload/" + id + "/"
						+ user.getUid();
				System.out.println(path);
				File targetFile = new File(path, file.getOriginalFilename());
				String realpath = "/TxCourse/upload/" + id + "/" + user.getId() + "/" + file.getOriginalFilename();
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 永久保存
				file.transferTo(targetFile);
				// System.out.println(request.getSession().getServletContext().getRealPath("/upload"));
				// String savePath =
				// request.getSession().getServletContext().getRealPath("/") +
				// "/upload/"+user2.getId();
				// File saveFile = new
				// File(savePath,file.getOriginalFilename());
				// String path="/upload/"+user2.getId()+"/"+
				// file.getOriginalFilename();
				// file.transferTo(saveFile);
				// System.out.println(path);
				JSONArray ja = stuService.updateUrl(user.getId(), id, realpath);
				if (ja != null) {
					result.status = 0;
					result.result = "success";
				} else {
					result.status = -1;
					result.result = "上传为空";
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.status = -1;
				result.result = "上传出错";
			}
		} else {
			System.out.println("文件为空");
		}
		return result;
	}

	/**
	 * 创建资源
	 * 
	 * @param cid
	 *            课程id
	 * @param session
	 *            学生id
	 * @return
	 */
	@RequestMapping(value = "/createResources")
	public @ResponseBody Object createResources(String cid, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Integer con = stuService.createResources(cid, user.getId(),user.getUid());
		if (con == 3) {
			result.status = 3;
			result.result = "已经存在主机";
		} else if (con == 1) {
			result.result = "创建失败，请联系管理员";
			result.status = 1;
		} else if (con == 0) {
			result.result = "创建成功";
			result.status = 0;
		} else {
			result.result = "创建失败，请联系管理员";
			result.status = -1;
		}
		return result;

	}

	/**
	 * 重启云机
	 * 
	 * @param cid
	 *            课程id
	 * @param session
	 *            学生id
	 * @return
	 */
	@RequestMapping(value = "/restartVps")
	public @ResponseBody Object restartVps(@RequestParam Long vpsid, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Integer con = stuService.restartVps(vpsid);
		if (con == 0) {
			result.result = "操作成功";
			result.status = 0;
		} else if (con == -1) {
			result.result = "重启失败";
			result.status = -1;
		} else if (con == 1) {
			result.result = "主机不存在";
			result.status = 1;
		}
		return result;

	}

	/**
	 * 开机
	 * 
	 * @param cid
	 *            课程id
	 * @param session
	 *            学生id
	 * @return
	 */
	@RequestMapping(value = "/openVps")
	public @ResponseBody Object openVps(@RequestParam Long vpsid, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Integer con = stuService.openVps(vpsid);
		if (con == 0) {
			result.result = "开机成功";
			result.status = 0;
		} else if (con == -1) {
			result.result = "现在操作不允许（主机以经开启)";
			result.status = -1;
		} else if (con == -1) {
			result.result = "开机失败";
			result.status = 1;
		} else if (con == 3) {
			result.result = "主机不存在";
			result.status = 3;
		}

		return result;

	}

	/**
	 * 关闭云机
	 * 
	 * @param cid
	 *            课程id
	 * @param session
	 *            学生id
	 * @return
	 */
	@RequestMapping(value = "/shutdownVps")
	public @ResponseBody Object shutdownVps(@RequestParam Long vpsid, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Integer con = stuService.shutdownVps(vpsid);
		if (con == 0) {
			result.result = "关机成功";
			result.status = 0;
		} else if (con == -1) {
			result.result = "现在操作不允许（主机以关闭过）";
			result.status = -1;
		} else if (con == 1) {
			result.result = "操作异常，请联系管理员";
			result.status = 1;
		} else {
			result.result = "主机不存在";
			result.status = 2;
		}
		return result;

	}

	/**
	 * 重置密码 判断vps的操作系统 win系统要弹窗用户输入密码（不小于6位） liunx 系统自己重置默认密码
	 * 
	 * @param cid
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getresetVpsPwd")
	public @ResponseBody Object getresetVpsPwd(String cid, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Integer con = stuService.getVpsSystems(cid, user.getId());
		if (con == 1) {
			result.status = 0;
			result.result = "win";
		} else if (con == 2) {
			result.status = 1;
			result.result = "liunx";
		} else {
			result.status = -1;
			result.result = "数据异常";
		}
		return result;
	}

	/**
	 * 重置密码 根据vpsid 更改
	 * 
	 * @param vpsid
	 * @param newPassword
	 * @return
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
					UserCourse userCourse = usercourseDao.findByVpsid(vpsid);
					userCourse.setPassword(newPassword);
					usercourseDao.save(userCourse);
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

	/**
	 * 重置密码
	 * 
	 * @param pswd
	 *            重置的密码
	 * @param cid
	 *            课程id
	 * @param session
	 *            学生id
	 * @return
	 */
	@RequestMapping(value = "/resetVpsPwd")
	public @ResponseBody Object resetVpsPwd(String pswd, @RequestParam String cid, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Integer ja = stuService.resetVpsPwd(pswd, cid, user.getId());
		if (ja == 0) {
			result.result = "win成功";
			result.status = 0;
		} else if (ja == 1) {
			result.result = "liunx成功";
			result.status = 1;
		} else if (ja == -1) {
			result.result = "主机不存在";
			result.status = -1;
		} else {
			result.result = "操作失败";
			result.status = 2;
		}

		return result;
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
		List<Message> mes = medao.findAllMessageByUid(user.getId());
		if (mes == null) {
			result.status = 0;
			result.result = ja;
			return result;
		}
		for (Message message : mes) {
			JSONObject jo = new JSONObject();
			// 发送者姓名
			User sender = udao.findUserById(message.getSendUserId());
			User recipient = udao.findUserById(message.getReceiveUserId());
			jo.put("id", message.getId());
			jo.put("senderName", sender.getUserName());
			jo.put("senderUid", sender.getUid());
			jo.put("recipientName", recipient.getUserName());
			jo.put("recipientUid", recipient.getUid());
			jo.put("msgContent", message.getContent());
			jo.put("date", message.getMsgTime());
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
	 * 发生留言到老师
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/sendMessage")
	public @ResponseBody Object getMessage(@RequestParam String tid, @RequestParam String me, HttpSession session) {
		User user = (User) session.getAttribute(User.CUR_USER);
		Message message = new Message();
		message.setContent(me);
		message.setMsgStatus(0);
		message.setReceiveUserId(tid);
		message.setSendUserId(user.getId());
		meDao.save(message);
		result.status = 0;
		result.result = "留言成功";
		return result;
	}

	/**
	 * 修改留言阅读状态
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
		return "student/mysessage";
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
		User user = udao.findUserById(message.getSendUserId());
		CourseDAO cdao = new CourseDAOImpl();
		jo.put("message", message.getContent());
		jo.put("date", message.getMsgTime());
		jo.put("sendName", user.getUserName());
		ja.add(jo);
		result.status = 0;
		result.result = ja;
		return result;
	}

}
