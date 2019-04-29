package com.shiro.factory;

import java.util.LinkedHashMap;
/**
 * 权限配置
 * @author 李东升
 *
 */
public class FilterChainDefinitionMapBuilder {
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/project/module/login/**", "anon");
		//authc认证   user认证或者记住我
		map.put("/project/module/**", "user");
		return map;
	}
}
