package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.ScoreDao;

import utils.HibernateUtil;

/**
 * 提供分数操作服务
 * @author lds
 *
 */
public class ScoreDaoImpl implements ScoreDao {
	/**
	 * 查询学校表所有数据
	 */
	@Override
	public List<?> findScore() {
		String sql = "select xx_id,xx_code,xx_name,xx_type,xx_remark from school order by xx_code desc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	/**
	 * 根据编码查询学校个数
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
	 * 根据名称查询学校个数
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
	 * 根据编码不包括当前学校id查询学校个数
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
	 * 根据名称不包括当前学校id查询学校个数
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
	 * 插入所有字段
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
	 * 修改所有字段
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
	 * 根据id删除
	 */
	@Override
	public void deleteScore(String xx_id) {
		String sql = "delete from school where xx_id= :xx_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xx_id", xx_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * 根据编码查询学校
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
