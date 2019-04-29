package com.business.dao.inter;

import java.util.List;

/**
 * 提供班级操作服务
 * @author lds
 *
 */
public interface ClassDao {
	List<?> findClass();
	int findCountClassByBJ_Code(String bj_code);
	int findCountClassByBJ_NameAndNJ_Id(String bj_name, String nj_id);
	int findCountClassByBJ_CodeAndId(String bj_code, String bj_id);
	int findCountClassBJ_NameAndNJ_IdAndId(String bj_name, String nj_id, String bj_id);
	void insertClass(String bj_code, String bj_name, String nj_id);
	void updateClass(String bj_id, String bj_code, String bj_name, String nj_id);
	void deleteClass(String bj_id);
	List<?> findClassByBJ_Code(String bj_code);
	List<?> findClassByNJ_Id(String nj_id);
	/**
	 * 取号函数：获取最大编码
	 */
	String findClassMaxCode();
}
