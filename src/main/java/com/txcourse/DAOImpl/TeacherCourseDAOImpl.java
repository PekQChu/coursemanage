package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shu.model.User;
import com.txcourse.DAO.CourseDAO;
import com.txcourse.DAO.TeacherCourseDAO;
import com.txcourse.model.Course;



/** 
* @author :liq 
* @version 创建时间：2017年11月29日 上午11:29:23 
* 类说明 
*/
public class TeacherCourseDAOImpl implements TeacherCourseDAO {

	private mySessionFactory sessionFactory = mySessionFactory.getInstance();
	
	@Override
	public Boolean addCourser(Course course) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(course);
			tx.commit();
			return true;
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public List<Course> findCourseByTeacherId(String id) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM Course where teacherId = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Course> acs = query.list();
			if (acs.size() == 0) {
				return null;
			}
			return acs;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 * 根据课程id查询课程详情
	 * 
	 * @param id
	 *            课程id
	 * @return
	 */
	public Course findCourseById(String id) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM Course where id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Course> acs = query.list();
			if (acs.size() == 0) {
				return null;
			}
			return acs.get(0);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<Course> findAllCourse() {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM Course";
			Query query = session.createQuery(hql);
			List<Course> acs = query.list();
			if (acs.size() == 0) {
				return null;
			}
			return acs;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	/**
	 *  管理员 关课 开课
	 */
	public boolean openCourse(Course course) {
		course.setCourseStatus(3);
		CourseDAO cDao = new CourseDAOImpl();
		return cDao.save(course);
	}

	/**
	 * 管理员 关课
	 */
	public boolean closeCourse(Course course) {
		course.setCourseStatus(2);
		CourseDAO cDao = new CourseDAOImpl();
		return cDao.save(course);
	}

	/**
	 * 管理员审核课程
	 */
	@Override
	public boolean authorizeCourse(Course course) {
		course.setCourseStatus(1);
		CourseDAO cDao = new CourseDAOImpl();
		return cDao.save(course);
	}

}
