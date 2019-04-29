/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#studentTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	//ID
	var xs_id = form.find('input[name=xs_id]');
	//编码
	var xs_code = form.find('input[name=xs_code]');
	//名称
	var xs_name = form.find('input[name=xs_name]');
	//性别
	var xs_sex = form.find('select[name=xs_sex]');
	//手机
	var xs_phone = form.find('input[name=xs_phone]');
	//email
	var xs_email = form.find('input[name=xs_email]');
	//年级id
	var nj_id = form.find('select[name=nj_id]');
	//班级id
	var bj_id = form.find('select[name=bj_id]');
	
	/*校验*/
	if(!checkSaveBefore(type,xs_id,xs_code,xs_name,xs_phone,xs_email,nj_id,bj_id)){
		return;
	}
	
	var dataUpdate = new Object();
	dataUpdate.xs_id = dataRowNumber.xs_id;
	dataUpdate.xs_code = xs_code.val().trim();
	dataUpdate.xs_name = xs_name.val().trim();
	dataUpdate.xs_sex = xs_sex.val().trim();
	dataUpdate.xs_phone = xs_phone.val().trim();
	dataUpdate.xs_email = xs_email.val().trim();
	dataUpdate.nj_id = nj_id.val().trim();
	dataUpdate.bj_id = bj_id.val().trim();
	/*3.上传服务器*/
	editSaveSubmit(form, xs_code, xs_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#studentTable").datagrid('collapseRow',rowNumber);
	$('#studentTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param xs_code 编码
 * @param xs_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, xs_code, xs_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/student/edit.do",//url
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
					if (nullFlag == "xs_code") {
						xs_code.focus();
					} else if (nullFlag == "xs_name") {
						xs_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "xs_code") {
						xs_code.focus();
					} else if (repeat == "xs_name") {
						xs_name.focus();
					}
				}
				return;
			}
			
			//要修改的数据如果不在当前年级，删除当前行
			var rows = $('#studentTable').datagrid('getRows');
			var currRow = rows[index];
			if(isModifiedClass(currRow,dataUpdate)){
				$('#studentTable').datagrid('deleteRow', index);
				return;
			}
			
			//修改数据
			$("#studentTable").datagrid('updateRow', {
				index : parseInt(index),
				row : dataUpdate
			});
			
			//修改数据成功后重新赋值表单
			var formNew = $("#studentTable").datagrid('getRowDetail', index).children("div form");
			setForm(formNew,index,dataUpdate);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改数据失败！");
		}
	});
	
	/**
	 * 是否修改了班级
	 * @returns
	 */
	function isModifiedClass(currRow, updateRow) {
		if(currRow.bj_id == updateRow.bj_id) {
			return false;
		} else {
			return true;
		}
	}
}

