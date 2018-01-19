package com.txcourse.DAO;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.txcourse.model.UserCourse;

/**
 * @author :liq
 * @version 创建时间：2017年11月30日 下午5:18:10 类说明
 */

public interface StudentCourseDAO {
	/**
	 * 根据学生id 查询所有课程
	 * @param id  学生id
	 * @return
	 */
	JSONArray findCourseByStuId(String id);
    /**
     * 查询所有课程 模糊查询
     * @param type 查询类型 0空 1
     * @param sql 参数
     * @return
     */
	JSONArray findAllCourse(Integer type,String sql);
	/**
	 * 学生上传实验报告
	 * @param id 学生id
	 * @param cid 课程id
	 * @param filePath 地址
	 * @return
	 */
    boolean updateUrl(String id,String cid, String filePath);
    /**
	 * 加入课程
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
    boolean joinCourse(String cid,String sid);
}
