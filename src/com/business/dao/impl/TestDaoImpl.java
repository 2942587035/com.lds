package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.TestDao;

import net.sf.json.JSONObject;
import utils.HibernateUtil;

/**
 * 提供测试操作服务
 * @author lds
 *
 */
public class TestDaoImpl implements TestDao {
	/**
	 * 查询课程表所有数据
	 */
	@Override
	public List<?> findTest() {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.cs_id,c.cs_code,c.cs_name,c.cs_type,c.cs_remark,c.nj_id,g.nj_name from test c ");
		sql.append("left join grade g on g.nj_id = c.nj_id ");
		sql.append("order by c.cs_code desc");
		List<?> list = HibernateUtil.find(sql.toString());
		
		return list;
	}

	/**
	 * 根据编码查询课程个数
	 */
	@Override
	public int findCountTestByCS_Code(String cs_code) {
		String sql = "select count(*) from test where cs_code= :cs_code";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_code", cs_code);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据名称查询课程个数
	 */
	@Override
	public int findCountTestByCS_NameAndNJ_Id(String cs_name,String nj_id) {
		String sql = "select count(*) from test where cs_name= :cs_name and nj_id= :nj_id";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_name", cs_name);
		map.put("nj_id", nj_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据编码不包括当前课程id查询课程个数
	 */
	@Override
	public int findCountTestByCS_CodeAndId(String cs_code, String cs_id) {
		String sql = "select count(*) from test where cs_code= :cs_code and cs_id != :cs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_code", cs_code);
		map.put("cs_id", cs_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 根据名称不包括当前课程id查询课程个数
	 */
	@Override
	public int findCountTestCS_NameAndNJ_IdAndId(String cs_name,String nj_id,String cs_id) {
		String sql = "select count(*) from test where cs_name= :cs_name and nj_id= :nj_id and cs_id != :cs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_name", cs_name);
		map.put("nj_id", nj_id);
		map.put("cs_id", cs_id);
		int count = HibernateUtil.getCountByCondition(sql, map);
		return count;
	}

	/**
	 * 插入所有字段
	 */
	@Override
	public void insertTest(String cs_code, String cs_name, String cs_type, String cs_remark, String nj_id) {
		String sql = "insert into test (cs_code,cs_name,cs_type,cs_remark,nj_id) values (:cs_code,:cs_name,:cs_type,:cs_remark,:nj_id)";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_code", cs_code);
		map.put("cs_name", cs_name);
		map.put("cs_type", cs_type);
		map.put("cs_remark", cs_remark);
		map.put("nj_id", nj_id);
		HibernateUtil.insert(sql, map);
	}

	/**
	 * 修改所有字段
	 */
	@Override
	public void updateTest(String cs_id,String cs_code, String cs_name, String cs_type, String cs_remark, String nj_id) {
		String sql = "update test set cs_code=:cs_code,cs_name=:cs_name,cs_type=:cs_type,cs_remark=:cs_remark,nj_id=:nj_id where cs_id=:cs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_code", cs_code);
		map.put("cs_name", cs_name);
		map.put("cs_type", cs_type);
		map.put("cs_remark", cs_remark);
		map.put("nj_id", nj_id);
		map.put("cs_id", cs_id);
		HibernateUtil.update(sql, map);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public void deleteTest(String cs_id) {
		String sql = "delete from test where cs_id= :cs_id";
		Map<String, Object> map = new HashMap<>();
		map.put("cs_id", cs_id);
		HibernateUtil.deleteByCondition(sql, map);
	}
	
	/**
	 * 根据编码查询课程
	 */
	@Override
	public List<?> findTestByCS_Code(String cs_code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.cs_id,c.cs_code,c.cs_name,c.cs_type,c.cs_remark,c.nj_id,g.nj_name from test c ");
		sql.append("left join grade g on g.nj_id = c.nj_id ");
		sql.append("where c.cs_code= :cs_code ");
		sql.append("order by c.cs_code desc");
		Map<String, Object> map = new HashMap<>();
		map.put("cs_code", cs_code);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	/**
	 * 根据年级ID查询课程
	 * @return
	 */
	@Override
	public List<?> findTestByNJ_Id(String nj_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.cs_id,c.cs_code,c.cs_name,c.cs_type,c.cs_remark,c.nj_id,g.nj_name from test c ");
		sql.append("left join grade g on g.nj_id = c.nj_id ");
		sql.append("where c.nj_id= :nj_id ");
		sql.append("order by c.cs_code desc");
		Map<String, Object> map = new HashMap<>();
		map.put("nj_id", nj_id);
		List<?> list = HibernateUtil.findByCondition(sql.toString(), map);
		return list;
	}

	@Override
	public String findTestMaxCode() {
		JSONObject successJson = new JSONObject();
		StringBuilder result = new StringBuilder("TS");
		String sql = "select max(cs_code) as code from test";
		Map<String, Object> map = (Map<String, Object>) HibernateUtil.findUnique(sql);
		Object code = map.get("code");
		if (code == null) {
			result.append("00000001");
		} else {
			String sub = code.toString().substring(2);
			String value = String.valueOf(Integer.valueOf(sub) + 1);
			switch (value.length()) {
			case 1:
				result.append("0000000");
				result.append(value);
				break;
			case 2:
				result.append("000000");
				result.append(value);
				break;
			case 3:
				result.append("00000");
				result.append(value);
				break;
			case 4:
				result.append("0000");
				result.append(value);
				break;
			case 5:
				result.append("000");
				result.append(value);
				break;
			case 6:
				result.append("00");
				result.append(value);
				break;
			case 7:
				result.append("0");
				result.append(value);
				break;
			case 8:
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
