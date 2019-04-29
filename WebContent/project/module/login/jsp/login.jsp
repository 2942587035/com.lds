<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<%
    String projectPath = request.getContextPath();
    /*如有可用cookie，则直接进入系统界面main.jsp*/
    Map<String, Cookie> map = new HashMap<>();
    Cookie[] cookies = request.getCookies();
    if(cookies != null) {
    	for(int i = 0; i < cookies.length; i++) {
    		Cookie cookie = cookies[i];
    		if("yh_id".equals(cookie.getName()) 
    		|| "yh_code".equals(cookie.getName())
    		|| "yh_name".equals(cookie.getName()) 
    		|| "yh_password".equals(cookie.getName())
    		|| "yh_type".equals(cookie.getName())) {
    			map.put(cookie.getName(), cookie);
    		}
    	}
    }
    
    if(map.keySet().size() == 5) {
        String isDelete = request.getParameter("cookie");
    	if("delete".equals(isDelete)) {
    		for(String key:map.keySet()) {
    			Cookie cookie = map.get(key);
    			cookie.setPath(request.getContextPath());
    			cookie.setMaxAge(0);
        		response.addCookie(cookie);
    		}
    		out.print("<script>alert('当前用户已注销，请重新登录！'); </script>");
    	} else {
    		String yh_id = map.get("yh_id").getValue();
    		String yh_name = java.net.URLEncoder.encode(map.get("yh_name").getValue(), "UTF-8");
    		String yh_code = map.get("yh_code").getValue();
    		String yh_password = map.get("yh_password").getValue();
    		String yh_type = map.get("yh_type").getValue();
    		response.sendRedirect(request.getContextPath()+"/project/module/main/jsp/main.jsp?yh_id="+yh_id+"&yh_name="+yh_name+"&yh_code="+yh_code+"&yh_password="+yh_password+"&yh_type="+yh_type);
    		return;
    	}
    }
%>
<html>
<head>
<meta charset="UTF-8">
<title>学生成绩管理系统</title>
<script src="<%=request.getContextPath()%>/project/module/login/js/login.js"></script>
</head>
<body class="easyui-layout" scroll="no">
    <div><span id="projectPath" style="display: none"><%=projectPath %></span></div>
    <div region="center" border="true">
		<div id="loginPanel" title="系统登录"  class="easyui-panel" data-options="style:{position:'absolute'}">
			<form id="form" action="/LoginServlet" method="post">
				<table id="table" cellspacing="10px">
					<tr>
						<td class="label" style="padding-left:20px">编码：</td>
						<td class="text" colspan="2" style="padding-right:20px">
						<input type="text" class="disableInputMethod" id="yh_code" name="yh_code" maxlength="10" required="true"
						onkeypress="transferFocus(event,this)"/>
						</td>
					</tr>
					<tr>
						<td class="label" style="padding-left:20px">密码：</td>
						<td class="text" colspan="2" style="padding-right:20px">
						<input type="password" class="disableInputMethod" id="yh_password" name="yh_password" maxlength="10" required="true"
						onkeypress="transferFocus(event,this)"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
						    <a href="#" id="ok" class="easyui-linkbutton" iconCls="icon-ok">登录</a>
						</td>
						<td>
						    <a href="#" id="exit" class="easyui-linkbutton" iconCls="icon-cancel">退出</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>