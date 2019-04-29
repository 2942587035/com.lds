package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.StudentDao;

import net.sf.json.JSONObject;
import utils.HibernateUtil;
/**
 * 提供学生操作服务
 * @author DELL
 *
 */
public class StudentDaoImpl implements StudentDao {
	/**
	 * 查询学生表所有数据
	 */
	@Override
	public List<?> findStudent() {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.xs_id,s.xs_code,s.xs_name,s.xs_sex,s.xs_phone,s.xs_email,s.nj_id,n.nj_name,s.bj_id,b.bj_name from student s ");
		sql.append("left join grade n on n.nj_id=s.nj_id ");
		sql.append("left join class b on b.bj_id=s.bj_id ");
		sql.append("order by s.xs_code desc");
		List<?> list = HibernateUtil.find(sql.toString());
		
		return list;
	}

	/**
	 * 根据编码查询学生个数
	 */
	@Override
	public int findCountStudentByXS_Code(String xs_code) {
		String sql = "select count(*) from student where xs_code= :xs_code";
		Map<String, Object> map = new HashMap<>();
		map.put("xs_code", xs_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据编码不包括当前学生id查询学生个数
	 */
	@Override
	public int findCountStudentByXS_CodeAndId(String xs_code, String xs_id) {
		String sql = "select count(*) from student where xs_code= :xs_code and xs_id != :xs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xs_code", xs_code);
		map.put("xs_id", xs_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 插入所有字段
	 */
	@Override
	public void insertStudent(String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		String sql = "insert into student (xs_code,xs_name,xs_sex,xs_phone,xs_email,nj_id,bj_id) values (:xs_code,:xs_name,:xs_sex,:xs_phone,:xs_email,:nj_id,:bj_id)";
		Map<String, Object> map = new HashMap<>();
		map.put("xs_code", xs_code);
		map.put("xs_name", xs_name);
		map.put("xs_sex", xs_sex);
		map.put("xs_phone", xs_phone);
		map.put("xs_email", xs_email);
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * 修改所有字段
	 */
	@Override
	public void updateStudent(String xs_id, String xs_code, String xs_name, String xs_sex, String xs_phone, String xs_email, String nj_id, String bj_id) {
		String sql = "update student set xs_code=:xs_code,xs_name=:xs_name,xs_sex=:xs_sex,xs_phone=:xs_phone,xs_email=:xs_email,nj_id=:nj_id,bj_id=:bj_id where xs_id=:xs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xs_code", xs_code);
		map.put("xs_name", xs_name);
		map.put("xs_sex", xs_sex);
		map.put("xs_phone", xs_phone);
		map.put("xs_email", xs_email);		
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		map.put("xs_id", xs_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public void deleteStudent(String xs_id) {
		String sql = "delete from student where xs_id= :xs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("xs_id", xs_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * 根据编码查询学生
	 */
	@Override
	public List<?> findStudentByXS_Code(String xs_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.xs_id,s.xs_code,s.xs_name,s.xs_sex,s.xs_phone,s.xs_email,s.nj_id,n.nj_name,s.bj_id,b.bj_name from student s ");
		sql.append("left join grade n on n.nj_id=s.nj_id ");
		sql.append("left join class b on b.bj_id=s.bj_id ");
		sql.append("where xs_code= :xs_code");
		Map<String, Object> map = new HashMap<>();
		map.put("xs_code", xs_code);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	@Override
	public Object findStudentByXS_Id(String xs_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.xs_id,s.xs_code,s.xs_name,s.xs_sex,s.xs_phone,s.xs_email,s.nj_id,n.nj_name,s.bj_id,b.bj_name from student s ");
		sql.append("left join grade n on n.nj_id=s.nj_id ");
		sql.append("left join class b on b.bj_id=s.bj_id ");
		sql.append("where xs_id= :xs_id");
		Map<String, Object> map = new HashMap<>();
		map.put("xs_id", xs_id);
		Object object = HibernateUtil.findById(sql.toString(), map);
		return object;
	}

	@Override
	public List<?> findStudentByNJ_IdAndBJ_Id(String nj_id, String bj_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.xs_id,s.xs_code,s.xs_name,s.xs_sex,s.xs_phone,s.xs_email,s.nj_id,n.nj_name,s.bj_id,b.bj_name from student s ");
		sql.append("left join grade n on n.nj_id=s.nj_id ");
		sql.append("left join class b on b.bj_id=s.bj_id ");
		sql.append("where s.nj_id=:nj_id and s.bj_id=:bj_id ");
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	@Override
	public String findStudentMaxCode() {
		JSONObject successJson = new JSONObject();
		StringBuilder result = new StringBuilder("XS");
		String sql = "select max(xs_code) as code from student";
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
