package test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  一级缓存测试：
 *   method: get save update saveorupdate
 * @author lds
 *
 */
public class OneCacheTest {

	/**
	 * 增加,id自增，不用设置
	 * @param session
	 */
	public void testAdd() {
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			//插入
			User user1 = new User();
			/*user1.setCode("20180002");
			user1.setName("张东");
			user1.setPassword("1");*/
			session.save(user1);
			
			//已缓存，不打印sql
			/*User user2 = session.get(User.class, user1.getId());*/
			//结果是true
		/*	System.out.println(user1 == user2);*/
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//关闭session
			session.close();
		}
	
	}
	
	/**
	 * 修改
	 * @param session
	 */
	@Test
	public void testUpdate() {
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			User user1 = session.get(User.class, 2);
			/*user1.setName("我很好");*/
			
			User user2 = session.get(User.class, 2);
			//结果是true
			System.out.println(user1 == user2);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//关闭session
			session.close();
		}
	
	}
	
	/**
	 * 查询（同session）
	 * @param session
	 */
	public void testGet() {
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			//打印sql
			User user1 = session.get(User.class, 2);
			//已缓存，不打印sql
			User user2 = session.get(User.class, 2);
			//结果是true
			System.out.println(user1 == user2);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//关闭session
			session.close();
		}
	
	}
	
	/**
	 *  查询(不同session)
	 * @param session
	 */
	public void testGet1() {
		User user1 = null;
		User user2 = null;
		
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			//打印sql
			user1 = session.get(User.class, 2);
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//关闭session
			session.close();
		}
		
		Session session1 = HibernateUtil.getSession();
		//开启事务
		Transaction transaction1 = session1.beginTransaction();
		//事务处理
		try {
			//无缓存，打印sql
			user2 = session1.get(User.class, 2);
			//结果是false
			System.out.println(user1 == user2);
			transaction1.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction1.rollback();
		} finally {
			//关闭session1
			session1.close();
		}
	
	}

}
