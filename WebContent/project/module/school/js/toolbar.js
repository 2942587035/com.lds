/**
 * 工具栏
 */

/*增加按钮点击事件*/
function add() {
	/*学校最多有一个*/
	var count = $("#schoolTable").datagrid("getRows").length;
	if (count >= 1) {
		alert('当前系统仅支持设置一个学校！');
		return;
	}
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
		url : $("#projectPath",parent.document).text()+"/school/getMaxCode.do",//url
		async : true,
		success : function(data, textStatus) {
			$("#xx_code").val(data.msg);
			/*获得焦点*/
			$("#xx_name").focus();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("获取数据失败！");
		}
	});
}