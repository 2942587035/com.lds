/**
 * 登陆
 */

/* 关闭窗口 */
function closeMainPage() {
	var flag = window.confirm("您正在试图关闭程序，请确认是否关闭？");
	if (flag) {
		/* 确认关闭 */
		window.opener = null;
		window.open('', '_self');
		window.close();
	}
}

/*网页初始化完毕*/
$(document).ready(function () {
	/*面板居中显示*/
	setCenter();
	/*界面大小调整仍居中*/
	window.onresize = function ()
    {
        setTimeout(setCenter(), 100);
    };

	/*用户编码获得焦点*/
	$("#yh_code").focus();
    /*1.登录按钮点击事件*/
    $("#ok").click(function (evt) {
        /*用户编码不能为空*/
        var yh_code = $("#yh_code");
        var yh_codeV = yh_code.val().trim();
        if (yh_codeV == "") {
            alert("请输入用户编码！");
            yh_code.focus();
            return;
        }
        /*用户编码不能输入中文*/
        var patternC= new RegExp(/^[^\u4e00-\u9fa5]+$/);  
        if(!patternC.test(yh_codeV)) {
        	alert("用户编码不能输入中文！");
        	yh_code.focus();
            return;
        } 
        
        /*密码不能为空*/
        var yh_password = $("#yh_password");
        var yh_passwordV = yh_password.val().trim();
        if (yh_passwordV == "") {
            alert("请输入密码！");
            yh_password.focus();
            return;
        }
        /*密码只能输入英文字母或数字*/
        var pattern= new RegExp(/^[0-9a-zA-Z]+$/);  
        if(!pattern.test(yh_passwordV)) {
        	alert("密码只能输入英文字母或数字！");
            yh_password.focus();
            return;
        } 

        /*将用户编码和密码传到服务器*/
        login();
    });

    /*2.退出按钮点击事件*/
    $("#exit").click(function (evt) {
        /*退出网页*/
        closeMainPage();
    });

});

/*设置面板居中*/
function setCenter(){
	var windowCurrent = $(window); 
	var loginPanel = $("#loginPanel");
	
	var top = (windowCurrent.height()-loginPanel.height())/2;
	var left = (windowCurrent.width()-loginPanel.width())/2;
	
	if(top<0) top=0;
	if(left<0) left=0;
	
	loginPanel.panel('move',{
		left:left,
	    top:top
	});  
}

/* 登录 */
function login() {
    $.ajax({
		type : "POST",//方法类型
		dataType: "json",//预期服务器返回的数据类型 
		url : $("#projectPath").text()+"/login/login.do",//url
		async: true, 
		data : $('#form').serialize(),
		success : function(data, textStatus) {
			//获得返回数据
			var success = data.success;
			var error = data.error;
			
			if(error != null) {//用户编码密码错误
				var message = error.message;
				var flag = error.flag;
				alert(message);
				if(flag == 0) {//用户编码错误,清空用户编码，获得焦点
					var yh_code = $("#yh_code");
					yh_code.val('');
					yh_code.focus();
				} else {//密码错误,清空密码，获得焦点
					var yh_password = $("#yh_password");
					yh_password.val('');
					yh_password.focus();
				}
			} else {//用户密码正确,可以登录系统
				var message = success.message;
				var yh_id = success.yh_id;
				var yh_code = success.yh_code;
				var yh_name = encodeURI(success.yh_name);
				var yh_password = success.yh_password;
				var yh_type = success.yh_type;
				window.location.href=$("#projectPath").text()+"/project/module/main/jsp/main.jsp?yh_id="+yh_id+"&yh_name="+yh_name+"&yh_code="+yh_code+"&yh_password="+yh_password+"&yh_type="+yh_type;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("登陆失败！");
		}
	});
}

/**
 * 焦点跳转
 * @param thisInput
 * @returns
 */
function transferFocus(event,currComponent){
	if(event.keyCode!=13) {return;}
	var parent = $(currComponent).parent().parent().parent();
    var components = $('input,select,a',parent);
    for(var i = 0;i<components.length;i++){
      // 如果是最后一个，则焦点回到第一个
      if(i==(components.length-1)){
        components[0].focus();
        break;
      }else if(currComponent == components[i]){
        components[i+1].focus();
        break;
      }
    }
} 