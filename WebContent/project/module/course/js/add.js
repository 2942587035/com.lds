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
	var kc_code = $("#kc_code");
	//名称
	var kc_name = $("#kc_name");
	//年级
	var nj_id = $("#nj_id");
	
    if(!checkSaveBefore(type,null,kc_code,kc_name,nj_id)){
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
	$("#kc_code").val('');
	$("#kc_name").val('');
}

/**
 * 增加保存提交数据
 * @returns
 */
function addSaveSubmit() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/course/add.do",//url
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
					if (nullFlag == "kc_code") {
						$("#kc_code").focus();
					} else if (nullFlag == "kc_name") {
						$("#kc_name").focus();
					}
				} else if (repeat != null) {
					if (repeat == "kc_code") {
						$("#kc_code").focus();
					} else if (repeat == "kc_name") {
						$("#kc_name").focus();
					}
				}
				return;
			}
			//收缩已展开的行
			/*$("#courseTable").datagrid({
				detailView : false
			});*/
			//表格插入一行数据
			$('#courseTable').datagrid('insertRow', {
				index : 0,
				row : success.data
			});
			//设置选中行
			$('#courseTable').datagrid('selectRow', 0);
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
