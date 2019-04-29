package com.business.service.inter;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 提供成绩操作服务
 * @author lds
 *
 */
public interface ScoreService {
	public List<?> findScore();
	public JSONObject insertScore(String xx_code, String xx_name, String xx_type, String xx_remark);
	public JSONObject updateScore(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark);
	public void deleteScore(String xx_id);
}
