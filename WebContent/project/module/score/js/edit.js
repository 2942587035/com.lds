/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#schoolTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	var typeV = form.find('input[name=type]').val().trim();
	if (typeV == "") {
		alert('业务类型为空！');
		return;
	}
	//ID
	var xx_id = form.find('input[name=xx_id]');
	var xx_idV = xx_id.val().trim();
	if (xx_idV == "") {
		alert('ID为空！');
		return;
	}
	//编码
	var xx_code = form.find('input[name=xx_code]');
	var xx_codeV = xx_code.val().trim();
	if (xx_codeV == "") {
		alert('请输入编码！');
		xx_code.focus();
		return;
	}
	//名称
	var xx_name = form.find('input[name=xx_name]');
	var xx_nameV = xx_name.val().trim();
	if (xx_nameV == "") {
		alert('请输入名称！');
		xx_name.focus();
		return;
	}

	/*2.正则校验*/
	//编码不能输入中文
	var patternC = new RegExp(/^[^\u4e00-\u9fa5]+$/);
	if (!patternC.test(xx_codeV)) {
		alert("用户编码不能输入中文！");
		xx_code.focus();
		return;
	}

	//类型
	var xx_typeV = form.find('select[name=xx_type]').val().trim();
	//备注
	var xx_remarkV = form.find('input[name=xx_remark]').val().trim();
	var dataUpdate = new Object();
	dataUpdate.xx_id = dataRowNumber.xx_id;
	dataUpdate.xx_code = xx_codeV;
	dataUpdate.xx_name = xx_nameV;
	dataUpdate.xx_type = xx_typeV;
	dataUpdate.xx_remark = xx_remarkV;
	/*3.上传服务器*/
	editSaveSubmit(form, xx_code, xx_name, dataUpdate, rowNumber);
}

/**
 * 取消：隐藏面板,清空面板数据
 * @param _this
 */
function editCancel(_this) {
	//重新赋值
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#schoolTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	form.form('load', getData(rowNumber, dataRowNumber));
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param xx_code 编码
 * @param xx_name 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, xx_code, xx_name, dataUpdate, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/SchoolServlet",//url
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
					if (nullFlag == "xx_code") {
						xx_code.focus();
					} else if (nullFlag == "xx_name") {
						xx_name.focus();
					}
				} else if (repeat != null) {
					if (repeat == "xx_code") {
						xx_code.focus();
					} else if (repeat == "xx_name") {
						xx_name.focus();
					}
				}
				return;
			}
			//修改数据
			$("#schoolTable").datagrid('updateRow', {
				index : index,
				row : dataUpdate
			});
			//修改数据成功后重新赋值表单
			var formNew = $("#schoolTable").datagrid('getRowDetail', index)
					.children("div form");
			formNew.form('load', getData(index, dataUpdate));
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改数据失败！");
		}
	});
}
