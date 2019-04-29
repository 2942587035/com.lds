<%@page import="utils.util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html>
<%
    String projectPath = request.getContextPath();
    String yh_id = request.getParameter("yh_id");
    String yh_code = request.getParameter("yh_code");
    String yh_name = request.getParameter("yh_name");
    String yh_password = request.getParameter("yh_password");
    String yh_type = request.getParameter("yh_type");
    if(!util.isBlank_String(yh_id) 
    && !util.isBlank_String(yh_code) 
    && !util.isBlank_String(yh_name) 
    && !util.isBlank_String(yh_password)
    && !util.isBlank_String(yh_type)) {
    	int maxAge = 60*60*24;
    	Cookie yh_idCookie = new Cookie("yh_id", yh_id);
    	Cookie yh_codeCookie = new Cookie("yh_code", yh_code);
        Cookie yh_nameCookie = new Cookie("yh_name", yh_name);
        Cookie yh_passwordCookie = new Cookie("yh_password", yh_password);
        Cookie yh_typeCookie = new Cookie("yh_type", yh_type);
        
        yh_idCookie.setPath(projectPath);
        yh_codeCookie.setPath(projectPath);
        yh_nameCookie.setPath(projectPath);
        yh_passwordCookie.setPath(projectPath);
        yh_typeCookie.setPath(projectPath);
        
        yh_idCookie.setMaxAge(maxAge);
        yh_codeCookie.setMaxAge(maxAge);
        yh_nameCookie.setMaxAge(maxAge);
        yh_passwordCookie.setMaxAge(maxAge);
        yh_typeCookie.setMaxAge(maxAge);
        
        response.addCookie(yh_idCookie);
        response.addCookie(yh_codeCookie);
        response.addCookie(yh_nameCookie);
        response.addCookie(yh_passwordCookie);
        response.addCookie(yh_typeCookie);
    }
%>
<html>
<head>
<meta charset="UTF-8">
<title>学生成绩管理系统</title>
<script src="<%=request.getContextPath()%>/project/module/main/js/main.js"></script>
</head>
<body class="easyui-layout" scroll="no">
    <div region="north" border="false" style="height:30px;">
        <table style="width:100%;">
            <tr>
                <td id="xx_id" style="display:none"></td>
                <td id="xx_name" style="padding-left:10px;width:150px;"></td>
				<td style="text-align:right;padding-right:10px">
				    <span id="yh_id" style="display: none"><%=yh_id%></span> 
				    <span id="yh_code" style="display: none"><%=yh_code%></span>
				    <span id="yh_password" style="display: none"><%=yh_password%></span> 
				    <span id="yh_name"><%=yh_name%></span> 
				    <span id="yh_type" style="display: none"><%=yh_type%></span> 
				    <span id="projectPath" style="display: none"><%=projectPath%></span> 
				    <shiro:hasRole name="1"> 【管理员】</shiro:hasRole>
				    <shiro:hasRole name="2"> 【学生】</shiro:hasRole>
				    <shiro:hasRole name="3"> 【教师】</shiro:hasRole>
				    <shiro:hasPermission name="admin:insert">有增加权限</shiro:hasPermission>
				    <a href="#" id="exitLogin">退出登录</a>
				</td>
			</tr>
        </table>
		<div style="text-align: right;float: right;padding-right:10px">
		    
		</div>
	</div>
	<div region="west" border="false" title="导航菜单" style="width: 150px;">
		<div class="easyui-panel" fit="true">
			<div>
				<ul class="easyui-tree" id="menuTree">
				</ul>
			</div>
		</div>
	</div>
	<div region="center" border="false">
		<div id="contentPanel" class="easyui-tabs" fit="true"></div>
	</div>
</body>
</html>