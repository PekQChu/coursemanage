package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.txcourse.DAO.CourseDAO;
import com.txcourse.model.Course;
import com.txcourse.model.UserCourse;

public class CourseDAOImpl implements CourseDAO {
	
	private mySessionFactory sessionFactory = mySessionFactory.getInstance();

	@Override
	public boolean save(Course course) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		boolean flag = false;
		try {
			session.saveOrUpdate(course);
			tx.commit();
			flag = true;
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		return flag;
	}

	@Override
	public boolean delete(Course course) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE Course WHERE id =:id";
			Query query = session.createQuery(hql);  
			query.setParameter("id", course.getId());
			query.executeUpdate();
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
	public boolean deleteById(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE Course WHERE id =:id";
			Query query = session.createQuery(hql);  
			query.setParameter("id", id);
			query.executeUpdate();
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
	public List<Course> findAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Course";
			Query query = session.createQuery(hql);  
			List<Course> list = query.list();
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
	public Course findCourseById(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Course WHERE id =:id";
			Query query = session.createQuery(hql);  
			query.setParameter("id",id);
			List<Course> list = query.list();
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
