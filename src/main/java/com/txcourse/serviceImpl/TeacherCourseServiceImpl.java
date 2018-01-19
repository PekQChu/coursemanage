package com.txcourse.serviceImpl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.internal.CoreLogging;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.FileHelper;
import com.shu.model.User;
import com.shu.model.VpsTemplate;
import com.txcourse.DAO.CourseVpsDAO;
import com.txcourse.DAO.TeacherCourseDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAO.VpsTemplateDAO;
import com.txcourse.DAOImpl.CourseVpsDAOImpl;
import com.txcourse.DAOImpl.TeacherCourseDAOImpl;
import com.txcourse.DAOImpl.UserCourseDAOImpl;
import com.txcourse.DAOImpl.UserDAOImpl;
import com.txcourse.DAOImpl.VpsTemplateDAOImpl;
import com.txcourse.model.Course;
import com.txcourse.model.CourseVps;
import com.txcourse.model.UserCourse;
import com.txcourse.service.TeacherCourseService;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.processors.JsonBeanProcessor;

/**
 * @author :liq
 * @version 创建时间：2017年11月29日 下午1:51:28 类说明
 */
public class TeacherCourseServiceImpl implements TeacherCourseService {
	private TeacherCourseDAO tdao = new TeacherCourseDAOImpl();
	private VpsTemplateDAO vdao = new VpsTemplateDAOImpl();
	private UserCourseDAO uCourDao = new UserCourseDAOImpl();
	private UserDAO udao = new UserDAOImpl();
	private CourseVpsDAO vpsDao = new CourseVpsDAOImpl();

	/**
	 * 根据老师id查询课程 id: 教师id
	 */
	@Override
	public JSONArray findCourseDaoByTheacherId(String id) {
		JSONArray ja = new JSONArray();
		List<Course> course = tdao.findCourseByTeacherId(id);
		for (Course cou : course) {
			JSONObject jo = new JSONObject();
			// 学生人数
			String stuNum = (cou.getStudentNum() + "/" + cou.getMaxStudentNum());
			// 课程状态
			String resourceType = "";
			if (cou.getCourseStatus() == 0) {
				resourceType = "未审核";
			} else if (cou.getCourseStatus() == 1) {
				resourceType = "审核通过";
			} else if (cou.getCourseStatus() == 2) {
				resourceType = "课程关闭";
			} else if (cou.getCourseStatus() == 3) {
				resourceType = "课程开启";
			}
			jo.put("courseId", cou.getId());
			jo.put("courseSequence", cou.getCourseSequence());
			jo.put("courseName", cou.getCourseName());
			jo.put("studentNum", stuNum);
			jo.put("CourseType", resourceType);
			jo.put("status",cou.getCourseStatus());
			jo.put("courseTime", cou.getCourseTime());
			ja.add(jo);
		}
		return ja;
	}

	/**
	 * return 1 课程名字为空 ; 2课程时间为空; 3课程描述为空 ; 4最大学生量小于1; 5计算资源类型为空;6 模板为空;7预设积分为空
	 * return 0 创建成功; 8创建失败;
	 */
	public String addCourse(Course course) {
		if (course.getCourseName() == null || course.getCourseName().equals("")) {
			return "课程名字不能为空";
		} else if (course.getCourseTime() == null || course.getCourseTime().equals("")) {
			return "课程时间不能空";
		} else if (course.getCourseContent() == null || course.getCourseContent().equals("")||course.getCourseContent().equals("<p>请输入课程要求：</P>")) {
			return "课程描述不能空";
		} else if (course.getMaxStudentNum() == null || course.getMaxStudentNum() < 1) {
			return "请给定最大学生数";
		} else if (course.getResourceType() == null) {
			return "计算资源类型不能空";
		} else if (course.getTemplateId() == null) {
			return "请选择模板";
		} else if (course.getCourseScore() == null) {
			return "请预设学生积分";
		} else {
			Boolean status = tdao.addCourser(course);
			if (status) {
				String cid = course.getId();
				String studentListUrl = course.getStudentListUrl();
				if (studentListUrl != null && !studentListUrl.equals("")) {
					try {
						Workbook workBook = Workbook.getWorkbook(new File(studentListUrl));
						Sheet sheet1 = workBook.getSheet(0);
						int colnum = sheet1.getColumns();// 列数
						int rownum = sheet1.getRows();// 行数
						String studentId = "";
						String studentName = "";
						String studentNum="";
						UserCourse userCourse;
						int successNum = 0;
						for (int i = 1; i < rownum; i++) {
							userCourse = new UserCourse();
							studentNum = sheet1.getCell(1, i).getContents();
							System.out.println("学号"+studentNum);
							studentName = sheet1.getCell(2, i).getContents();
							User userid = udao.findUserByUId(studentNum);
							if (userid!=null) {
								userCourse.setStudentId(userid.getId());
								userCourse.setCourseId(cid);
								userCourse.setChooseStatus(0);
								uCourDao.save(userCourse);
								successNum++;
							}else {
								System.out.println(studentName+"user表中不存在");
							}							
						}
						String suc = "上传成功" + successNum + "条记录";
						System.out.println(suc);
						return "创建课程成功";
					} catch (BiffException e) {
						// TODO Auto-generated catch block
						System.out.println("上传失败");
						e.printStackTrace();
						return "创建课程失败";
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("上传失败");
						return "创建课程失败";
					}
				} else {
					return "创建课程成功";
				}
			} else {
				return "创建课程失败";
			}

		}

	}

