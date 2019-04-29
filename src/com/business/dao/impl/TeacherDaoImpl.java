package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.TeacherDao;

import net.sf.json.JSONObject;
import utils.HibernateUtil;

/**
 * 提供教师操作服务
 * @author lds
 *
 */
public class TeacherDaoImpl implements TeacherDao {
	/**
	 * 查询教师表所有数据
	 */
	@Override
	public List<?> findTeacher() {
		String sql = "select js_id,js_code,js_name,js_sex,js_phone,js_email from teacher order by js_code desc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	/**
	 * 根据编码查询教师个数
	 */
	@Override
	public int findCountTeacherByJS_Code(String js_code) {
		String sql = "select count(*) from teacher where js_code= :js_code";
		Map<String, Object> map = new HashMap<>();
		map.put("js_code", js_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据编码不包括当前教师id查询教师个数
	 */
	@Override
	public int findCountTeacherByJS_CodeAndId(String js_code, String js_id) {
		String sql = "select count(*) from teacher where js_code= :js_code and js_id != :js_id";
		Map<String, Object> map = new HashMap<>();
		map.put("js_code", js_code);
		map.put("js_id", js_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 插入所有字段
	 */
	@Override
	public void insertTeacher(String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		String sql = "insert into teacher (js_code,js_name,js_sex,js_phone,js_email) values (:js_code,:js_name,:js_sex,:js_phone,:js_email)";
		Map<String, Object> map = new HashMap<>();
		map.put("js_code", js_code);
		map.put("js_name", js_name);
		map.put("js_sex", js_sex);
		map.put("js_phone", js_phone);
		map.put("js_email", js_email);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * 修改所有字段
	 */
	@Override
	public void updateTeacher(String js_id, String js_code, String js_name, String js_sex, String js_phone, String js_email) {
		String sql = "update teacher set js_code=:js_code,js_name=:js_name,js_sex=:js_sex,js_phone=:js_phone,js_email=:js_email where js_id=:js_id";
		Map<String, Object> map = new HashMap<>();
		map.put("js_code", js_code);
		map.put("js_name", js_name);
		map.put("js_sex", js_sex);
		map.put("js_phone", js_phone);
		map.put("js_email", js_email);
		map.put("js_id", js_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public void deleteTeacher(String js_id) {
		String sql = "delete from teacher where js_id= :js_id";
		Map<String, Object> map = new HashMap<>();
		map.put("js_id", js_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * 根据编码查询教师
	 */
	@Override
	public List<?> findTeacherByJS_Code(String js_code) {
		String sql = "select js_id,js_code,js_name,js_sex,js_phone,js_email from teacher where js_code= :js_code";
		Map<String, Object> map = new HashMap<>();
		map.put("js_code", js_code);
		List<?> list = HibernateUtil.findByCondition(sql, map);
		return list;
	}

	@Override
	public Object findTeacherByJS_Id(String js_id) {
		String sql = "select js_id,js_code,js_name,js_sex,js_phone,js_email from teacher where js_id= :js_id";
		Map<String, Object> map = new HashMap<>();
		map.put("js_id", js_id);
		Object object = HibernateUtil.findById(sql, map);
		return object;
	}

	@Override
	public String findTeacherMaxCode() {
		JSONObject successJson = new JSONObject();
		StringBuilder result = new StringBuilder("JS");
		String sql = "select max(js_code) as code from teacher";
		Map<String, Object> map = (Map<String, Object>) HibernateUtil.findUnique(sql);
		Object code = map.get("code");
		if (code == null) {
			result.append("000001");
		} else {
			String sub = code.toString().substring(2);
			String value = String.valueOf(Integer.valueOf(sub) + 1);
			switch (value.length()) {
			case 1:
				result.append("00000");
				result.append(value);
				break;
			case 2:
				result.append("0000");
				result.append(value);
				break;
			case 3:
				result.append("000");
				result.append(value);
				break;
			case 4:
				result.append("00");
				result.append(value);
				break;
			case 5:
				result.append("0");
				result.append(value);
				break;
			case 6:
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
