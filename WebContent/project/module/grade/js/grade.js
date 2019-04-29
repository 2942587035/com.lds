/**
 * 年级
 */
$(function() {
	/*增加面板和修改面板不显示*/
	$("#grade .easyui-panel").css({
		"display" : "none"
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#gradeTable").datagrid('getSelected');
				if (selectedRow) {
					var nj_id = selectedRow.nj_id;
					if (nj_id != null && nj_id != '') {
						//上传服务器
						var selectedRowIndex = $("#gradeTable").datagrid('getRowIndex', selectedRow);
						remove(nj_id, selectedRowIndex);
					}
				}
			});
	
	/* 表格  */
	$("#gradeTable").datagrid(
			{
				columns : [[ 
					{field : 'nj_id',title : 'ID',width : 100,hidden:'true'}, 
					{field : 'nj_code',title : '编码',width : 300}, 
					{field : 'nj_name',title : '名称',width : 300} 
				]],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#gradeToolbar',
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
					$('#gradeTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					form.form('load', getData(index, row));
					$('#gradeTable').datagrid('fixDetailRowHeight', index);
					
					//获得焦点
					form.find('input[name=nj_name]').focus();
				}
			});
	/*查询表格数据*/
	search();
});

/*删除事件*/
function remove(nj_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/grade/remove.do",//url
		async : true,
		data : {
			"nj_id" : nj_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				/*alert(success.msg);*/
				$('#gradeTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("年级信息删除失败！");
		}
	});
}
/* 查询 */
function search() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/grade/search.do",//url
		async : true,
		success : function(data, textStatus) {
			$("#gradeTable").datagrid({
				data : data
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("年级信息获取失败！");
		}
	});
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
	data.nj_id = row.nj_id;
	data.nj_code = row.nj_code;
	data.nj_name = row.nj_name;

	return data;
}

/**
 * 保存前校验
 * @returns
 */
function checkSaveBefore(type,nj_id,nj_code,nj_name) {
	//类型
	var typeV = type.val().trim();
	if (typeV == "") {
		alert('业务类型为空！');
		return;
	}
	//ID
	if(nj_id != null){
		var nj_idV = nj_id.val().trim();
		if (nj_idV == "") {
			alert('ID为空！');
			return;
		}
	}
	//编码
	var nj_codeV = nj_code.val().trim();
	if (nj_codeV == "") {
		alert('请选择编码！');
		nj_code.focus();
		return false;
	}
	//编码不能输入中文
	var patternC = new RegExp(/^[^\u4e00-\u9fa5]+$/);
	if (!patternC.test(nj_codeV)) {
		alert("编码不能输入中文！");
		nj_code.focus();
		return;
	}
	//名称
	var nj_nameV = nj_name.val().trim();
	if (nj_nameV == "") {
		alert('请选择名称！');
		nj_name.focus();
		return false;
	}

	return true;
}