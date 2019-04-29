package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.GradeDao;

import net.sf.json.JSONObject;
import utils.HibernateUtil;

/**
 * 提供年级操作服务
 * @author lds
 *
 */
public class GradeDaoImpl implements GradeDao {
	/**
	 * 查询年级表所有数据
	 */
	@Override
	public List<?> findGrade() {
		String sql = "select nj_id,nj_code,nj_name from grade order by nj_code desc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	/**
	 * 根据编码查询年级个数
	 */
	@Override
	public int findCountGradeByNJ_Code(String nj_code) {
		String sql = "select count(*) from grade where nj_code= :nj_code";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_code", nj_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据名称查询年级个数
	 */
	@Override
	public int findCountGradeByNJ_Name(String nj_name) {
		String sql = "select count(*) from grade where nj_name= :nj_name";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_name", nj_name);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据编码不包括当前年级id查询年级个数
	 */
	@Override
	public int findCountGradeByNJ_CodeAndId(String nj_code, String nj_id) {
		String sql = "select count(*) from grade where nj_code= :nj_code and nj_id != :nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_code", nj_code);
		map.put("nj_id", nj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据名称不包括当前年级id查询年级个数
	 */
	@Override
	public int findCountGradeByNJ_NameAndId(String nj_name, String nj_id) {
		String sql = "select count(*) from grade where nj_name= :nj_name and nj_id != :nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_name", nj_name);
		map.put("nj_id", nj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 插入所有字段
	 */
	@Override
	public void insertGrade(String nj_code, String nj_name) {
		String sql = "insert into grade (nj_code,nj_name) values (:nj_code,:nj_name)";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_code", nj_code);
		map.put("nj_name", nj_name);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * 修改所有字段
	 */
	@Override
	public void updateGrade(String nj_id, String nj_code, String nj_name) {
		String sql = "update grade set nj_code=:nj_code,nj_name=:nj_name where nj_id=:nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_code", nj_code);
		map.put("nj_name", nj_name);
		map.put("nj_id", nj_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public void deleteGrade(String nj_id) {
		String sql = "delete from grade where nj_id= :nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * 根据编码查询年级
	 */
	@Override
	public List<?> findGradeByNJ_Code(String nj_code) {
		String sql = "select nj_id,nj_code,nj_name from grade where nj_code= :nj_code";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_code", nj_code);
		List<?> list = HibernateUtil.findByCondition(sql, map);
		return list;
	}

	/**
	 * 查询年级：年级id和名称
	 */
	@Override
	public List<?> findGrade_NameAndId() {
		String sql = "select nj_id as id,nj_name as text from grade order by nj_code asc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	@Override
	public String findGradeMaxCode() {
		JSONObject successJson = new JSONObject();
		StringBuilder result = new StringBuilder("NJ");
		String sql = "select max(nj_code) as code from grade";
		Map<String, Object> map = (Map<String, Object>) HibernateUtil.findUnique(sql);
		Object code = map.get("code");
		if (code == null) {
			result.append("00001");
		} else {
			String sub = code.toString().substring(2);
			String value = String.valueOf(Integer.valueOf(sub) + 1);
			switch (value.length()) {
			case 1:
				result.append("0000");
				result.append(value);
				break;
			case 2:
				result.append("000");
				result.append(value);
				break;
			case 3:
				result.append("00");
				result.append(value);
				break;
			case 4:
				result.append("0");
				result.append(value);
				break;
			case 5:
				result.append(value);
				break;

			default:
				break;
			}
		}
		
		successJson.put("msg", result.toString());
		return successJson.toString();
	}
}
