package test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;
/**
   *      ���Ӳ��ԣ���ɾ�Ĳ�
 * @author lds
 *
 */
public class ConnectorDBTest {
	@Test
	public void testExecute() {
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			testAdd(session);
			
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
	 * ����,id��������������
	 * @param session
	 */
	public void testAdd(Session session) {
		User userAdd = new User();
		/*userAdd.setCode("20180002");
		userAdd.setName("�Ŷ�");
		userAdd.setPassword("1");*/
		session.save(userAdd);
	}
	
	/**
	 * ɾ��
	 * @param session
	 */
	public void testDelete(Session session) {
		Query<?> query = session.createSQLQuery("select * from User where id = 2").addEntity(User.class);
		User user = (User) query.uniqueResult();
	    /*user.setCode("20180003");*/
	    session.delete(user);
	}
	
	/**
	 * �޸�
	 * @param session
	 */
	public void testUpdate(Session session) {
		Query<?> query = session.createSQLQuery("select * from User where id = 2").addEntity(User.class);
		User user = (User) query.uniqueResult();
	    /*user.setCode("20180003");*/
		session.update(user);
	}
	
	/**
	 * ��ѯ
	 * @param session
	 */
	public User testQuery(Session session) {
		String sqlQuery = "select * from User where id = 2";
		Query<?> query = session.createSQLQuery(sqlQuery).addEntity(User.class);
		return (User) query.list().get(0);
	}
	
}
