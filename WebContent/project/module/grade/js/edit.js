/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#gradeTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	//ID
	var nj_id = form.find('input[name=nj_id]');
	//编码
	var nj_code = form.find('input[name=nj_code]');
	//名称
	var nj_name = form.find('input[name=nj_name]');
	
	if(!checkSaveBefore(type,nj_id,nj_code,nj_name)){
		return;
	}

	var dataUpdate = new Object();
	dataUpdate.nj_id = dataRowNumber.nj_id;
	dataUpdate.nj_code = nj_code.val().trim();
	dataUpdate.nj_name = nj_name.val().trim();
	/*3.上传服务器*/
	editSaveSubmit(form, nj_code, nj_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#gradeTable").datagrid('collapseRow',rowNumber);
	$('#gradeTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param nj_code 编码
 * @param nj_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, nj_code, nj_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/grade/edit.do",//url
		async : true,
		data : form.serialize(),
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);

				var nullFlag = error.nullFlag;
				var repeat = error.repeat;
				if (nullFlag != null) {
					if (nullFlag == "nj_code") {
						nj_code.focus();
					} else if (nullFlag == "nj_name") {
						nj_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "nj_code") {
						nj_code.focus();
					} else if (repeat == "nj_name") {
						nj_name.focus();
					}
				}
				return;
			}
			//修改数据
			$("#gradeTable").datagrid('updateRow', {
				index : parseInt(index),
				row : dataUpdate
			});
			//修改数据成功后重新赋值表单
			var formNew = $("#gradeTable").datagrid('getRowDetail', index)
					.children("div form");
			formNew.form('load', getData(index, dataUpdate));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改数据失败！");
		}
	});
}
