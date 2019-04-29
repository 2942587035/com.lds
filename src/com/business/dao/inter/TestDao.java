package com.business.dao.inter;

import java.util.List;

/**
 * 提供测试操作服务
 * @author lds
 *
 */
public interface TestDao {
	public List<?> findTest();
	public int findCountTestByCS_Code(String cs_code);
	public int findCountTestByCS_NameAndNJ_Id(String cs_name, String nj_id);
	public int findCountTestByCS_CodeAndId(String cs_code, String cs_id);
	public int findCountTestCS_NameAndNJ_IdAndId(String cs_name, String nj_id, String cs_id);
	public void insertTest(String cs_code, String cs_name, String cs_type, String cs_remark, String nj_id);
	public void updateTest(String cs_id, String cs_code, String cs_name, String cs_type, String cs_remark, String nj_id);
	public void deleteTest(String cs_id);
	public List<?> findTestByCS_Code(String cs_code);
	public List<?> findTestByNJ_Id(String nj_id);
	/**
	 * 取号函数：获取最大编码
	 */
	String findTestMaxCode();
}
