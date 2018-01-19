package com.txcourse.DAOImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.txcourse.DAO.MessageDAO;
import com.txcourse.model.CourseVps;
import com.txcourse.model.Message;

/** 
* @author :liq 
* @version 创建时间：2017年12月9日 下午5:30:46 
* 类说明 
*/
public class MessageDAOImpl implements MessageDAO {
	private mySessionFactory sessionFactory = mySessionFactory.getInstance();

	/**
	 *  根据接收者id  查询所有信息
	 */
	public List<Message> findAllMessageByUid(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Message where receiveUserId = :receiveUserId or sendUserId = :sendUserId order by msgStatus ASC,msgTime desc";
			Query query = session.createQuery(hql);
			query.setParameter("receiveUserId", id);
			query.setParameter("sendUserId", id);
			List<Message> list = query.list();
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
     * 根据信息id  查询信息详情
     * @param id
     * @return
     */
	public Message getMessageByid(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Message where id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Message> list = query.list();
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
	public void save(Message m) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(m);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "DELETE Message WHERE id =:id";
			Query query = session.createQuery(hql);
			query.setParameter("id",id);
			query.executeUpdate();
			tx.commit();
			
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			
		} finally {
			session.close();
		}
	}
	
	/**
	 * 根据接受者id查询新信息数量
	 * @param id 接受者id
	 * @return 数量 
	 */
	public int getNewMsgNum(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Message where receiveUserId = :id and msgStatus = 0 order by msgTime";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			List<Message> list = query.list();
			return list.size();
		} catch (RuntimeException e) {
			tx.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			session.close();
		}
	}

	
	

}
