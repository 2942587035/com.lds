package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.query.Query;

import com.business.dao.inter.UserDao;
import com.entity.User;

import utils.HibernateUtil;

/**
 * �ṩ�û���������
 * @author lds
 *
 */
public class UserDaoImpl implements UserDao {
	/**
	 * ��ѯ�û�����������
	 */
	@Override
	public List<?> findUser() {
		String sql = "select yh_id,yh_code,yh_name,yh_type,yh_password from user order by yh_code desc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	/**
	 * ���ݱ����ѯ�û�����
	 */
	@Override
	public int findCountUserByYH_Code(String yh_code) {
		String sql = "select count(*) from user where yh_code= :yh_code";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_code", yh_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * ���ݱ��벻������ǰ�û�id��ѯ�û�����
	 */
	@Override
	public int findCountUserByYH_CodeAndId(String yh_code, String yh_id) {
		String sql = "select count(*) from user where yh_code= :yh_code and yh_id != :yh_id";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_code", yh_code);
		map.put("yh_id", yh_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * ���������ֶ�
	 */
	@Override
	public void insertUser(String yh_code, String yh_name, String yh_type, String yh_password) {
		String sql = "insert into user (yh_code,yh_name,yh_type,yh_password) values (:yh_code,:yh_name,:yh_type,:yh_password)";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_code", yh_code);
		map.put("yh_name", yh_name);
		map.put("yh_type", yh_type);
		map.put("yh_password", yh_password);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * �޸��û����������
	 */
	@Override
	public void updateUserYH_CodeAndName(String yh_id, String yh_code, String yh_name) {
		String sql = "update user set yh_code=:yh_code,yh_name=:yh_name where yh_id=:yh_id";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_code", yh_code);
		map.put("yh_name", yh_name);
		map.put("yh_id", yh_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * �����û������޸ı��������
	 */
	@Override
	public void updateUserYH_CodeAndNameByYH_Code(String yh_code, String yh_codeNew, String yh_name) {
		String sql = "update user set yh_code=:yh_codeNew,yh_name=:yh_name where yh_code=:yh_code";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_codeNew", yh_codeNew);
		map.put("yh_name", yh_name);
		map.put("yh_code", yh_code);
		HibernateUtil.update(sql, map);
	}

	/**
	 * ����idɾ��
	 */
	@Override
	public void deleteUser(String yh_id) {
		String sql = "delete from user where yh_id= :yh_id";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_id", yh_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * ���ݱ���ɾ���û�
	 */
	@Override
	public void deleteUserByYh_Code(String yh_code) {
		String sql = "delete from user where yh_code= :yh_code";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_code", yh_code);
		HibernateUtil.deleteByCondition(sql, map);
	}

	/**
	 * ���ݱ����ѯ�û�
	 */
	@Override
	public List<?> findUserByYH_Code(String yh_code) {
		String sql = "select yh_id,yh_code,yh_name,yh_type,yh_password from user where yh_code= :yh_code";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_code", yh_code);
		List<?> list = HibernateUtil.findByCondition(sql, map);
		return list;
	}

	/**
	 * �޸��û�����
	 */
	@Override
	public void updateUserYH_Password(String yh_id, String yh_password) {
		String sql = "update user set yh_password = :yh_password where yh_id = :yh_id";
		Map<String, Object> map = new HashMap<>();
		map.put("yh_password", yh_password);
		map.put("yh_id", yh_id);
		HibernateUtil.update(sql, map);
	}

	@Override
	public User getUserByYH_Code(String yh_code) {
		String sql = "select yh_id,yh_code,yh_name,yh_type,yh_password from user where yh_code= :yh_code";
		Query<?> query = HibernateUtil.getCurrentSession().createSQLQuery(sql).addEntity(User.class);
    	query.setParameter("yh_code", yh_code);
		return (User) query.uniqueResult();
	}
}
