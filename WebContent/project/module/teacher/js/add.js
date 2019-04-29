/**
 * 增加
 */

/**
 * 保存
 * @returns
 */
function addSave() {
	//类型
	var type = $("#type");
	//编码
	var js_code = $("#js_code");
	//名称
	var js_name = $("#js_name");
	//手机
	var js_phone = $("#js_phone");
	//邮箱
	var js_email = $("#js_email");
	
	if(!checkSaveBefore(type,null,js_code,js_name,js_phone,js_email)){
		return;
	}
	
	/*上传服务器*/
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
	$("#js_code").val('');
	$("#js_name").val('');
	$("#js_sex").val(1);
	$("#js_phone").val('');
	$("#js_email").val('');
}

/**
 * 增加保存提交数据
 * @returns
 */
function addSaveSubmit() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/teacher/add.do",//url
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
					if (nullFlag == "js_code") {
						$("#js_code").focus();
					} else if (nullFlag == "js_name") {
						$("#js_name").focus();
					}
				} else if (repeat != null) {
					if (repeat == "js_code") {
						$("#js_code").focus();
					} else if (repeat == "js_name") {
						$("#js_name").focus();
					}
				}
				return;
			}
			//表格插入一行数据
			$('#teacherTable').datagrid('insertRow', {
				index : 0,
				row : success.data
			});
			//设置选中行
			$('#teacherTable').datagrid('selectRow', 0);
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
