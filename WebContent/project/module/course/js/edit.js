/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#courseTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	//ID
	var kc_id = form.find('input[name=kc_id]');
	//编码
	var kc_code = form.find('input[name=kc_code]');
	//名称
	var kc_name = form.find('input[name=kc_name]');
	//年级
	var nj_id = form.find('select[name=nj_id]');
	
    if(!checkSaveBefore(type,kc_id,kc_code,kc_name,nj_id)){
    	return;
    }

	//年级
	var dataUpdate = new Object();
	dataUpdate.kc_id = dataRowNumber.kc_id;
	dataUpdate.kc_code = kc_code.val().trim();
	dataUpdate.kc_name = kc_name.val().trim();
	dataUpdate.nj_id = nj_id.val().trim();
	/*3.上传服务器*/
	editSaveSubmit(form, kc_code, kc_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#courseTable").datagrid('collapseRow',rowNumber);
	$('#courseTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param kc_code 编码
 * @param kc_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, kc_code, kc_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/course/edit.do",//url
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
					if (nullFlag == "kc_code") {
						kc_code.focus();
					} else if (nullFlag == "kc_name") {
						kc_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "kc_code") {
						kc_code.focus();
					} else if (repeat == "kc_name") {
						kc_name.focus();
					}
				}
				return;
			}
			//要修改的数据如果不在当前年级，删除当前行
			var rows = $('#courseTable').datagrid('getRows');
			var currRow = rows[index];
			if(isModifiedGrade(currRow,dataUpdate)){
				$('#courseTable').datagrid('deleteRow', index);
				return;
			}
			//修改数据
			$("#courseTable").datagrid('updateRow', {
				index : parseInt(index),
				row : dataUpdate
			});
			//修改数据成功后重新赋值表单
			var formNew = $("#courseTable").datagrid('getRowDetail', index).children("div form");
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
