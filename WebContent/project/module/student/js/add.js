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
	var xs_code = $("#xs_code");
	//名称
	var xs_name = $("#xs_name");
	//手机
	var xs_phone = $("#xs_phone");
	//邮箱
	var xs_email = $("#xs_email");
	//年级id
	var nj_id = $("#nj_id");
	//班级id
	var bj_id = $("#bj_id");
	
	if(!checkSaveBefore(type,null,xs_code,xs_name,xs_phone,xs_email,nj_id,bj_id)){
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
	$("#xs_code").val('');
	$("#xs_name").val('');
	$("#xs_sex").val(1);
	$("#xs_phone").val('');
	$("#xs_email").val('');
}

/**
 * 增加保存提交数据
 * @returns
 */
function addSaveSubmit() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/student/add.do",//url
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
					if (nullFlag == "xs_code") {
						$("#xs_code").focus();
					} else if (nullFlag == "xs_name") {
						$("#xs_name").focus();
					}
				} else if (repeat != null) {
					if (repeat == "xs_code") {
						$("#xs_code").focus();
					} else if (repeat == "xs_name") {
						$("#xs_name").focus();
					}
				}
				return;
			}
			//表格插入一行数据
			$('#studentTable').datagrid('insertRow', {
				index : 0,
				row : success.data
			});
			//设置选中行
			$('#studentTable').datagrid('selectRow', 0);
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
