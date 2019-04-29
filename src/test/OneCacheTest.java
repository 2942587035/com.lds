package test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  һ��������ԣ�
 *   method: get save update saveorupdate
 * @author lds
 *
 */
public class OneCacheTest {

	/**
	 * ����,id��������������
	 * @param session
	 */
	public void testAdd() {
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			//����
			User user1 = new User();
			/*user1.setCode("20180002");
			user1.setName("�Ŷ�");
			user1.setPassword("1");*/
			session.save(user1);
			
			//�ѻ��棬����ӡsql
			/*User user2 = session.get(User.class, user1.getId());*/
			//�����true
		/*	System.out.println(user1 == user2);*/
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//�ر�session
			session.close();
		}
	
	}
	
	/**
	 * �޸�
	 * @param session
	 */
	@Test
	public void testUpdate() {
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			User user1 = session.get(User.class, 2);
			/*user1.setName("�Һܺ�");*/
			
			User user2 = session.get(User.class, 2);
			//�����true
			System.out.println(user1 == user2);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//�ر�session
			session.close();
		}
	
	}
	
	/**
	 * ��ѯ��ͬsession��
	 * @param session
	 */
	public void testGet() {
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			//��ӡsql
			User user1 = session.get(User.class, 2);
			//�ѻ��棬����ӡsql
			User user2 = session.get(User.class, 2);
			//�����true
			System.out.println(user1 == user2);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			//�ر�session
			session.close();
		}
	
	}
	
	/**
	 *  ��ѯ(��ͬsession)
	 * @param session
	 */
	public void testGet1() {
		User user1 = null;
		User user2 = null;
		
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			//��ӡsql
			user1 = session.get(User.class, 2);
			
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
			//�޻��棬��ӡsql
			user2 = session1.get(User.class, 2);
			//�����false
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
