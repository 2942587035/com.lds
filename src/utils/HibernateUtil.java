package utils;
/**
 * 封装数据库操作
 * @author lds
 *
 */

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.query.Query;

public class HibernateUtil {
	/********************************************sessionFactory***********************************************/
	
	/**
	 * sessionFactory是单例的
	 */
	private static SessionFactory sessionFactory;
	
	/**
	 * spring注入
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtil.sessionFactory = sessionFactory;
		checkSessionFactory();
	}

	/**
	 * 获得SessionFactory
	 * @return
	 */
	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	private static void checkSessionFactory() {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactory未注入，请在applicationContext.xml中将sessionFactory注入HibernateUtil");
		}
	}
	
	/********************************************session***********************************************/
	
	/**
     *  获得openSession 
     *  测试类在用
     * @return
     */
	public static Session getSession() {
    	return getSessionFactory().openSession();
    }
	
	/**
     *  获得CurrentSession 目前都使用此方法
     *  
     * @return
     */
	public static Session getCurrentSession() {
    	return getSessionFactory().getCurrentSession();
    }
	
	/********************************************数据库操作方法封装***********************************************/
	
	/**
	 * 插入
	 * @param sql
	 */
	public static void insert(String sql,Map<String, Object> data) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	for (String key : data.keySet()) {
    		query.setParameter(key, data.get(key));
		}
    	query.executeUpdate();
    };
	/**
	 * 根据ID删除
	 * @param sql
	 */
    public static void deleteByCondition(String sql,Map<String, Object> data) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	for (String key : data.keySet()) {
    		query.setParameter(key, data.get(key));
		}
    	query.executeUpdate();
    };
	/**
	 * 修改
	 * @param sql
	 */
    public static void update(String sql,Map<String, Object> data) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	for (String key : data.keySet()) {
    		query.setParameter(key, data.get(key));
		}
    	query.executeUpdate();
    };
	/**
	 * 查询所有
	 * @param sql
	 */
    public static List<?> find(String sql) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
    	return query.list();
    };
	/**
	 * 根据ID查询
	 * @param sql
	 */
    public static Object findById(String sql,Map<String, Object> condition) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
    	for (String key : condition.keySet()) {
    		query.setParameter(key, condition.get(key));
		}
    	return query.uniqueResult();
    };
	/**
	 * 查询唯一数据
	 * @param sql
	 */
    public static Object findUnique(String sql) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
    	return query.uniqueResult();
    };
	/**
	 * 按条件查询
	 * @param sql
	 */
    public static List<?> findByCondition(String sql,Map<String, Object> condition) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
    	for (String key : condition.keySet()) {
    		query.setParameter(key, condition.get(key));
		}
    	return query.list();
    };
	/**
	 * 查询总数据量
	 * @param sql
	 */
    public static int getCount(String sql) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	return ((BigInteger) query.uniqueResult()).intValue();
    };
	/**
	 * 按条件查询得到的数据量
	 * @param sql
	 */
    public static int getCountByCondition(String sql,Map<String, Object> condition) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	for (String key : condition.keySet()) {
    		query.setParameter(key, condition.get(key));
		}
    	return ((BigInteger) query.uniqueResult()).intValue();
    };
}
