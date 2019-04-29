package test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  �����������
 * @author lds
 *
 */
public class SecondCacheTest {
	/**
	 *  ��ѯ(��ͬsession)
	 * @param session
	 */
	@Test
	public void testGet1() {
		User user1 = null;
		User user2 = null;
		
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			//��ӡsql
			user1 = session.get(User.class, 1);
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//�ر�session
			session.close();
		}
		
		Session session1 = HibernateUtil.getSession();
		//��������
		Transaction transaction1 = session1.beginTransaction();
		//������
		try {
			//���棬����ӡsql
			user2 = session1.get(User.class, 1);
			//�����false�����������������������new��
			System.out.println(user1 == user2);
			transaction1.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction1.rollback();
		} finally {
			//�ر�session1
			session1.close();
		}
	
	}

}
