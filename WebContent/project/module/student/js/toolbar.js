/**
 * 工具栏
 */

/*增加按钮点击事件*/
function add() {
	/*显示增加面板*/
	$("#addPanel").css({
		"display" : "inline"
	});
	/*获得焦点*/
	getCode();
}

function getCode() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/student/getMaxCode.do",//url
		async : true,
		success : function(data, textStatus) {
			$("#xs_code").val(data.msg);
			/*获得焦点*/
			$("#xs_name").focus();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("获取数据失败！");
		}
	});
}