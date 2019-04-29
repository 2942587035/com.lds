/**
 * 学校
 */
$(function() {
	/*增加面板和修改面板不显示*/
	$("#school .easyui-panel").css({
		"display" : "none"
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#schoolTable").datagrid('getSelected');
				if (selectedRow) {
					var xx_id = selectedRow.xx_id;
					if (xx_id != null && xx_id != '') {
						//上传服务器
						var selectedRowIndex = $("#schoolTable").datagrid(
								'getRowIndex', selectedRow);
						remove(xx_id, selectedRowIndex);
					}
				}
			});
	
	/* 表格  */
	$("#schoolTable").datagrid(
			{
				columns : [ [ {
					field : 'xx_id',
					title : 'ID',
					width : 80
				}, {
					field : 'xx_code',
					title : '编码',
					width : 120
				}, {
					field : 'xx_name',
					title : '名称',
					width : 200
				}, {
					field : 'xx_type',
					title : '类型',
					width : 80,
					formatter : typeFormat
				}, {
					field : 'xx_remark',
					title : '备注',
					width : 300
				} ] ],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#schoolToolbar',
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
					$('#schoolTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					form.form('load', getData(index, row));
					$('#schoolTable').datagrid('fixDetailRowHeight', index);
				}
			});
	/*查询表格数据*/
	search();
});

/*删除事件*/
function remove(xx_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/SchoolServlet",//url
		async : true,
		data : {
			"type" : "remove",
			"xx_id" : xx_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				alert(success.msg);
				$('#schoolTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("学校信息删除失败！");
		}
	});
}
/* 查询 */
function search() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/SchoolServlet",//url
		async : true,
		data : {
			"type" : "search"
		},
		success : function(data, textStatus) {
			$("#schoolTable").datagrid({
				data : data
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("学校信息获取失败！");
		}
	});
}

/**
 * 格式化学校类型
 * @param value
 * @param row
 * @param index
 * @returns
 */
function typeFormat(value, row, index) {
	if (value == "1") {
		value = "小学";
	} else if (value == "2") {
		value = "初中";
	} else if (value == "3") {
		value = "高中";
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
	data.xx_id = row.xx_id;
	data.xx_code = row.xx_code;
	data.xx_name = row.xx_name;
	data.xx_remark = row.xx_remark;

	var xx_type = row.xx_type;
	if (xx_type == '小学') {
		xx_type = 1;
	} else if (xx_type == '初中') {
		xx_type = 2;
	} else if (xx_type == '高中') {
		xx_type = 3;
	}
	data.xx_type = xx_type;

	return data;
}