	/**
	 * 课程详情
	 */
	@Override
	public JSONArray findCourseByid(String id) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		if (id == null || id.equals("")) {
			return null;
		} else {
			Course course = tdao.findCourseById(id);
			String osName = vdao.findOSNameByOid(course.getTemplateId());
			String stuNum = (course.getStudentNum() + "/" + course.getMaxStudentNum());
			jo.put("id", course.getId());
			jo.put("courseSequence", course.getCourseSequence());
			jo.put("courseName", course.getCourseName());
			jo.put("studentNum", stuNum);
			jo.put("osname", osName);
			jo.put("courseTime", course.getCourseTime());
			jo.put("courseContent", course.getCourseContent());
			ja.add(jo);
			return ja;
		}
	}

	/**
	 * 渲染实验管理 表格数据
	 * 
	 * @param tid
	 *            教师id
	 * @param cid
	 *            课程id
	 * @return
	 */
	public JSONArray getpracticeGovern(String tid, String cid) {
		JSONArray ja = new JSONArray();
		List<UserCourse> UserList = uCourDao.findUserChooseByCourseId(cid);
		if (UserList != null && UserList.size() > 0) {
			for (UserCourse userCourse : UserList) {
				JSONObject jo = new JSONObject();
				jo.put("id", userCourse.getId());
			//	jo.put("ip", userCourse.getIp());
				jo.put("sid", userCourse.getStudentId());
				// 根据学生id 查询学生学号
				User user = udao.findUserById(userCourse.getStudentId());
				//如果vpid不是0
				if (userCourse.getVpsid()!=0) {	
					CourseVps Coursevps = vpsDao.findCourseVpsByVpsid(userCourse.getVpsid());
					jo.put("vpsid", userCourse.getVpsid());
					jo.put("runningstat", Coursevps.getState());
					jo.put("ip", Coursevps.getIp());
					jo.put("password", Coursevps.getDpass());
					jo.put("experimentStatus", "已经上机");
				}else {
					jo.put("vpsid", "0");
					jo.put("runningstat", "-");
					jo.put("password","-");
					jo.put("experimentStatus", "未上机");
				}
				jo.put("snum", user.getUid());
				jo.put("sname", user.getUserName());
				if (userCourse.getReportUrl() == null) {
					jo.put("reportStatus", "未上传");
					jo.put("reportUrl", "");
				} else {
					jo.put("reportStatus", "已上传");
					jo.put("reportUrl", userCourse.getReportUrl());
				}
				jo.put("score", userCourse.getScore());
				ja.add(jo);
			}
		} else {
			ja = null;
		}
		return ja;
	}

	/** controller 直接处理，没用到service
	 * 批量审批加入课程
	 */
	public JSONArray teacherPass(Integer[] num) {
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		for (int i = 0; i < num.length; i++) {
			jo.put("result", "成功");
		}
		return null;
	}

	/**
	 * 老师 获取所有留言
	 */
	public JSONArray getMessage(String receiveUserId) {
		// TODO Auto-generated method stub
		return null;
	}

}
