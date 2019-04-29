/**
 * 班级
 */
$(function() {
	/*增加面板和修改面板不显示*/
	$("#course .easyui-panel").css({
		"display" : "none"
	});
	
	/* 树点击事件 */
	$("#gradeTree").tree({
		onSelect : function(selectedNode) {
			/* 点击叶子节点，查询当前年级的班级 */
			if (selectedNode && $("#gradeTree").tree('isLeaf',selectedNode.target)) {
				var nj_id = selectedNode.id;
				if(nj_id == null){
					return;
				}
				/*增加面板年级控件默认值为选中的年级*/
				var option = $("#nj_id option");
				if(nj_id != option.val()) {
					option.val(nj_id);
				}
				
				var text = selectedNode.text;
				if(text != option.text()) {
					option.text(text);
				}
				//查询当前年级的班级
				search(nj_id);
			}
		}
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#courseTable").datagrid('getSelected');
				if (selectedRow) {
					var kc_id = selectedRow.kc_id;
					if (kc_id != null && kc_id != '') {
						//上传服务器
						var selectedRowIndex = $("#courseTable").datagrid('getRowIndex', selectedRow);
						remove(kc_id, selectedRowIndex);
					}
				}
			});
	
	/* 表格  */
	$("#courseTable").datagrid(
			{
				columns : [ [
					{field : 'kc_id',title : 'ID',width : 80,hidden:'true'}, 
					{field : 'kc_code',title : '编码',width : 300}, 
					{field : 'kc_name',title : '名称',width : 300},
					{field : 'nj_id',title : '年级id',width : 100,hidden:'true'}, 
					{field : 'nj_name',title : '年级',width : 300}
				] ],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#courseToolbar',
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
					$('#courseTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					form.form('load', getData(index, row));
					$('#courseTable').datagrid('fixDetailRowHeight', index);
					
					//获得焦点
					form.find('input[name=kc_name]').focus();
				}
			});
	/*查询年级*/
	searchGrade();
});

/*删除事件*/
function remove(kc_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/course/remove.do",//url
		async : true,
		data : {
			"kc_id" : kc_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				/*alert(success.msg);*/
				$('#courseTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("班级信息删除失败！");
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
		url : $("#projectPath",parent.document).text()+"/course/searchGrade.do",//url
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
		url : $("#projectPath",parent.document).text()+"/course/search.do",//url
		async : true,
		data : {
			"nj_id": nj_id
		},
		success : function(data, textStatus) {
			$("#courseTable").datagrid({
				data : data,
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("班级信息获取失败！");
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
	data.kc_id = row.kc_id;
	data.kc_code = row.kc_code;
	data.kc_name = row.kc_name;
	data.nj_id = row.nj_id;
	return data;
}

/**
 * 保存前校验
 * @returns
 */
function checkSaveBefore(type,kc_id,kc_code,kc_name,nj_id) {
	//类型
	var typeV = type.val().trim();
	if (typeV == "") {
		alert('业务类型为空！');
		return;
	}
	//ID
	if(kc_id != null){
		var kc_idV = kc_id.val().trim();
		if (kc_idV == "") {
			alert('ID为空！');
			return;
		}
	}
	//编码
	var kc_codeV = kc_code.val().trim();
	if (kc_codeV == "") {
		alert('请选择编码！');
		kc_code.focus();
		return false;
	}
	//编码不能输入中文
	var patternC = new RegExp(/^[^\u4e00-\u9fa5]+$/);
	if (!patternC.test(kc_codeV)) {
		alert("编码不能输入中文！");
		kc_code.focus();
		return;
	}
	//名称
	var kc_nameV = kc_name.val().trim();
	if (kc_nameV == "") {
		alert('请选择名称！');
		kc_name.focus();
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