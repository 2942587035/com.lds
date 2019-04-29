package test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  二级缓存测试
 * @author lds
 *
 */
public class SecondCacheTest {
	/**
	 *  查询(不同session)
	 * @param session
	 */
	@Test
	public void testGet1() {
		User user1 = null;
		User user2 = null;
		
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			//打印sql
			user1 = session.get(User.class, 1);
			
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
			//缓存，不打印sql
			user2 = session1.get(User.class, 1);
			//结果是false（二级缓存中区对象会重新new）
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
