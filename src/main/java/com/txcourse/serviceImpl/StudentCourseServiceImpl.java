package com.txcourse.serviceImpl;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.NetResult;
import com.shu.model.User;
import com.shu.model.VpsTemplate;
import com.txcourse.DAO.CourseVpsDAO;
import com.txcourse.DAO.StudentCourseDAO;
import com.txcourse.DAO.TeacherCourseDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAO.VpsTemplateDAO;
import com.txcourse.DAOImpl.CourseDAOImpl;
import com.txcourse.DAOImpl.CourseVpsDAOImpl;
import com.txcourse.DAOImpl.StudentCourseDAOImpl;
import com.txcourse.DAOImpl.TeacherCourseDAOImpl;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.DAOImpl.VpsTemplateDAOImpl;
import com.txcourse.model.Course;
import com.txcourse.model.CourseVps;
import com.txcourse.model.UserCourse;
import com.txcourse.service.StudentCourseService;
import com.txcourse.service.VpsService;

/**
 * @author :liq
 * @version 创建时间：2017年11月30日 下午5:12:09 类说明
 */
public class StudentCourseServiceImpl implements StudentCourseService {
	private static final String JSONObject = null;
	private StudentCourseDAO stuDao = new StudentCourseDAOImpl();
	private TeacherCourseDAO teaDao = new TeacherCourseDAOImpl();
	private UserDAO uDao = new UserDAOImpl();
	private UserCourseDAO uCourDao = new UserCourseDAOImpl();
	private VpsTemplateDAO vpsDao = new VpsTemplateDAOImpl();
	private CourseVpsDAO courseVpsDao = new CourseVpsDAOImpl();

