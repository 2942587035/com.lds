/**
 * 增加
 */

/**
 * 保存
 * @returns
 */
function addSave() {
	/*1.判空*/
	//业务类型
	var type = $("#type");
	//编码
	var xx_code = $("#xx_code");
	//名称
	var xx_name = $("#xx_name");
	
	if(!checkSaveBefore(type,null,xx_code,xx_name)){
		return;
	}
	
	/*3.上传服务器*/
	addSaveSubmit();
}

/**
 * 取消：隐藏面板,清空面板数据
 */
function addCancel() {
	//隐藏面板
	$("#addPanel").css({
		"display" : "none"
	});
	//清空面板数据
	clearPanel();
}

/**
 * 清空面板数据
 */
function clearPanel() {
	$("#xx_code").val('');
	$("#xx_name").val('');
	$("#xx_type").val(1);
	$("#xx_remark").val('');
}

/**
 * 增加保存提交数据
 * @returns
 */
function addSaveSubmit() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/school/add.do",//url
		async : true,
		data : $('#addForm').serialize(),
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);

				var nullFlag = error.nullFlag;
				var repeat = error.repeat;
				if (nullFlag != null) {
					if (nullFlag == "xx_code") {
						$("#xx_code").focus();
					} else if (nullFlag == "xx_name") {
						$("#xx_name").focus();
					}
				} else if (repeat != null) {
					if (repeat == "xx_code") {
						$("#xx_code").focus();
					} else if (repeat == "xx_name") {
						$("#xx_name").focus();
					}
				}
				return;
			}
			//收缩已展开的行
			/*$("#schoolTable").datagrid({
				detailView : false
			});*/
			//表格插入一行数据
			$('#schoolTable').datagrid('insertRow', {
				index : 0,
				row : success.data
			});
			//设置选中行
			$('#schoolTable').datagrid('selectRow', 0);
			//隐藏增加面板
			$("#addPanel").css({
				"display" : "none"
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("增加数据失败！");
		}
	});
}
