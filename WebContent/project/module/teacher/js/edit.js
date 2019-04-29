/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#teacherTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	//ID
	var js_id = form.find('input[name=js_id]');
	//编码
	var js_code = form.find('input[name=js_code]');
	//名称
	var js_name = form.find('input[name=js_name]');
	//性别
	var js_sex = form.find('select[name=js_sex]');
	//手机
	var js_phone = form.find('input[name=js_phone]');
	//email
	var js_email = form.find('input[name=js_email]');
	
	/*校验*/
	if(!checkSaveBefore(type,js_id,js_code,js_name,js_phone,js_email)){
		return;
	}
	
	var dataUpdate = new Object();
	dataUpdate.js_id = dataRowNumber.js_id;
	dataUpdate.js_code = js_code.val().trim();
	dataUpdate.js_name = js_name.val().trim();
	dataUpdate.js_sex = js_sex.val().trim();
	dataUpdate.js_phone = js_phone.val().trim();
	dataUpdate.js_email = js_email.val().trim();
	/*3.上传服务器*/
	editSaveSubmit(form, js_code, js_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#teacherTable").datagrid('collapseRow',rowNumber);
	$('#teacherTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param js_code 编码
 * @param js_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, js_code, js_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/teacher/edit.do",//url
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
					if (nullFlag == "js_code") {
						js_code.focus();
					} else if (nullFlag == "js_name") {
						js_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "js_code") {
						js_code.focus();
					} else if (repeat == "js_name") {
						js_name.focus();
					}
				}
				return;
			}
			//修改数据
			$("#teacherTable").datagrid('updateRow', {
				index : parseInt(index),
				row : dataUpdate
			});
			//修改数据成功后重新赋值表单
			var formNew = $("#teacherTable").datagrid('getRowDetail', index).children("div form");
			formNew.form('load', getData(index, dataUpdate));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改数据失败！");
		}
	});
}

/**
 * 编辑保存图片
 * @param _this
 * @returns
 */
function editSaveImg(_this) {
	$(_this).parent().find('[name=submit]').click();
}
