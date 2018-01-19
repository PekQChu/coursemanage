package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.alibaba.fastjson.JSONArray;
import com.txcourse.DAO.StudentCourseDAO;
import com.txcourse.DAO.UserCourseDAO;
import com.txcourse.model.UserCourse;

/** 
* @author :liq 
* @version 创建时间：2017年11月30日 下午5:14:16 
* 类说明 
*/
public class StudentCourseDAOImpl implements StudentCourseDAO {
	
	private mySessionFactory sessionFactory = mySessionFactory.getInstance();
	
	/**
	 * 根据学生id 查询课程详情
	 */
	@Override
	public JSONArray findCourseByStuId(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String sql = "select c.courseName courseName, c.teacherId teacherId, u.username teacherName, c.id courseId, c.courseSequence courseSequence, "
					+ "c.courseStatus courseStatus, uc.chooseStatus chooseStatus, uc.score "
					+ "from Course c "
					+ "right join UserCourse uc on uc.courseId = c.id "
					+ "left join user u on c.teacherId = u.id "
					+ "WHERE uc.studentId = :studentId and uc.chooseStatus = 1 ";
			Query query = session.createSQLQuery(sql);  
			query.setParameter("studentId", id);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = query.list();
			System.out.println(JSONArray.toJSON(list).toString());
			return (JSONArray) JSONArray.toJSON(list);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	/** 
	 * 模糊查询所有课程
	 * type搜索类型 0空 1 课程号 2老师姓名
	 * sql 参数  
	 * return 课程id	课程号	课程名称	任课教师	课程状态	
	 */
	public JSONArray findAllCourse(Integer type,String sql) {
		String condition = sql;
		String where;
		int flag;//0表示无条件查询， 1表示
		if (type == null || type == 0 || condition == null || condition.trim().equals("")) {
			where = "";
			condition = "";
			flag = 0;
		} else {	
			if (type == 1) {
				where = "where c.courseName like ?";
				flag = 1;
			} else if (type == 2) {
				where  = "where u.username like ?";
				flag = 2;
			} else {
				where = "";
				flag = 0;
			}
		}
		condition = condition.trim();
		String sqlStr = "select c.id courseId, c.courseSequence courseSequence, c.courseName courseName, "
				+ "c.courseContent courseContent, "
				+ "u.username teacherName, c.courseStatus courseStatus "
				+ "from Course c "
				+ "left join user u on c.teacherId = u.id ";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = null;
			if (flag == 0) {
				query = session.createSQLQuery(sqlStr);
			} else {
				query = session.createSQLQuery(sqlStr + where);
				query.setParameter(0, "%" + condition + "%");
			}
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = query.list();
			System.out.println(JSONArray.toJSON(list).toString());
			return (JSONArray) JSONArray.toJSON(list);
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	/**
	 * 上传报告
	 * id 学生id cid课程id  filePath 路径
	 */
	@Override
	public boolean updateUrl(String id, String cid, String filePath) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse where studentId = :studentId and courseId = :courseId";
			Query query = session.createQuery(hql);  
			query.setParameter("studentId", id);
			query.setParameter("courseId", cid);
			List<UserCourse> ucs = query.list();
			if (ucs.size() == 0) {
				return false;
			} else {
				UserCourse uc = ucs.get(0);
				UserCourseDAO uDao = new UserCourseDAOImpl();
				uc.setReportUrl(filePath);
				uDao.save(uc);
				return true;
			}
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

	/**
	 * 加入课程
	 * @param cid 课程id 
	 * @param sid 学生id
	 * @return
	 */
	public boolean joinCourse(String cid, String sid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from UserCourse where studentId = :studentId and courseId = :courseId";
			Query query = session.createQuery(hql);  
			query.setParameter("studentId", sid);
			query.setParameter("courseId", cid);
			List<UserCourse> ucs = query.list();
			long vpsid=0;
			if (ucs.size() == 0) {
				UserCourse uc = new UserCourse();
				uc.setCourseId(cid);
				uc.setStudentId(sid);
				uc.setChooseStatus(1);
				uc.setVpsid(vpsid);
				UserCourseDAO uDao = new UserCourseDAOImpl();
				uDao.save(uc);
			} else {
				UserCourse uc = ucs.get(0);
				uc.setVpsid(vpsid);
				uc.setChooseStatus(1);
				UserCourseDAO uDao = new UserCourseDAOImpl();
				uDao.save(uc);
			}
			return true;
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

}
