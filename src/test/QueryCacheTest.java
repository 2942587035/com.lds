package test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 *  查询缓存测试
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
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			Query<?> query = session.createSQLQuery(sqlQuery).addEntity(User.class);
			query.setCacheable(true);
			list1 = query.list();
			
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
			
			Query<?> query = session1.createSQLQuery(sqlQuery).addEntity(User.class);
			query.setCacheable(true);
			list2 = query.list();
			
			//结果是true
			System.out.println(list1 == list2);
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
