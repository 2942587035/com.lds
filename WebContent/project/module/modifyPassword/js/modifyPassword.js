/**
 * 修改密码
 */

$(function() {
	/*原密码获得焦点*/
	$("#oldPassword").focus();
	/*确定按钮点击事件*/
	$("#ok").click(function() {
		/*原密码不能为空*/
		var oldPassword = $("#oldPassword");
		var oldPasswordV = oldPassword.val().trim();
		if (oldPasswordV == "") {
			alert("请输入原密码！");
			oldPassword.focus();
			return;
		}
		/*新密码不能为空*/
		var newPassword = $("#newPassword");
		var newPasswordV = newPassword.val().trim();
		if (newPasswordV == "") {
			alert("请输入新密码！");
			newPassword.focus();
			return;
		}
		/*确认密码不能为空*/
		var okPassword = $("#okPassword");
		var okPasswordV = okPassword.val().trim();
		if (okPasswordV == "") {
			alert("请输入确认密码！");
			okPassword.focus();
			return;
		}
		/*密码只能输入英文字母或数字*/
		var pattern = new RegExp(/^[0-9a-zA-Z]+$/);
		if (!pattern.test(oldPasswordV)) {
			alert("原密码只能输入英文字母或数字！");
			oldPassword.focus();
			return;
		}
		if (!pattern.test(newPasswordV)) {
			alert("新密码只能输入英文字母或数字！");
			newPassword.focus();
			return;
		}
		if (!pattern.test(okPasswordV)) {
			alert("确认密码只能输入英文字母或数字！");
			okPassword.focus();
			return;
		}
		/*确认密码与新密码不一致*/
		if (okPasswordV != newPasswordV) {
			alert("确认密码与新密码不一致，请重新输入！");
			okPassword.focus();
			return;
		}
		/*原密码输入错误*/
		//获得原密码（加密）
		var yh_password = parent.document.getElementById("yh_password").innerText;
		if (yh_password == null || yh_password == "") {
			alert("当前用户不存在，请退出登录！");
			return;
		}
		//获得输入的原密码（加密）
		/*oldPasswordV*/
		var yh_passwordOld = hex_md5(oldPasswordV);
		if (yh_passwordOld != yh_password) {
			alert("原密码输入错误，请重新输入！");
			oldPassword.focus();
			return;
		}
		
		/*开始上传服务器*/
		var yh_id = parent.document.getElementById("yh_id").innerText;
		if (yh_id == null || yh_id == "") {
			alert("当前用户不存在，请退出登录！");
			return;
		}

		modifyPassword(yh_id, hex_md5(newPasswordV));
	});
	/*清空按钮点击事件*/
	$("#cancel").click(function() {
		clearTab();
	});
});

/*清空当前选项卡*/
function clearTab() {
	$("#oldPassword").val('');
	$("#newPassword").val('');
	$("#okPassword").val('');
}

/* 修改密码 */
function modifyPassword(yh_id, newPasswordV) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/modifyPassword/edit.do",//url
		async : true,
		data : {
			"yh_id" : yh_id,
			"newPassword" : newPasswordV
		},
		success : function(data, textStatus) {
			//获得返回数据
			/* alert(data.msg); */
			//退出登录
			if (data.flag == 0) {
				window.parent.location.href = $("#projectPath",parent.document).text()+"/project/module/login/jsp/login.jsp?cookie=delete";
				return;
			}
			if (data.msg == '密码为空！') {
				$("#newPassword").focus();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("密码修改失败！");
		}
	});
}