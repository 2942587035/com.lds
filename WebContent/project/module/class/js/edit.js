/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#classTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	
	//类型
	var type = form.find('input[name=type]');
	//ID
	var bj_id = form.find('input[name=bj_id]');
	//编码
	var bj_code = form.find('input[name=bj_code]');
	//名称
	var bj_name = form.find('input[name=bj_name]');
	//年级
	var nj_id = form.find('select[name=nj_id]');
	
    if(!checkSaveBefore(type,bj_id,bj_code,bj_name,nj_id)){
    	return;
    }

	//年级
	var dataUpdate = new Object();
	dataUpdate.bj_id = dataRowNumber.bj_id;
	dataUpdate.bj_code = bj_code.val().trim();
	dataUpdate.bj_name = bj_name.val().trim();
	dataUpdate.nj_id = nj_id.val().trim();
	/*3.上传服务器*/
	editSaveSubmit(form, bj_code, bj_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#classTable").datagrid('collapseRow',rowNumber);
	$('#classTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param bj_code 编码
 * @param bj_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, bj_code, bj_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/class/edit.do",//url
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
					if (nullFlag == "bj_code") {
						bj_code.focus();
					} else if (nullFlag == "bj_name") {
						bj_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "bj_code") {
						bj_code.focus();
					} else if (repeat == "bj_name") {
						bj_name.focus();
					}
				}
				return;
			}
			//要修改的数据如果不在当前年级，删除当前行
			var rows = $('#classTable').datagrid('getRows');
			var currRow = rows[index];
			if(isModifiedGrade(currRow,dataUpdate)){
				$('#classTable').datagrid('deleteRow', index);
				return;
			}
			//修改数据
			$("#classTable").datagrid('updateRow', {
				index : parseInt(index),
				row : dataUpdate
			});
			//修改数据成功后重新赋值表单
			var formNew = $("#classTable").datagrid('getRowDetail', index).children("div form");
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
