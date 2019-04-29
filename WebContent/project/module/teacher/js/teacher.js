/**
 * 教师
 */
$(function() {
	/*增加面板和修改面板不显示*/
	$("#teacher .easyui-panel").css({
		"display" : "none"
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#teacherTable").datagrid('getSelected');
				if (selectedRow) {
					var js_id = selectedRow.js_id;
					if (js_id != null && js_id != '') {
						//上传服务器
						var selectedRowIndex = $("#teacherTable").datagrid(
								'getRowIndex', selectedRow);
						remove(js_id, selectedRowIndex);
					}
				}
	});
	
	/* 表格  */
	$("#teacherTable").datagrid(
			{
				columns : [[ 
					{field : 'js_id',title : 'ID',width : 80,hidden:'true'}, 
					{field : 'js_code',title : '编码',width : 200},
					{field : 'js_name',title : '名称',width : 200}, 
					{field : 'js_sex',title : '性别',width : 80,formatter : typeFormat}, 
					{field : 'js_phone',title : '手机',width : 200},
					{field : 'js_email',title : '邮箱',width : 200}
				] ],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#teacherToolbar',
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid("selectRow", rowIndex);
					$('#removeMenu').menu('show', {
						left : e.pageX,
						top : e.pageY
					});
				},
				view : detailview,
				detailFormatter : function(index, row) {
					return $(".easyui-panel")[1].innerHTML;
				},

				onExpandRow : function(index, row) {
					//展开后设置选中行
					$('#teacherTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					form.form('load', getData(index, row));
					$('#teacherTable').datagrid('fixDetailRowHeight', index);
					
					//编码获得焦点
					form.find('input[name=js_name]').focus();
				}
			});
	/*查询表格数据*/
	search();
});

/*删除事件*/
function remove(js_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法性别
		dataType : "json",//预期服务器返回的数据性别 
		url : $("#projectPath",parent.document).text()+"/teacher/remove.do",//url
		async : true,
		data : {
			"js_id" : js_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				alert(success.msg);
				$('#teacherTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("教师信息删除失败！");
		}
	});
}
/* 查询 */
function search() {
	$.ajax({
		type : "POST",//方法性别
		dataType : "json",//预期服务器返回的数据性别 
		url : $("#projectPath",parent.document).text()+"/teacher/search.do",//url
		async : true,
		success : function(data, textStatus) {
			$("#teacherTable").datagrid({
				data : data
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("教师信息获取失败！");
		}
	});
}

/**
 * 格式化教师性别
 * @param value
 * @param row
 * @param index
 * @returns
 */
function typeFormat(value, row, index) {
	if (value == "1") {
		value = "男";
	} else if (value == "2") {
		value = "女";
	} else if (value == "3") {
		value = "其他";
	}
	return value;
}

/**
 * 获得表格当前行的数据
 * @param index
 * @param row
 * @returns
 */
function getData(index, row) {
	var data = new Object();
	data.rowNumber = index;
	data.type = 'edit';
	data.js_id = row.js_id;
	data.js_code = row.js_code;
	data.js_name = row.js_name;
	data.js_phone = row.js_phone;
	data.js_email = row.js_email;

	var js_sex = row.js_sex;
	if (js_sex == '男') {
		js_sex = 1;
	} else if (js_sex == '女') {
		js_sex = 2;
	} else if (js_sex == '其他') {
		js_sex = 3;
	}
	data.js_sex = js_sex;

	return data;
}

/**
 * 保存前校验
 * @returns
 */
function checkSaveBefore(type,js_id,js_code,js_name,js_phone,js_email) {
	//类型
	var typeV = type.val().trim();
	if (typeV == "") {
		alert('业务类型为空！');
		return;
	}
	//ID
	if(js_id != null){
		var js_idV = js_id.val().trim();
		if (js_idV == "") {
			alert('ID为空！');
			return;
		}
	}
	//编码
	var js_codeV = js_code.val().trim();
	if (js_codeV == "") {
		alert('请输入编码！');
		js_code.focus();
		return false;
	}
	//名称
	var js_nameV = js_name.val().trim();
	if (js_nameV == "") {
		alert('请输入名称！');
		js_name.focus();
		return false;
	}

	/*2.正则校验*/
	//编码不能输入中文
	var patternC = new RegExp(/^[^\u4e00-\u9fa5]+$/);
	if (!patternC.test(js_codeV)) {
		alert("编码不能输入中文！");
		js_code.focus();
		return false;
	}
	//手机正则
	var js_phoneV = js_phone.val().trim();
	if(js_phoneV != '') {
		var phonePattern = /^\d{11}$/;
		if(!phonePattern.test(js_phoneV)){
			alert("只能输入11位数字");
			return;
		}
	}
	//邮箱正则
	var js_emailV = js_email.val().trim();
	if(js_emailV != '') {
		var emailPattern = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		if(!emailPattern.test(js_emailV)){
			alert("邮箱格式不正确");
			return false;
		}
	}
	
	return true;
}