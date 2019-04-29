package test;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  悲观锁: testBG1方法不提交testBG2方法无法查询数据库，只能等待
 *  与乐观锁
 * @author lds
 *
 */
public class LockTest {
	/**
	 * 悲观锁测试
	 */
	@Test
    public void testBG1() {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			User user = session.get(User.class, 1, LockMode.UPGRADE);		
			/*user.setCode("20181226");*/
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
           
	}
    
	/**
	 * 悲观锁测试
	 */
	@Test
    public void testBG2() {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			User user = session.get(User.class, 1, LockMode.UPGRADE);		
			/*user.setName("杜老板");*/
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
           
	}
}
