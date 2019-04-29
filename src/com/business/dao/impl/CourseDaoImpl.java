package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.CourseDao;

import net.sf.json.JSONObject;
import utils.HibernateUtil;

/**
 * �ṩ�γ̲�������
 * @author lds
 *
 */
public class CourseDaoImpl implements CourseDao {
	/**
	 * ��ѯ�γ̱���������
	 */
	@Override
	public List<?> findCourse() {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.kc_id,c.kc_code,c.kc_name,c.nj_id,g.nj_name from course c ");
		sql.append("left join grade g on g.nj_id = c.nj_id ");
		sql.append("order by c.kc_code desc");
		List<?> list = HibernateUtil.find(sql.toString());
		
		return list;
	}

	/**
	 * ���ݱ����ѯ�γ̸���
	 */
	@Override
	public int findCountCourseByBJ_Code(String kc_code) {
		String sql = "select count(*) from course where kc_code= :kc_code";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_code", kc_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * �������Ʋ�ѯ�γ̸���
	 */
	@Override
	public int findCountCourseByBJ_NameAndNJ_Id(String kc_name,String nj_id) {
		String sql = "select count(*) from course where kc_name= :kc_name and nj_id= :nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_name", kc_name);
		map.put("nj_id", nj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * ���ݱ��벻������ǰ�γ�id��ѯ�γ̸���
	 */
	@Override
	public int findCountCourseByBJ_CodeAndId(String kc_code, String kc_id) {
		String sql = "select count(*) from course where kc_code= :kc_code and kc_id != :kc_id";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_code", kc_code);
		map.put("kc_id", kc_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * �������Ʋ�������ǰ�γ�id��ѯ�γ̸���
	 */
	@Override
	public int findCountCourseBJ_NameAndNJ_IdAndId(String kc_name,String nj_id,String kc_id) {
		String sql = "select count(*) from course where kc_name= :kc_name and nj_id= :nj_id and kc_id != :kc_id";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_name", kc_name);
		map.put("nj_id", nj_id);
		map.put("kc_id", kc_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * ���������ֶ�
	 */
	@Override
	public void insertCourse(String kc_code, String kc_name, String nj_id) {
		String sql = "insert into course (kc_code,kc_name,nj_id) values (:kc_code,:kc_name,:nj_id)";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_code", kc_code);
		map.put("kc_name", kc_name);
		map.put("nj_id", nj_id);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * �޸������ֶ�
	 */
	@Override
	public void updateCourse(String kc_id, String kc_code, String kc_name, String nj_id) {
		String sql = "update course set kc_code=:kc_code,kc_name=:kc_name,nj_id=:nj_id where kc_id=:kc_id";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_code", kc_code);
		map.put("kc_name", kc_name);
		map.put("nj_id", nj_id);
		map.put("kc_id", kc_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * ����idɾ��
	 */
	@Override
	public void deleteCourse(String kc_id) {
		String sql = "delete from course where kc_id= :kc_id";
		Map<String, Object> map = new HashMap<>();
		map.put("kc_id", kc_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * ���ݱ����ѯ�γ�
	 */
	@Override
	public List<?> findCourseByBJ_Code(String kc_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.kc_id,c.kc_code,c.kc_name,c.nj_id,g.nj_name from course c ");
		sql.append("left join grade g on g.nj_id = c.nj_id ");
		sql.append("where c.kc_code= :kc_code ");
		sql.append("order by c.kc_code desc");
		Map<String, Object> map = new HashMap<>();
		map.put("kc_code", kc_code);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	/**
	 * �����꼶ID��ѯ�γ�
	 * @return
	 */
	@Override
	public List<?> findCourseByNJ_Id(String nj_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.kc_id,c.kc_code,c.kc_name,c.nj_id,g.nj_name from course c ");
		sql.append("left join grade g on g.nj_id = c.nj_id ");
		sql.append("where c.nj_id= :nj_id ");
		sql.append("order by c.kc_code desc");
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	@Override
	public String findCourseMaxCode() {
		JSONObject successJson = new JSONObject();
		StringBuilder result = new StringBuilder("KC");
		String sql = "select max(kc_code) as code from course";
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
