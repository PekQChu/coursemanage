package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shu.model.User;
import com.txcourse.DAO.CourseDAO;
import com.txcourse.DAO.CourseVpsDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.DAO.UserDAO;
import com.txcourse.model.Course;
import com.txcourse.model.CourseVps;
import com.txcourse.model.UserCourse;
import com.txcourse.service.VpsService;

/**
 * @author :liq
 * @version 创建时间：2017年12月6日 上午11:00:56 类说明
 */
public class CourseVpsDAOImpl implements CourseVpsDAO {

	private mySessionFactory sessionFactory = mySessionFactory.getInstance();
	/**
	 * 根据ip查询CourseVps
	 */
	public CourseVps findCourseVpsByIp(String ip) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from CourseVps where ip = :ip";
			Query query = session.createQuery(hql);
			query.setParameter("ip", ip);
			List<CourseVps> list = query.list();
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

	@Override
	public boolean save(CourseVps cvps) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(cvps);
			tx.commit();
			return true;
		} catch (RuntimeException e) {
			tx.rollback();
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean delete(CourseVps cvps) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE CourseVps WHERE id =:id";
			Query query = session.createQuery(hql);
			query.setParameter("id", cvps.getId());
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
			String hql = "DELETE CourseVps WHERE id =:id";
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
	public List<CourseVps> findAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from CourseVps";
			Query query = session.createQuery(hql);
			List<CourseVps> list = query.list();
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

	/*
	 * 通过课程id 查询所有的学生云机
	 */
	public JSONArray findByCid(String cid) {
		CourseDAO cDao = new CourseDAOImpl();
		Course c = cDao.findCourseById(cid);
		if (c == null) {
			return new JSONArray();
		}
		JSONArray ja = new JSONArray();
		String courseName = c.getCourseName();
		UserCourseDAO ucDao = new UserCourseDAOImpl();
		List<UserCourse> ucs = ucDao.findAllUserByCourseId(cid);
		if (ucs == null) {
			return new JSONArray();
		}
		System.out.println("size = " + ucs.size());
		for (UserCourse uc : ucs) {
			String ip ="";
			String dpass="" ;
			Long vpsid = uc.getVpsid();
			String uid = "";
			String runningStat = "";
			String stuName = "";
			String teaName = "";
			if (uc.getVpsid()!=0) {
				System.out.println(ip);
				CourseVps cVps = new CourseVpsDAOImpl().findCourseVpsByVpsid(uc.getVpsid());
				ip=cVps.getIp();
				dpass=cVps.getDpass();
				System.out.println("密码"+dpass);
				uid = cVps.getUid();
				if (cVps.getState().equals("-1")) {
					runningStat = "安装中";			
				} else {
					runningStat = cVps.getState();
				}
				UserDAO uDao = new UserDAOImpl();
				User stu = uDao.findUserByUId(uid);
				if (stu != null)
					stuName = stu.getUserName();
				User tea = uDao.findUserById(c.getTeacherId());
				if (tea != null)
					teaName = tea.getUserName();
			}
			JSONObject jo = new JSONObject();
			jo.put("courseName", courseName);
			jo.put("ip", ip);
			jo.put("dpass", dpass);
			jo.put("uid", uid);
			jo.put("vpsid", vpsid);
			jo.put("runningStat", runningStat);
			jo.put("stuName", stuName);
			jo.put("teaName", teaName);
			ja.add(jo);
		}
		return ja;
	}

	@Override
	public CourseVps findCourseVpsByVpsid(Long vpsid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from CourseVps where vpsid = :vpsid";
			Query query = session.createQuery(hql);
			query.setParameter("vpsid", vpsid);
			List<CourseVps> list = query.list();
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
