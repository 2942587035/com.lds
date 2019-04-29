package com.business.dao.inter;

import java.util.List;

/**
 * 提供分数操作服务
 * @author lds
 *
 */
public interface ScoreDao {
	public List<?> findScore();
	public int findCountScoreByXX_Code(String xx_code);
	public int findCountScoreByXX_Name(String xx_name);
	public int findCountScoreByXX_CodeAndId(String xx_code, String xx_id);
	public int findCountScoreByXX_NameAndId(String xx_name, String xx_id);
	public void insertScore(String xx_code, String xx_name, String xx_type, String xx_remark);
	public void updateScore(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark);
	public void deleteScore(String xx_id);
	public List<?> findScoreByXX_Code(String xx_code);
}
