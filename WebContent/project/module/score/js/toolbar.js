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
	/*编码文本框获得焦点*/
	$("#xx_code").focus();
}