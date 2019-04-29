package test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;
/**
   *      连接测试，增删改查
 * @author lds
 *
 */
public class ConnectorDBTest {
	@Test
	public void testExecute() {
		Session session = HibernateUtil.getSession();
		//开启事务
		Transaction transaction = session.beginTransaction();
		//事务处理
		try {
			testAdd(session);
			
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
	 * 增加,id自增，不用设置
	 * @param session
	 */
	public void testAdd(Session session) {
		User userAdd = new User();
		/*userAdd.setCode("20180002");
		userAdd.setName("张东");
		userAdd.setPassword("1");*/
		session.save(userAdd);
	}
	
	/**
	 * 删除
	 * @param session
	 */
	public void testDelete(Session session) {
		Query<?> query = session.createSQLQuery("select * from User where id = 2").addEntity(User.class);
		User user = (User) query.uniqueResult();
	    /*user.setCode("20180003");*/
	    session.delete(user);
	}
	
	/**
	 * 修改
	 * @param session
	 */
	public void testUpdate(Session session) {
		Query<?> query = session.createSQLQuery("select * from User where id = 2").addEntity(User.class);
		User user = (User) query.uniqueResult();
	    /*user.setCode("20180003");*/
		session.update(user);
	}
	
	/**
	 * 查询
	 * @param session
	 */
	public User testQuery(Session session) {
		String sqlQuery = "select * from User where id = 2";
		Query<?> query = session.createSQLQuery(sqlQuery).addEntity(User.class);
		return (User) query.list().get(0);
	}
	
}
