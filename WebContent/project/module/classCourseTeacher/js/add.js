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
	//教师ID
	var js_id = $("#js_id");
	//课程ID
	var kc_id = $("#kc_id");
	//年级id
	var nj_id = $("#nj_id");
	//班级id
	var bj_id = $("#bj_id");
	
	if(!checkSaveBefore(type,null,js_id,kc_id,nj_id,bj_id)){
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
	$("#js_id").val('');
	$("#kc_id").val(1);
	$("#bj_id").val(1);
}

/**
 * 增加保存提交数据
 * @returns
 */
function addSaveSubmit() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/classCourseTeacher/add.do",//url
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
					if (nullFlag == "js_id") {
						$("#js_id").focus();
					} 
				} else if (repeat != null) {
					if (repeat == "js_id") {
						$("#js_id").focus();
					} 
				}
				return;
			}
			//表格插入一行数据
			$('#classCourseTeacherTable').datagrid('insertRow', {
				index : 0,
				row : success.data
			});
			//设置选中行
			$('#classCourseTeacherTable').datagrid('selectRow', 0);
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
