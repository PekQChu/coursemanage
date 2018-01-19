package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.model.UserCourse;

/**
 * @author :liq
 * @version 创建时间：2017年11月30日 下午2:19:44 类说明 UserCourse
 */
public class UserCourseDAOImpl implements UserCourseDAO {
	
	private mySessionFactory sessionFactory = mySessionFactory.getInstance();
	
	@Override
	public void save(UserCourse uc) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(uc);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE UserCourse WHERE id =:id";
			Query query = session.createQuery(hql);  
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	public void delete(UserCourse uc) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE UserCourse WHERE id =:id";
			Query query = session.createQuery(hql);  
			query.setParameter("id", uc.getId());
			query.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * 根据课程id 查询学生详情 已选课
	 */
	@Override
	public List<UserCourse> findUserChooseByCourseId(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse WHERE courseId =:courseId and chooseStatus = 1";
			Query query = session.createQuery(hql);  
			query.setParameter("courseId",id);
			List<UserCourse> list = query.list();
			if (list.size() == 0) {
				return null;
			}
			return list;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据课程id 查询学生详情 未选课
	 */
	@Override
	public List<UserCourse> findUserUnChooseByCourseId(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse WHERE courseId =:courseId and chooseStatus = 0";
			Query query = session.createQuery(hql);  
			query.setParameter("courseId",id);
			List<UserCourse> list = query.list();
			if (list.size() == 0) {
				return null;
			}
			return list;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	/**
     * 根据学生id 和课程id 查询 学生该科 ip地址
     * @param sid学生id cid 课程id
     * @return
     */
	@Override
	public UserCourse findBySidAndCid(String cid, String sid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse WHERE courseId =:courseId and studentId = :studentId";
			Query query = session.createQuery(hql);  
			query.setParameter("courseId", cid);
			query.setParameter("studentId", sid);
			List<UserCourse> list = query.list();
			if (list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	

	 /**
   	 * 根据课程id查询全部学生
   	 * @param id 课程id
   	 * @return 
   	 */
	@Override
	public List<UserCourse> findAllUserByCourseId(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse WHERE courseId =:courseId";
			Query query = session.createQuery(hql);  
			query.setParameter("courseId", id);
			List<UserCourse> list = query.list();
			if (list.size() == 0) {
				return null;
			}
			return list;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public UserCourse findByVpsid(Long vpsid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse WHERE vpsid =:vpsid";
			Query query = session.createQuery(hql);  
			query.setParameter("vpsid", vpsid);
			List<UserCourse> list = query.list();
			if (list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	/**
	 * 根据id 查询usercourse
	 * @param id 主键id
	 * @return
	 */
	@Override
	public UserCourse findByid(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse WHERE id =:id";
			Query query = session.createQuery(hql);  
			query.setParameter("id", id);
			List<UserCourse> list = query.list();
			if (list.size() == 0) {
				return null;
			}
			return list.get(0);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	

}
