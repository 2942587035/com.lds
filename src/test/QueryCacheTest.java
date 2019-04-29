package test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  ��ѯ�������
 * @author lds
 *
 */
public class QueryCacheTest {
	@Test
    public void testQuery() {
    	List<?> list1 = null;
    	List<?> list2 = null;
    	String sqlQuery = "select s.id,s.name,s.code,s.password from User s";
		Session session = HibernateUtil.getSession();
		//��������
		Transaction transaction = session.beginTransaction();
		//������
		try {
			Query<?> query = session.createSQLQuery(sqlQuery).addEntity(User.class);
			query.setCacheable(true);
			list1 = query.list();
			
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
			
			Query<?> query = session1.createSQLQuery(sqlQuery).addEntity(User.class);
			query.setCacheable(true);
			list2 = query.list();
			
			//�����true
			System.out.println(list1 == list2);
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
