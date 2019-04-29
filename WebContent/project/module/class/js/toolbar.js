/**
 * 工具栏
 */

/*增加按钮点击事件*/
function add() {
	/*显示增加面板*/
	$("#addPanel").css({
		"display" : "inline"
	});
	/*获取编码*/
	getCode();
}

function getCode() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/class/getMaxCode.do",//url
		async : true,
		success : function(data, textStatus) {
			$("#bj_code").val(data.msg);
			/*获得焦点*/
			$("#bj_name").focus();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("获取数据失败！");
		}
	});
}