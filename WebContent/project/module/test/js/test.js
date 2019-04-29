$(function() {
	/*增加面板和修改面板不显示*/
	$("#test .easyui-panel").css({
		"display" : "none"
	});
	
	/* 树点击事件 */
	$("#gradeTree").tree({
		onSelect : function(selectedNode) {
			/* 点击叶子节点，查询当前年级 */
			if (selectedNode && $("#gradeTree").tree('isLeaf',selectedNode.target)) {
				var nj_id = selectedNode.id;
				var nj_name = selectedNode.text;
				/*增加面板年级控件默认值为选中的年级*/
				var option = $("#nj_id option");
				if(nj_id != option.val()) {
					option.val(nj_id);
				}
				
				if(nj_name != option.text()) {
					option.text(nj_name);
				}
				//查询当前年级的数据
				search(nj_id);
			}
		}
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#testTable").datagrid('getSelected');
				if (selectedRow) {
					var cs_id = selectedRow.cs_id;
					if (cs_id != null && cs_id != '') {
						//上传服务器
						var selectedRowIndex = $("#testTable").datagrid('getRowIndex', selectedRow);
						remove(cs_id, selectedRowIndex);
					}
				}
			});
	
	/* 表格  */
	$("#testTable").datagrid(
			{
				columns : [ [
					{field : 'cs_id',title : 'ID',width : 80,hidden:'true'}, 
					{field : 'cs_code',title : '编码',width : 100}, 
					{field : 'cs_name',title : '名称',width : 200},
					{field : 'cs_type',title : '类型',width : 100,formatter : typeFormat}, 
					{field : 'cs_remark',title : '备注',width : 300}, 
					{field : 'nj_id',title : '年级ID',width : 100,hidden:'true'}, 
					{field : 'nj_name',title : '年级',width : 200}
				] ],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#testToolbar',
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
					return $(".easyui-panel")[2].innerHTML;
				},

				onExpandRow : function(index, row) {
					//展开后设置选中行
					$('#testTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					form.form('load', getData(index, row));
					$('#testTable').datagrid('fixDetailRowHeight', index);
					//编码获得焦点
					form.find('input[name=cs_name]').focus();
				}
			});
	/*查询年级*/
	searchGrade();
});

/*删除事件*/
function remove(cs_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/test/remove.do",//url
		async : true,
		data : {
			"cs_id" : cs_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				/*alert(success.msg);*/
				$('#testTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("信息删除失败！");
		}
	});
}

/**
 * 查询年级列表
 * @returns
 */
function searchGrade() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/test/searchGrade.do",//url
		async : true,
		success : function(data, textStatus) {
			//赋值年级列表
			$("#gradeTree").tree({
				data : data,
				onLoadSuccess: function (node, data) {
				    if (data.length > 0) {
				          //找到第一个元素
				          var n = $('#gradeTree').tree('find', data[0].id);
				          //调用选中事件
				          $('#gradeTree').tree('select', n.target);
				    }
				}, 
			});
			//赋值修改面板年级控件
			if(data==null || data.length==0) {
				return;
			}
			
	        for(var i=0;i < data.length;i++) {
	        	var value = data[i].id;
	        	var text = data[i].text;
	        	
	        	$("#nj_id_edit").append("<option value='"+value+"'>"+text+"</option>");
	        }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("年级信息获取失败！");
		}
	});
}

/**
 * 查询
 * @returns
 */
function search(nj_id) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/test/search.do",//url
		async : true,
		data : {
			"nj_id": nj_id
		},
		success : function(data, textStatus) {
			$("#testTable").datagrid({
				data : data,
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("信息获取失败！");
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
		value = "年级统考";
	} else if (value == "2") {
		value = "普通测试";
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
	data.cs_id = row.cs_id;
	data.cs_code = row.cs_code;
	data.cs_name = row.cs_name;
	data.cs_type = row.cs_type;
	data.cs_remark = row.cs_remark;
	data.nj_id = row.nj_id;
	return data;
}

/**
 * 保存前校验
 * @returns
 */
function checkSaveBefore(type,cs_id,cs_code,cs_name,cs_type,nj_id) {
	//类型
	var typeV = type.val().trim();
	if (typeV == "") {
		alert('业务类型为空！');
		return false;
	}
	//ID
	if(cs_id != null){
		var cs_idV = cs_id.val().trim();
		if (cs_idV == "") {
			alert('ID为空！');
			return false;
		}
	}
	//编码
	var cs_codeV = cs_code.val().trim();
	if (cs_codeV == "") {
		alert('请选择编码！');
		cs_code.focus();
		return false;
	}
	//编码不能输入中文
	var patternC = new RegExp(/^[^\u4e00-\u9fa5]+$/);
	if (!patternC.test(cs_codeV)) {
		alert("编码不能输入中文！");
		cs_code.focus();
		return false;
	}
	//名称
	var cs_nameV = cs_name.val().trim();
	if (cs_nameV == "") {
		alert('请选择名称！');
		cs_name.focus();
		return false;
	}
	//类型
	var cs_typeV = cs_type.val().trim();
	if (cs_typeV == "") {
		alert('类型为空！');
		cs_type.focus();
		return false;
	}
	//年级id
	var nj_idV = nj_id.val().trim();
	if (nj_idV == "") {
		alert('年级id为空！');
		nj_id.focus();
		return false;
	}

	
	return true;
}