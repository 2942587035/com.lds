package test;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.junit.Test;

import com.entity.User;

import utils.HibernateUtil;

/**
 * hibernate五种检索方式 导航图对象检索 OID HQL QBC 本地sql 投影查询 动态实例查询 ？传参，：传参
 * 
 * @author lds
 *
 */
public class CheckTypeTest {
	/**
	 * 测试入口
	 */
	@Test
	public void testExecute() {
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			/*testOID(session);*/
			/*testHQL(session);*/
			/*testSQL(session);*/
			/*testWH(session);*/
			/*testMH(session);*/
			/*testTouYing(session);*/
			/*testDTSL(session);*/
			/*testFY(session);*/
			testQBC(session);
			
			
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
           
	}
	
	/**
	 * 导航对象图检索对象中的对象
	 */
	public void testObjectOfObject() {

	}

	/**
	 * OID
	 */
	public void testOID(Session session) {
		User user = session.get(User.class, 1);
		/*System.out.println(user.getCode());*/
	}

	/**
	 * HQL
	 */
	public void testHQL(Session session) {
		String sql = "from User";
        Query<?> query = session.createQuery(sql);
        System.out.println( query.list().size());
	}

	/**
	 * 投影查询
	 */
	public void testTouYing(Session session) {
		String sql = "select s.id,s.name from User s where s.name=:name";
        Query<?> query = session.createQuery(sql);
        query.setParameter("name", "李东升");
        Object[] objects = (Object[]) query.list().get(0);
	}

	/**
	 * 动态实例查询
	 */
	public void testDTSL(Session session) {
		String sql = "select new User(s.id,s.name) from User s where s.name=:name";
        Query<?> query = session.createQuery(sql);
        query.setParameter("name", "李东升");
        User user = (User) query.list().get(0);
	}

	/**
	 * 分页查询
	 */
	public void testFY(Session session) {
		String sql = "from User";
        Query<?> query = session.createQuery(sql);
        query.setFirstResult(1);
        query.setMaxResults(3);
        List<User> list = (List<User>) query.list();
	}

	/**
	 * ?传参,会报错，就不建议用这种方式
	 */
	public void testWH(Session session) {
		String sql = "from User s where s.name=?";
        Query<?> query = session.createQuery(sql);
        query.setParameter(0, "李东升");
        System.out.println( query.list().size());
	}

	/**
	 * :传参
	 */
	public void testMH(Session session) {
		String sql = "from User s where s.name=:name";
        Query<?> query = session.createQuery(sql);
        query.setParameter("name", "李东升");
        System.out.println( query.list().size());
	}

	/**
	 * QBC
	 */
	public void testQBC(Session session) {
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("name", "李东升"));
        criteria.addOrder(Order.desc("id"));
        List<User> list = criteria.list();
	}

	/**
	 * SQL
	 */
	public void testSQL(Session session) {
		String sql = "select * from user";
        Query<?> query = session.createSQLQuery(sql);
        System.out.println( query.list().size());
	}
}
