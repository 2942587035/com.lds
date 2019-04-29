/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#testTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	//ID
	var cs_id = form.find('input[name=cs_id]');
	//编码
	var cs_code = form.find('input[name=cs_code]');
	//名称
	var cs_name = form.find('input[name=cs_name]');
	//类型
	var cs_type = form.find('select[name=cs_type]');
	//年级
	var nj_id = form.find('select[name=nj_id]');
	
	if(!checkSaveBefore(type,cs_id,cs_code,cs_name,cs_type,nj_id)){
		return;
	}
	
	//备注
	var cs_remark = form.find('input[name=cs_remark]');
	
	//年级
	var nj_idV = form.find('select[name=nj_id]').val().trim();
	var dataUpdate = new Object();
	dataUpdate.cs_id = dataRowNumber.cs_id;
	dataUpdate.cs_code = cs_code.val().trim();
	dataUpdate.cs_name = cs_name.val().trim();
	dataUpdate.cs_type = cs_type.val().trim();
	dataUpdate.cs_remark = cs_remark.val().trim();
	dataUpdate.nj_id = nj_idV;
	/*3.上传服务器*/
	editSaveSubmit(form, cs_code, cs_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#testTable").datagrid('collapseRow',rowNumber);
	$('#testTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param cs_code 编码
 * @param cs_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, cs_code, cs_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/test/edit.do",//url
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
					if (nullFlag == "cs_code") {
						cs_code.focus();
					} else if (nullFlag == "cs_name") {
						cs_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "cs_code") {
						cs_code.focus();
					} else if (repeat == "cs_name") {
						cs_name.focus();
					}
				}
				return;
			}
			//要修改的数据如果不在当前年级，删除当前行
			var rows = $('#testTable').datagrid('getRows');
			var currRow = rows[index];
			if(isModifiedGrade(currRow,dataUpdate)){
				$('#testTable').datagrid('deleteRow', index);
				return;
			}
			//修改数据
			$("#testTable").datagrid('updateRow', {
				index : parseInt(index),
				row : dataUpdate
			});
			//修改数据成功后重新赋值表单
			var formNew = $("#testTable").datagrid('getRowDetail', index).children("div form");
			formNew.form('load', getData(index, dataUpdate));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改数据失败！");
		}
	});
	
	/**
	 * 是否修改了年级
	 * @returns
	 */
	function isModifiedGrade(currRow, updateRow) {
		if(currRow.nj_id == updateRow.nj_id) {
			return false;
		} else {
			return true;
		}
	}
}
