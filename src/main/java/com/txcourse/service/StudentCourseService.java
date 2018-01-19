package com.txcourse.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.txcourse.DAO.StudentCourseDAO;
import com.txcourse.DAOImpl.StudentCourseDAOImpl;

/** 
* @author :liq 
* @version 创建时间：2017年11月30日 下午5:11:42 
* 类说明  学生端 service
*/
@Service
public interface StudentCourseService {
	
	/**
	 * 根据 学生id 查课程
	 * @param id 
	 * @return
	 */
	JSONArray findCourseByStuId(String id);

	
	
	/**
	 * 根据课程的id 查询实验详情
	 * @param cid 课程id  sid 学生id
	 * @return
	 */
	JSONArray findCourseByCId(String cid,String sid);
	
	/**
	 * 查询 所有课程
	 * @param type 搜索条件 
	 * @param sql 参数
	 * @return
	 */
	JSONArray findAllCourse(Integer type,String sql);
	
	/**
	 * 根据课程id 查询课程详情
	 * @param id 课程id   Sid  学生id
	 * @return
	 */
	JSONArray findCourseByID(String id,String Sid);
	/**
	 * 加入课程
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer joinCourse(String cid,String sid);
	/**
	 * 创建资源
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer createResources(String cid,String sid,String uid);
	
	/**
	 * 学生上传实验报告
	 * @param id 学生学号  cid 课程id
	 * @param filePath 实验报告地址
	 * @return
	 */
	JSONArray updateUrl(String id,String cid,String filePath);
	/**
	 * 重启资源
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer restartVps(Long vpsid);
	/**
	 * 开机
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer openVps(Long vpsid);
	/**
	 * 关机
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer shutdownVps(Long vpsid);
	/**
	 * 获取主机的操作系统
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer getVpsSystems(String cid,String sid);
	/**
	 * 重置密码
	 * @param pawd 用户输出的重置密码 
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	Integer resetVpsPwd(String pawd,String cid,String sid);
}
