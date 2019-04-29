package utils;
/**
 * ��װ���ݿ����
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
	 * sessionFactory�ǵ�����
	 */
	private static SessionFactory sessionFactory;
	
	/**
	 * springע��
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtil.sessionFactory = sessionFactory;
		checkSessionFactory();
	}

	/**
	 * ���SessionFactory
	 * @return
	 */
	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	private static void checkSessionFactory() {
		if (sessionFactory == null) {
			throw new IllegalStateException("sessionFactoryδע�룬����applicationContext.xml�н�sessionFactoryע��HibernateUtil");
		}
	}
	
	/********************************************session***********************************************/
	
	/**
     *  ���openSession 
     *  ����������
     * @return
     */
	public static Session getSession() {
    	return getSessionFactory().openSession();
    }
	
	/**
     *  ���CurrentSession Ŀǰ��ʹ�ô˷���
     *  
     * @return
     */
	public static Session getCurrentSession() {
    	return getSessionFactory().getCurrentSession();
    }
	
	/********************************************���ݿ����������װ***********************************************/
	
	/**
	 * ����
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
	 * ����IDɾ��
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
	 * �޸�
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
	 * ��ѯ����
	 * @param sql
	 */
    public static List<?> find(String sql) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
    	return query.list();
    };
	/**
	 * ����ID��ѯ
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
	 * ��ѯΨһ����
	 * @param sql
	 */
    public static Object findUnique(String sql) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
    	return query.uniqueResult();
    };
	/**
	 * ��������ѯ
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
	 * ��ѯ��������
	 * @param sql
	 */
    public static int getCount(String sql) {
    	Query<?> query = getCurrentSession().createSQLQuery(sql);
    	return ((BigInteger) query.uniqueResult()).intValue();
    };
	/**
	 * ��������ѯ�õ���������
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
