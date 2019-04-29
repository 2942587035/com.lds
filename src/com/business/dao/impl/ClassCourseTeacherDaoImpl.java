package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.ClassCourseTeacherDao;

import utils.HibernateUtil;

/**
 * 提供班级课程教师操作服务
 * @author lds
 *
 */
public class ClassCourseTeacherDaoImpl implements ClassCourseTeacherDao {
	/**
	 * 查询学校表所有数据
	 */
	@Override
	public List<?> findClassCourseTeacher() {
		String sql = "select nbkj_id,nj_id,bj_id,kc_id,js_id from class_course_teacher order by nj_id desc";
		List<?> list = HibernateUtil.find(sql);
		
		return list;
	}

	/**
	 * 根据年级班级课程查询教师个数
	 */
	@Override
	public int findCountClassCourseTeacherByNBKJ(String nj_id, String bj_id, String kc_id, String js_id) {
		String sql = "select count(*) from class_course_teacher where nj_id= :nj_id and bj_id= :bj_id and kc_id= :kc_id and js_id= :js_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		map.put("kc_id", kc_id);
		map.put("js_id", js_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据年级班级课程不包括当前id查询教师个数
	 */
	@Override
	public int findCountClassCourseTeacherByNBKJAndId(String nj_id, String bj_id, String kc_id, String js_id, String nbkj_id) {
		String sql = "select count(*) from class_course_teacher where nj_id= :nj_id and bj_id= :bj_id and kc_id= :kc_id and js_id= :js_id and nbkj_id != :nbkj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		map.put("kc_id", kc_id);
		map.put("js_id", js_id);
		map.put("nbkj_id", nbkj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 插入所有字段
	 */
	@Override
	public void insertClassCourseTeacher(String nj_id, String bj_id, String kc_id, String js_id) {
		String sql = "insert into class_course_teacher (nj_id,bj_id,kc_id,js_id) values (:nj_id,:bj_id,:kc_id,:js_id)";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		map.put("kc_id", kc_id);
		map.put("js_id", js_id);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * 修改所有字段
	 */
	@Override
	public void updateClassCourseTeacher(String nbkj_id, String nj_id, String bj_id, String kc_id, String js_id) {
		String sql = "update class_course_teacher set nj_id=:nj_id,bj_id=:bj_id,kc_id=:kc_id,js_id=:js_id where nbkj_id=:nbkj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		map.put("kc_id", kc_id);
		map.put("js_id", js_id);
		map.put("nbkj_id", nbkj_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public void deleteClassCourseTeacher(String nbkj_id) {
		String sql = "delete from class_course_teacher where nbkj_id= :nbkj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("nbkj_id", nbkj_id);
		HibernateUtil.deleteByCondition(sql, map);
	}

	/**
	 * 根据年级ID查询分配教师表
	 */
	@Override
	public List<?> findClassCourseTeacherByNJ_Id(String nj_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cct.nbkj_id,cct.nj_id,g.nj_name,cct.bj_id,b.bj_name,cct.kc_id,c.kc_name,cct.js_id,t.js_name,t.js_code from class_course_teacher cct ");
		sql.append("left join grade g on g.nj_id = cct.nj_id ");
		sql.append("left join course c on c.kc_id = cct.kc_id ");
		sql.append("left join class b on b.bj_id = cct.bj_id ");
		sql.append("left join teacher t on t.js_id = cct.js_id ");
		sql.append("where cct.nj_id = :nj_id ");
		sql.append("order by js_id desc");
		
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);

		return list;}

	/**
	 * 查询增加的数据
	 */
	@Override
	public List<?> findClassCourseTeacherByNBKJ(String nj_id, String bj_id, String kc_id, String js_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cct.nbkj_id,cct.nj_id,g.nj_name,cct.bj_id,b.bj_name,cct.kc_id,c.kc_name,cct.js_id,t.js_name,t.js_code from class_course_teacher cct ");
		sql.append("left join grade g on g.nj_id = cct.nj_id ");
		sql.append("left join course c on c.kc_id = cct.kc_id ");
		sql.append("left join class b on b.bj_id = cct.bj_id ");
		sql.append("left join teacher t on t.js_id = cct.js_id ");
		sql.append("where cct.nj_id = :nj_id and cct.bj_id = :bj_id and cct.kc_id = :kc_id and cct.js_id = :js_id ");
		sql.append("order by js_id desc");
		
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		map.put("kc_id", kc_id);
		map.put("js_id", js_id);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);

		return list;}

	@Override
	public Object findClassCourseTeacherByNBKJ_Id(String nbkj_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select cct.nbkj_id,cct.nj_id,g.nj_name,cct.bj_id,b.bj_name,cct.kc_id,c.kc_name,cct.js_id,t.js_name,t.js_code from class_course_teacher cct ");
		sql.append("left join grade g on g.nj_id = cct.nj_id ");
		sql.append("left join course c on c.kc_id = cct.kc_id ");
		sql.append("left join class b on b.bj_id = cct.bj_id ");
		sql.append("left join teacher t on t.js_id = cct.js_id ");
		sql.append("where cct.nbkj_id = :nbkj_id ");
		
		Map<String, Object> map = new HashMap<>();
		map.put("nbkj_id", nbkj_id);
		Object object = HibernateUtil.findById(sql.toString(), map);

		return object;
	}
	
}
