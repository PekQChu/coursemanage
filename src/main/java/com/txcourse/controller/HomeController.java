package com.txcourse.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.cpa.utility.FileHelper;
import com.shu.cpa.utility.NetResult;
import com.shu.model.User;
import com.shu.xen.api.Vps;
import com.txcourse.DAO.UserDAO;
import com.txcourse.DAOImpl.UserDAOImpl;

/**import jxl.Sheet;
import jxl.Workbook;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	



	@RequestMapping(value="/home")
    public String addSession(@RequestParam String uid,HttpSession session) {
		//User user = (User)session.getAttribute(User.CUR_USER);
	//	HttpSession session ;
		UserDAO udao=new UserDAOImpl();
				User user2 = udao.findUserByUId(uid);
				if(user2!=null){
					session.setAttribute(User.CUR_USER,user2);
					return "student/studentcourses";
					///toStudentCourse
				}else{
					return "";
				}
		//User user = (User)session.getAttribute(User.CUR_USER);
        //if (user.getUserType() == User.TEACHER) {
        //	return "redirect:/teacherCourse/toteacherCoursesPage";
        //} else if (user.getUserType() == User.STUDENT) {
       // 	return "redirect:/studentCourse/toStudentCourse";
        //} else {
        //	return "redirect:/admin/getAllCourses";
        //}
		
    }



		/**
		 vnc:function(id){
		 layer.load(1);
		 jQuery.ajax({
		 type: 'GET',
		 data:{vpsid:id},
		 url: "classvnc",
		 dataType: 'json',
		 success: function(r) {
		 layer.closeAll('loading');
		 if(r.status==0){
		 window.open('http://txxen.hoc.ccshu.net/files/console/console.html?cc=' + encode(r.expireTime + '|' + r.vsip + '|' + r.port + '|' + r.token), '_blank');
		 }else{
		 layer.msg('连接失败', {icon: 5});//
		 }
		 }
		 });
		 },
		 * @param vpsid
		 * @param session
		 * @return
		 */

	/*
	@RequestMapping(value = "/classvnc", method = { RequestMethod.GET })
	public @ResponseBody Object  VNC(
			@RequestParam(value = "vpsid", required = false) long vpsid,
			HttpSession session) {
		Vps vps = new Vps();
		vps.setVpsid(vpsid);

		NetResult r = apiInstance.vps_console(vps,"state");
		JSONObject json=new JSONObject();

		if(r.status!=0){//访问不了返回
			json.put("status", 1);
			return json.toString();
		}

		json=JSONObject.parseObject(r.result.toString());

		if(!json.getString("state").equals("enabled")){//是否开启

			r = apiInstance.vps_console(vps, "enable");
			if (r.status != 0 || !r.result.toString().equals("0")) {//能否开启
				json.put("status", 1);
				return json.toString();
			}

			r = apiInstance.vps_console(vps,"state");
			json=JSONObject.parseObject(r.result.toString());
		}

		json.put("status", 0);
		return json;
	}
	@RequestMapping(value="/uploadUserExcel", method=RequestMethod.POST)
	public @ResponseBody NetResult  uploadStudentExcelUpdatePart(
	        MultipartFile clientFile,
	        Integer n,//学号所在列号
	        HttpSession session,HttpServletRequest request){
		NetResult r=new NetResult();
		int successNum=0;
		int faild=0;
		if(n==null)n=1;
		User user=(User)session.getAttribute(User.CUR_USER);
	    if (clientFile != null&&!clientFile.isEmpty()) {
	    	System.out.println(clientFile.getOriginalFilename());
	    	String str = clientFile.getOriginalFilename();
	    	String kuozhanname = str.substring(str.lastIndexOf(".") + 1);//扩展名
	    	if(!kuozhanname.endsWith("xls")) {
	    		r.status=-1;
	    		r.result="只支持.xls后缀名格式";
	    		return r;
	    	}
	    	   String name = str+"_"+new Date().getTime()+"."+kuozhanname;
	    	   String part = "";
	    	   String sex="" ;
	    	   String phone ="";
	    	   String email ="";
	    	   String userName ="";
	    	   String types="";
	    	   UserDAO udao=new UserDAOImpl();
	    	   User u=new User();

	        try {
	        	String fileName=FileHelper.SaveFileToPath(clientFile, name,request);
	        	Workbook workBook =Workbook.getWorkbook(FileHelper.GetUploadedFile(request,fileName));
//	        	工作簿1
	        	Sheet sheet1 = workBook.getSheet(0);
	        	int colnum=sheet1.getColumns();//列数
				int rownum=sheet1.getRows();//行数
				String uid="";
				for(int i=1;i<rownum;i++) {
					uid = sheet1.getCell(n, i).getContents();
					userName = sheet1.getCell(2, i).getContents();
					part = sheet1.getCell(4, i).getContents();
					sex = sheet1.getCell(3, i).getContents();
					phone = sheet1.getCell(5, i).getContents();
					email = sheet1.getCell(6, i).getContents();
					types = sheet1.getCell(7, i).getContents();
						u=udao.findByUId(uid);
							if(u==null) {u=new User();u.setPassword(uid);}
							u.setPart(part).
							setUid(uid)
							.setEmail(email)
							.setMale(sex.equals("男")?true:false)
							.setPhone(phone)
							.setUserName(userName);
							if(types.contains("教师")) {
								u.setUserType(User.TEACHER);
							}else {
								u.setUserType(User.STUDENT);
							}
//							.setType(0)
//							.setUserType(User.STUDENT)
//							.setUserName(userName);
							udao.save(u);

						successNum++;
				}
				r.result="上传成功"+successNum+"条记录；失败"+faild+"条记录";

			} catch (Exception e) {
				r.status = 1;
				r.result = "上传失败，联系管理员";
			}

	    } else {
	    	r.status = -1;
	    	r.result = "(⊙o⊙)上传失败！";
	    }
	    return r;
	}
	*/
		}

