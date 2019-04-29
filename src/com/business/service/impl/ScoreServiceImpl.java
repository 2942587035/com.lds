package com.business.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.business.dao.inter.ScoreDao;
import com.business.dao.inter.ScoreDao;
import com.business.service.inter.ScoreService;

import net.sf.json.JSONObject;

/**
 * 提供分数操作服务
 * @author lds
 *
 */
@Transactional
public class ScoreServiceImpl implements ScoreService {
	private ScoreDao scoreDao;

	public void setScoreDao(ScoreDao scoreDao) {
		this.scoreDao = scoreDao;
	}
	
	@Override
	public List<?> findScore() {
		return scoreDao.findScore();
	}

	@Override
	public JSONObject insertScore(String xx_code, String xx_name, String xx_type, String xx_remark) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = scoreDao.findCountScoreByXX_Code(xx_code);
		if (count1 > 0) {
			errorJson.put("repeat", "xx_code");
			errorJson.put("msg", "编码重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = scoreDao.findCountScoreByXX_Name(xx_name);
		if (count2 > 0) {
			errorJson.put("repeat", "xx_name");
			errorJson.put("msg", "名称重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		scoreDao.insertScore(xx_code, xx_name, xx_type, xx_remark);
		List<?> list = scoreDao.findScoreByXX_Code(xx_code);
		if (list.size() != 1) {
			errorJson.put("msg", "增加数据失败！");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		successJson.put("data", list.get(0));
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public JSONObject updateScore(String xx_id, String xx_code, String xx_name, String xx_type, String xx_remark) {
		//Json容器
		JSONObject mainJson = new JSONObject();
		//成功数据Json容器
		JSONObject successJson = new JSONObject();
		//失败数据Json容器
		JSONObject errorJson = new JSONObject();
		
		int count1 = scoreDao.findCountScoreByXX_CodeAndId(xx_code, xx_id);
		if (count1 > 0) {
			errorJson.put("repeat", "xx_code");
			errorJson.put("msg", "编码重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		int count2 = scoreDao.findCountScoreByXX_NameAndId(xx_name, xx_id);
		if (count2 > 0) {
			errorJson.put("repeat", "xx_name");
			errorJson.put("msg", "名称重复");
			mainJson.put("error", errorJson);
			
			return mainJson;
		}
		
		scoreDao.updateScore(xx_id, xx_code, xx_name, xx_type, xx_remark);
		
		successJson.put("msg", "修改数据成功！");
		mainJson.put("success", successJson);
		return mainJson;
	}

	@Override
	public void deleteScore(String xx_id) {
		scoreDao.deleteScore(xx_id);		
	}
}
