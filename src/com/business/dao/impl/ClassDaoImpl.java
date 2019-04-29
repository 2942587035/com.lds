package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.ClassDao;

import net.sf.json.JSONObject;
import utils.HibernateUtil;

/**
 * 提供班级操作服务
 * @author lds
 *
 */
public class ClassDaoImpl implements ClassDao {
	/**
	 * 查询班级表所有数据
	 */
	@Override
	public List<?> findClass() {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bj_id,b.bj_code,b.bj_name,b.nj_id,g.nj_name from class b ");
		sql.append("left join grade g on g.nj_id = b.nj_id ");
		sql.append("order by b.bj_code desc");
		List<?> list = HibernateUtil.find(sql.toString());
		
		return list;
	}

	/**
	 * 根据编码查询班级个数
	 */
	@Override
	public int findCountClassByBJ_Code(String bj_code) {
		String sql = "select count(*) from class where bj_code= :bj_code";
		Map<String, Object> map = new HashMap<>();
		map.put("bj_code", bj_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据名称查询班级个数
	 */
	@Override
	public int findCountClassByBJ_NameAndNJ_Id(String bj_name,String nj_id) {
		String sql = "select count(*) from class where bj_name= :bj_name and nj_id= :nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("bj_name", bj_name);
		map.put("nj_id", nj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据编码不包括当前班级id查询班级个数
	 */
	@Override
	public int findCountClassByBJ_CodeAndId(String bj_code, String bj_id) {
		String sql = "select count(*) from class where bj_code= :bj_code and bj_id != :bj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("bj_code", bj_code);
		map.put("bj_id", bj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据名称不包括当前班级id查询班级个数
	 */
	@Override
	public int findCountClassBJ_NameAndNJ_IdAndId(String bj_name,String nj_id,String bj_id) {
		String sql = "select count(*) from class where bj_name= :bj_name and nj_id= :nj_id and bj_id != :bj_id";
		System.out.println("select count(*) from class where bj_name= "+bj_name+" and nj_id= "+nj_id+" and bj_id != "+bj_id);
		Map<String, Object> map = new HashMap<>();
		map.put("bj_name", bj_name);
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 插入所有字段
	 */
	@Override
	public void insertClass(String bj_code, String bj_name, String nj_id) {
		String sql = "insert into class (bj_code,bj_name,nj_id) values (:bj_code,:bj_name,:nj_id)";
		Map<String, Object> map = new HashMap<>();
		map.put("bj_code", bj_code);
		map.put("bj_name", bj_name);
		map.put("nj_id", nj_id);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * 修改所有字段
	 */
	@Override
	public void updateClass(String bj_id, String bj_code, String bj_name, String nj_id) {
		String sql = "update class set bj_code=:bj_code,bj_name=:bj_name,nj_id=:nj_id where bj_id=:bj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("bj_code", bj_code);
		map.put("bj_name", bj_name);
		map.put("nj_id", nj_id);
		map.put("bj_id", bj_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public void deleteClass(String bj_id) {
		String sql = "delete from class where bj_id= :bj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("bj_id", bj_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * 根据编码查询班级
	 */
	@Override
	public List<?> findClassByBJ_Code(String bj_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bj_id,b.bj_code,b.bj_name,b.nj_id,g.nj_name from class b ");
		sql.append("left join grade g on g.nj_id = b.nj_id ");
		sql.append("where b.bj_code= :bj_code ");
		sql.append("order by b.bj_code desc");
		Map<String, Object> map = new HashMap<>();
		map.put("bj_code", bj_code);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	/**
	 * 根据年级ID查询班级
	 * @return
	 */
	@Override
	public List<?> findClassByNJ_Id(String nj_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bj_id,b.bj_code,b.bj_name,b.nj_id,g.nj_name from class b ");
		sql.append("left join grade g on g.nj_id = b.nj_id ");
		sql.append("where b.nj_id= :nj_id ");
		sql.append("order by b.bj_code desc");
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	/**
	 * 查询最大编码
	 * 设置新编码=最大编码前两位+最大编码后6位转成int类型后加1然后不足6为前面补0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String findClassMaxCode() {
		JSONObject successJson = new JSONObject();
		StringBuilder result = new StringBuilder("BJ");
		String sql = "select max(bj_code) as code from class";
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
