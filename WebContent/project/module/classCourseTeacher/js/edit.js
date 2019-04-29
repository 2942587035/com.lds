/**
 * 编辑
 * @param _this
 */
function editSave(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	var rows = $('#classCourseTeacherTable').datagrid('getRows');
	var dataRowNumber = rows[rowNumber];
	/*1.判空*/
	//类型
	var type = form.find('input[name=type]');
	//ID
	var nbkj_id = form.find('input[name=nbkj_id]');
	//教师id
	var js_id = form.find('input[name=js_id]');
	//课程ID
	var kc_id = form.find('select[name=kc_id]');
	//年级id
	var nj_id = form.find('select[name=nj_id]');
	//班级id
	var bj_id = form.find('select[name=bj_id]');
	
	/*校验*/
	if(!checkSaveBefore(type,nbkj_id,js_id,kc_id,nj_id,bj_id)) {
		return;
	}
	
	/*3.上传服务器*/
	editSaveSubmit(form, js_id, kc_id, rowNumber);
}

/**
 * 取消：隐藏面板
 * @param _this
 */
function editCancel(_this) {
	var form = _this.parent().parent().parent().parent().parent();
	var rowNumber = form.find('input[name=rowNumber]').val();
	$("#classCourseTeacherTable").datagrid('collapseRow',rowNumber);
	$('#classCourseTeacherTable').datagrid('fixRowHeight',rowNumber); 
}

/**
 * 编辑保存提交数据
 * @param form 表单
 * @param js_id 编码
 * @param kc_id 名称
 * @param dataUpdate 待更新数据
 * @param index 更新数据行
 * @returns
 */
function editSaveSubmit(form, js_id, kc_id, index) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/classCourseTeacher/edit.do",//url
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
					if (nullFlag == "js_id") {
						js_id.focus();
					} 
				} else if (repeat != null) {
					if (repeat == "js_id") {
						js_id.focus();
					} 
				}
				return;
			}
			
			var dataUpdate = success.msg;
			//要修改的数据如果不在当前年级，删除当前行
			var rows = $('#classCourseTeacherTable').datagrid('getRows');
			var currRow = rows[index];
			if(isModifiedGrade(currRow,dataUpdate)){
				$('#classCourseTeacherTable').datagrid('deleteRow', index);
				return;
			}
			
			//修改数据
			$("#classCourseTeacherTable").datagrid('updateRow', {
				index : index,
				row : dataUpdate
			});
			
			//修改数据成功后重新赋值表单
			var formNew = $("#classCourseTeacherTable").datagrid('getRowDetail', index).children("div form");
			//赋值教师
			formNew.find('input[name=js_id]').combobox({
				valueField : 'js_id',
				textField : 'js_name',
				data : $('#js_id').combobox('getData'),
				filter: function(q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) >= 0 || row.js_code.indexOf(q) >= 0;
				},
				onLoadSuccess: function (data){
					//教师
				    for(var i=0;i<data.length;i++){
						var item = data[i];
						if (item.js_id == dataUpdate.js_id) {
							$(this).combobox("select", item.js_id);
							break;
						}
					}
					/*设置表单数据*/
					setForm(formNew,index,dataUpdate);
				}
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改数据失败！");
		}
	});
	
	/**
	 * 是否修改了班级
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

