package com.business.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.business.dao.inter.MenuPowerDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.HibernateUtil;

public class MenuPowerDaoImpl implements MenuPowerDao {

	@Override
	public JSONArray findMenuPowerByGN_Type(String gn_type) {
		JSONArray resultArray = new JSONArray();
		List<?> menuPowerList = findMenuPowerByGN_TypeAndGN_Pid(gn_type, null);
		for (int i = 0; i < menuPowerList.size(); i++) {
			Map<String, Object> menuPowerMap = (Map<String, Object>) menuPowerList.get(i);
			JSONObject menuPowerObject = new JSONObject();
			//²Ëµ¥ID
			String gn_id = String.valueOf(menuPowerMap.get("gn_id"));
			menuPowerObject.put("id", gn_id);
			menuPowerObject.put("text", menuPowerMap.get("gn_name"));
			menuPowerObject.put("gn_type", menuPowerMap.get("gn_type"));
			menuPowerObject.put("gn_pid", menuPowerMap.get("gn_pid"));
			menuPowerObject.put("gn_code", menuPowerMap.get("gn_code"));
			menuPowerObject.put("children", findMenuPowerByGN_TypeAndGN_Pid1(gn_type, gn_id));
			resultArray.add(menuPowerObject);
		}
		return resultArray;
	}

	public List<?> findMenuPowerByGN_TypeAndGN_Pid(String gn_type, String gn_pid) {
		Map<String, Object> map = new HashMap<>();
		String sql = null;
		if (gn_pid == null) {
			sql = "select gn_id,gn_code,gn_name,gn_type,gn_pid from menupower where gn_type=:gn_type and gn_pid is null";
		} else {
			sql = "select gn_id,gn_code,gn_name,gn_type,gn_pid from menupower where gn_type=:gn_type and gn_pid=:gn_pid";
			map.put("gn_pid", gn_pid);
		}
		
		map.put("gn_type", gn_type);
		List<?> list = HibernateUtil.findByCondition(sql, map);
		
		return list;
	}
	
	public JSONArray findMenuPowerByGN_TypeAndGN_Pid1(String gn_type, String gn_pid) {
		JSONArray menuPowerArray = new JSONArray();
		List<?> menuPowerList = findMenuPowerByGN_TypeAndGN_Pid(gn_type, gn_pid);
		for (int i = 0; i < menuPowerList.size(); i++) {
			Map<String, Object> menuPowerMap = (Map<String, Object>) menuPowerList.get(i);
			JSONObject menuPowerObject = new JSONObject();
			//²Ëµ¥ID
			String gn_id = String.valueOf(menuPowerMap.get("gn_id"));
			menuPowerObject.put("id", gn_id);
			menuPowerObject.put("text", menuPowerMap.get("gn_name"));
			menuPowerObject.put("gn_type", menuPowerMap.get("gn_type"));
			menuPowerObject.put("gn_pid", menuPowerMap.get("gn_pid"));
			menuPowerObject.put("gn_code", menuPowerMap.get("gn_code"));
			
			menuPowerArray.add(menuPowerObject);
		}
		return menuPowerArray;
	}
    
}
