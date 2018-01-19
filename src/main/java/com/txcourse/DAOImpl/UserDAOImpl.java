package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shu.model.User;
import com.txcourse.DAO.UserDAO;

/** 
* @author :liq 
* @version 创建时间：2017年11月30日 下午3:27:41 
* 类说明 
*/
public class UserDAOImpl implements UserDAO {

	private mySessionFactory sessionFactory = mySessionFactory.getInstance();
	
	/**
	 * 根据用户编号 查询用户
	 */
	public User findUserById(String id) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM User where id=:id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List acs = query.list();
			if (acs.size() == 0) {
				return null;
			}

			return (User) acs.get(0);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public User findUserByUId(String uid) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM User where uid=:uid";
			Query query = session.createQuery(hql);
			query.setParameter("uid", uid);
			List acs = query.list();
			if (acs.size() == 0) {
				return null;
			}

			return (User) acs.get(0);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

}
