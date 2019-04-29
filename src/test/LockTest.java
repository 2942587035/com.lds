package test;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  ������: testBG1�������ύtestBG2�����޷���ѯ���ݿ⣬ֻ�ܵȴ�
 *  ���ֹ���
 * @author lds
 *
 */
public class LockTest {
	/**
	 * ����������
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
	 * ����������
	 */
	@Test
    public void testBG2() {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			User user = session.get(User.class, 1, LockMode.UPGRADE);		
			/*user.setName("���ϰ�");*/
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
           
	}
}
