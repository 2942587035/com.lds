<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改密码</title>
<script src="<%=request.getContextPath()%>/project/module/modifyPassword/js/modifyPassword.js"></script>
<script src="<%=request.getContextPath()%>/plugins/md5/md5.js"></script>
</head>
<body>
    <div id="modifyPassword">
        <form id="modifyPasswordForm" action="" method="get">
            <table>
				<tr>
					<td class="label">原密码：</td>
					<td class="text">
					<input type="password" class="disableInputMethod" id="oldPassword" name="oldPassword" maxlength="10" required="true" 
					onkeypress="parent.transferFocus(event,this)"/>
					</td>
				</tr>
				<tr>
					<td class="label">新密码：</td>
					<td class="text">
					<input type="password" class="disableInputMethod" id="newPassword" name="newPassword" maxlength="10" required="true" 
					onkeypress="parent.transferFocus(event,this)"/>
					</td>
				</tr>
				<tr>
					<td class="label">确认密码：</td>
					<td class="text">
					<input type="password" class="disableInputMethod" id="okPassword" name="okPassword" maxlength="10" required="true" 
					onkeypress="parent.transferFocus(event,this)"/>
					</td>
				</tr>
				<tr>
				    <td></td>
				    <td style="text-align:center">
				    <a id="ok" href="#" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
				    <a id="cancel" href="#" class="easyui-linkbutton" iconCls="icon-cancel">清空</a>
				    </td>
				</tr>
			</table>
        </form>
    </div>
</body>
</html>