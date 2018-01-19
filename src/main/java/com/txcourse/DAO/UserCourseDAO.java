package com.txcourse.DAO;

import java.util.List;

import com.txcourse.model.UserCourse;

public interface UserCourseDAO {
	void save(UserCourse userCourse);
	
	void delete(UserCourse userCourse);
	
	void delete(String id);
	/**
	 * 根据id 查询usercourse
	 * @param id 主键id
	 * @return
	 */
	UserCourse findByid(String id);
	
	
	UserCourse findByVpsid(Long vpsid);
	
	/**
	 * 根据课程id查询学生详情  已选课
	 * @param id 课程id
	 * @return 
	 */
     List<UserCourse> findUserChooseByCourseId(String id);
     /**
 	 * 根据课程id查询学生详情 未选课
 	 * @param id 课程id
 	 * @return 
 	 */
      List<UserCourse> findUserUnChooseByCourseId(String id);
     /**
   	 * 根据课程id 查询全部学生
   	 * @param id 课程id
   	 * @return 
   	 */
        List<UserCourse> findAllUserByCourseId(String id);
      /**
       * 根据学生id 和课程id 查询 学生该科 ip地址
       * @param sid学生id cid 课程id
       * @return
       */
     UserCourse findBySidAndCid(String cid,String sid);
}
