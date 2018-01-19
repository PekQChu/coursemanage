package com.txcourse.DAO;

import com.shu.model.User;

/** 
* @author :liq 
* @version 创建时间：2017年11月30日 下午3:24:49 
* 类说明  用户查询
*/
public interface UserDAO {
	/**
	 * 根据用户编号 查询用户
	 * @param id 用户编号
	 * @return
	 */
    User findUserById(String id);
    /**
	 * 根据用户编号 查询用户
	 * @param uid 学号或者工号
	 * @return
	 */
    User findUserByUId(String uid);
}
