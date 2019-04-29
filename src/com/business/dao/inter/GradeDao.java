package com.business.dao.inter;

import java.util.List;

/**
 * 提供年级操作服务
 * @author lds
 *
 */
public interface GradeDao {
	public List<?> findGrade();
	public int findCountGradeByNJ_Code(String nj_code);
	public int findCountGradeByNJ_Name(String nj_name);
	public int findCountGradeByNJ_CodeAndId(String nj_code, String nj_id);
	public int findCountGradeByNJ_NameAndId(String nj_name, String nj_id);
	public void insertGrade(String nj_code, String nj_name);
	public void updateGrade(String nj_id, String nj_code, String nj_name);
	public void deleteGrade(String nj_id);
	public List<?> findGradeByNJ_Code(String nj_code);
	public List<?> findGrade_NameAndId();
	/**
	 * 取号函数：获取最大编码
	 */
	String findGradeMaxCode();
}
