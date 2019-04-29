package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.ScoreDao;

import utils.HibernateUtil;

/**
 * �ṩ������������
 * @author lds
 *
 */
public class ScoreDaoImpl implements ScoreDao {
	/**
	 * ��ѯѧУ����������
	 */
	@Override
	public List<?> findScore() {
		String sql = "select xx_id,xx_code,xx_name,xx_type,xx_remark from school order by xx_code desc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	/**
	 * ���ݱ����ѯѧУ����
	 */
	@Override
	public int findCountScoreByXX_Code(String xx_code) {
		String sql = "select count(*) from school where xx_code= :xx_code";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_code", xx_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * �������Ʋ�ѯѧУ����
	 */
	@Override
	public int findCountScoreByXX_Name(String xx_name) {
		String sql = "select count(*) from school where xx_name= :xx_name";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_name", xx_name);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * ���ݱ��벻������ǰѧУid��ѯѧУ����
	 */
	@Override
	public int findCountScoreByXX_CodeAndId(String xx_code, String xx_id) {
		String sql = "select count(*) from school where xx_code= :xx_code and xx_id != :xx_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_code", xx_code);
		map.put("xx_id", xx_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * �������Ʋ�������ǰѧУid��ѯѧУ����
	 */
	@Override
	public int findCountScoreByXX_NameAndId(String xx_name, String xx_id) {
		String sql = "select count(*) from school where xx_name= :xx_name and xx_id != :xx_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_name", xx_name);
		map.put("xx_id", xx_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * ���������ֶ�
	 */
	@Override
	public void insertScore(String xx_code, String xx_name, String xx_type, String xx_remark) {
		String sql = "insert into school (xx_code,xx_name,xx_type,xx_remark) values (:xx_code,:xx_name,:xx_type,:xx_remark)";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_code", xx_code);
		map.put("xx_name", xx_name);
		map.put("xx_type", xx_type);
		map.put("xx_remark", xx_remark);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * �޸������ֶ�
	 */
	@Override
	public void updateScore(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark) {
		String sql = "update school set xx_code=:xx_code,xx_name=:xx_name,xx_type=:xx_type,xx_remark=:xx_remark where xx_id=:xx_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_code", xx_code);
		map.put("xx_name", xx_name);
		map.put("xx_type", xx_type);
		map.put("xx_remark", xx_remark);
		map.put("xx_id", xx_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * ����idɾ��
	 */
	@Override
	public void deleteScore(String xx_id) {
		String sql = "delete from school where xx_id= :xx_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_id", xx_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * ���ݱ����ѯѧУ
	 */
	@Override
	public List<?> findScoreByXX_Code(String xx_code) {
		String sql = "select xx_id,xx_code,xx_name,xx_type,xx_remark from school where xx_code= :xx_code";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_code", xx_code);
		List<?> list = HibernateUtil.findByCondition(sql, map);
		return list;
	}
}
