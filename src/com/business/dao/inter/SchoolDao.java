package com.business.dao.inter;

import java.util.List;

/**
 * 提供用户操作服务
 * @author lds
 *
 */
public interface SchoolDao {
	public List<?> findSchool();
	public int findCountSchoolByXX_Code(String xx_code);
	public int findCountSchoolByXX_Name(String xx_name);
	public int findCountSchoolByXX_CodeAndId(String xx_code, String xx_id);
	public int findCountSchoolByXX_NameAndId(String xx_name, String xx_id);
	public void insertSchool(String xx_code, String xx_name, String xx_type, String xx_remark);
	public void updateSchool(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark);
	public void deleteSchool(String xx_id);
	public List<?> findSchoolByXX_Code(String xx_code);
	/**
	 * 取号函数：获取最大编码
	 */
	String findSchoolMaxCode();
}