	/**
	 * 根据学生id 查询我的所有课程
	 */
	@Override
	public JSONArray findCourseByStuId(String id) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		ja = stuDao.findCourseByStuId(id);
		return ja;
	}

	/**
	 * 根据课程id 查询实验详情
	 */
	@Override
	public JSONArray findCourseByCId(String id, String sid) {
		// 废除
		return null;
	}

	/**
	 * 查询所有课程
	 */
	@Override
	public JSONArray findAllCourse(Integer type, String sql) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		ja = stuDao.findAllCourse(type, sql);
		return ja;
	}

	/**
	 * 根据课程id 查询课程详情
	 */
	public JSONArray findCourseByID(String id, String sid) {
		Course course = teaDao.findCourseById(id);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("sid", sid);
		jo.put("courseSequence", course.getCourseSequence());
		jo.put("tid", course.getTeacherId());
		jo.put("content", course.getCourseContent());
		jo.put("courseName", course.getCourseName());
		jo.put("studentNum", course.getStudentNum());
		jo.put("resourceName", course.getResourceType());
		jo.put("courseTime", course.getCourseTime());
		// 根据课程 学生id 查询ip
		UserCourse Ucourse = uCourDao.findBySidAndCid(id, sid);
		// 如果ip不为空 渲染云机数据
		if (Ucourse != null) {
			jo.put("reportUrl", Ucourse.getReportUrl() == null ? "" : Ucourse.getReportUrl());
			if (Ucourse.getVpsid()!=0L) {
				CourseVps vps = VpsService.getVps(Ucourse.getVpsid());
				String dpass = vps.getDpass();
				jo.put("workTime",Ucourse.getWorkTime());
				CourseVps courseVps = courseVpsDao.findCourseVpsByVpsid(Ucourse.getVpsid());
				if (courseVps!=null) {	
					jo.put("ip", courseVps.getIp());
					jo.put("vpsid", courseVps.getVpsid());
					jo.put("vpsname", courseVps.getVpsname());
					jo.put("osname", courseVps.getOsname());
					jo.put("vpsCpu", courseVps.getCpu());
					jo.put("vpsMem", courseVps.getRam());
					jo.put("passwd", courseVps.getDpass());			
					jo.put("vpsstatus", courseVps.getState());			
					jo.put("status",0);
				}
			}else {
				jo.put("status",-1);
				jo.put("result", "没有主机信息");
			}
			
		} else {
			jo.put("result", "没有课程");
			jo.put("status",-1);
		}
		ja.add(jo);
		return ja;
	}

	/**
	 * 学生上传实验报告 id 学生id cid 课程id filePath 文件路径
	 */
	public JSONArray updateUrl(String id, String cid, String filePath) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		boolean con = stuDao.updateUrl(id, cid, filePath);
		if (con == true) {
			jo.put("result", "上传成功");
			ja.add(jo);
		} else {
			ja = null;
		}
		return ja;
	}

	/**
	 * 加入课程
	 */
	public Integer joinCourse(String cid, String sid) {
		Integer cons=3;
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		UserCourse userCourse = uCourDao.findBySidAndCid(cid, sid);
		if (userCourse == null) {
			boolean con = stuDao.joinCourse(cid, sid);
			if (con) {
				jo.put("result", "加入成功");
				cons=0;
			} else {
				jo.put("result", "加入失败");
				cons=1;
			}
			ja.add(jo);
		} else {
			if (userCourse.getChooseStatus() == 0) {
				boolean con = stuDao.joinCourse(cid, sid);
				if (con) {
					jo.put("result", "加入成功");
					cons=0;
				} else {
					jo.put("result", "加入失败");
					cons=1;
				}
				ja.add(jo);
			} else {
				jo.put("result", "课程已经加入过");
				cons=-1;
				ja.add(jo);
			}		
		}
		return cons;
	}

	/**
	 * 创建资源
	 * 
	 * @param cid
	 *            课程id
	 * @param sid
	 *            学生id
	 * @return
	 */
	public Integer createResources(String cid, String sid,String uid) {
		Integer cons = 3;
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		CourseVps vps = new CourseVps();
		User user = uDao.findUserById(sid);
		UserCourse userCourse = uCourDao.findBySidAndCid(cid, sid);
		Course c = new CourseDAOImpl().findCourseById(cid);
		if (c.getTemplateId() == null) {
			return cons=1;
		}
		if (userCourse.getVpsid() != 0) {
			cons = 3;
			jo.put("result", "已经创建过资源");
			System.out.println("userCourse is null");
		} else {
			vps.setCpu(2);
			//vps.setMaxMem(8);
			vps.setMaxMem(8192);
			vps.setDisk(40);
			vps.setOsid(Integer.parseInt(c.getTemplateId()));
			vps.setVpsname("txCourse" + user.getUid());
			// 创建 云主机
			Long vpsid = VpsService.createCourseVps(vps);
			System.out.println(vpsid);
			if (vpsid != 0) {
				// 创建成功
				userCourse.setVpsid(vpsid);
				userCourse.setExperimentStatus(1);
				CourseVps newVps = VpsService.getVps(vpsid);
				Integer status = newVps.getStatus();
				if (status == 1 || status == 2) {
					newVps.setState("running");
				} else {
					newVps.setState("-1");
				}
				newVps.setUid(uid);
				boolean con = courseVpsDao.save(newVps);
				if (con) {
					userCourse.setIp(newVps.getIp());
					userCourse.setPassword(newVps.getDpass());
					uCourDao.save(userCourse);
					jo.put("result", "创建成功");
					cons = 0;
				} else {
					jo.put("result", "创建失败");
					cons = 1;
				}
			} else {
				// 创建失败
				cons = 1;
			}
		}
		// 返回值
		ja.add(jo);
		return cons;
	}

	/**
	 * 重启资源
	 * 
	 * @param cid
	 *            课程id
	 * @param sid
	 *            学生id
	 * @return
	 */
	public Integer restartVps(Long vpsid) {
		Integer con = 1;
		UserCourse userCourse = uCourDao.findByVpsid(vpsid);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (userCourse != null) {
			CourseVps courseVps = courseVpsDao.findCourseVpsByVpsid(vpsid);
			if (courseVps.getVpsid() != null) {
				System.out.println("vpsid" + courseVps.getVpsid());
				NetResult result = VpsService.restartVps(courseVps.getVpsid());
				// JSONArray status = (JSONArray)
				// JSONArray.toJSON(result.status);
				// String con = status.toString();
				if (result.status == 0) {
					con = 0;
					courseVps.setState("running");
					boolean update = courseVpsDao.save(courseVps);
					jo.put("result", "重启成功");
				} else {
					con = -1;
					jo.put("result", "重启失败");
				}
			}
			ja.add(jo);
		} else {
			con = 1;
			jo.put("result", "主机不存在");
		}
		return con;
	}

	/**
	 * 开机
	 * 
	 * @param cid
	 *            课程id
	 * @param sid
	 *            学生id
	 * @return
	 */
	public Integer openVps(Long vpsid) {
		Integer con = 3;
		UserCourse userCourse = uCourDao.findByVpsid(vpsid);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (userCourse != null) {
			CourseVps courseVps = courseVpsDao.findCourseVpsByVpsid(vpsid);
			if (courseVps.getVpsid() != null && courseVps.getVpsid() != 0) {
				NetResult result = VpsService.startVps(courseVps.getVpsid());
				// JSONArray status = (JSONArray)
				// JSONArray.toJSON(result.status);
				// String con = status.toString();
				if (result.status == 0) {
					jo.put("result", "开机成功");
					courseVps.setState("running");
					// 把状态存入数据库中
					con = 0;
					boolean update = courseVpsDao.save(courseVps);
				} else if (result.status == -1) {
					con = -1;
					jo.put("result", "现在操作不允许（主机以经开启");
				} else {
					con = 1;
					jo.put("result", "开机失败");
				}
			}
			ja.add(jo);
		} else {
			con = 3;
		}
		return con;
	}

	/**
	 * 关机
	 * 
	 * @param cid
	 *            课程id
	 * @param sid
	 *            学生id
	 * @return
	 */
	public Integer shutdownVps(Long vpsid ) {
		Integer con = 3;
		UserCourse userCourse = uCourDao.findByVpsid(vpsid);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (userCourse != null) {
			CourseVps courseVps = courseVpsDao.findCourseVpsByVpsid(vpsid);
			if (courseVps.getVpsid() != null && courseVps.getVpsid() != 0) {
				System.out.println("vpsid" + courseVps.getVpsid());
				NetResult result = VpsService.shutdownVps(courseVps.getVpsid());
				// JSONArray status = (JSONArray)
				// JSONArray.toJSON(result.status);
				// String con = status.toString();
				if (result.status == 0) {
					courseVps.setState("halted");
					// 把状态存入数据库中
					boolean update = courseVpsDao.save(courseVps);
					con = 0;
				} else if (result.status == -1) {
					con = -1;
					jo.put("result", "现在操作不允许（主机以关闭过）");
				} else {
					con = 1;
					jo.put("result", "关机失败");
				}
			}
			ja.add(jo);
		} else {
			con=3;
			jo.put("result", "主机不存在");
		}
		return con;
	}

	/**
	 * 重置密码
	 * 
	 * @param pswd
	 *            用户输入密码
	 * @param cid
	 *            课程id
	 * @param sid
	 *            学生id
	 * @return
	 */
	public Integer resetVpsPwd(String pswd, String cid, String sid) {
		Integer cons = 3;
		UserCourse userCourse = uCourDao.findBySidAndCid(cid, sid);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (userCourse.getVpsid() != 0) {
			CourseVps courseVps = courseVpsDao.findCourseVpsByIp(userCourse.getIp());
			String osname = courseVps.getOsname();
			Long vpsid = userCourse.getVpsid();
			if (osname.indexOf("Windows") != -1) {
				// win重置密码
				NetResult result = VpsService.resetWindowsVpsPwd(vpsid,pswd);
				 JSONArray con = (JSONArray) JSONArray.toJSON(result.result);
				 jo.put("result", con.getString(0));
				 System.out.println(con.getString(0));
				 ja.add(jo);
				// 将新密码存到数据库中
				CourseVps newVps = courseVpsDao.findCourseVpsByIp(userCourse.getIp());
				 newVps.setDpass(pswd);
				 courseVpsDao.save(newVps);
				return cons = 0;
			} else {
				 NetResult result = VpsService.resetLinuxVpsPwd(vpsid);
				 JSONArray con = (JSONArray) JSONArray.toJSON(result.result);
				 jo.put("result", con.getString(0));
				 System.out.println(con.getString(0));
				 // 将新密码存到数据库中
				 CourseVps oldvps = VpsService.getVps(vpsid);
				 CourseVps newVps = courseVpsDao.findCourseVpsByIp(userCourse.getIp());
				 newVps.setDpass(oldvps.getDpass());
				 courseVpsDao.save(newVps);
				 ja.add(jo);
				return cons = 1;
			}
		} else {
			jo.put("result", "主机不存在");
			return cons = -1;
		}

	}

	/**
	 * 获取主机的操作系统
	 * 
	 * @param cid
	 *            课程id
	 * @param sid
	 *            学生id
	 * @return
	 */
	public Integer getVpsSystems(String cid, String sid) {
		UserCourse userCourse = uCourDao.findBySidAndCid(cid, sid);
		Integer con = null;
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (userCourse.getIp() != null) {
			CourseVps courseVps = courseVpsDao.findCourseVpsByIp(userCourse.getIp());
			// 获取主机的操作系统
			String osname = courseVps.getOsname();
			if (osname.indexOf("windows") != -1) {
				con = 1;
			} else {
				con = 2;
			}
		}
		return con;

	}
}
