/**
 * 班级
 */
$(function() {
	/*增加面板和修改面板不显示*/
	$("#student .easyui-panel").css({
		"display" : "none"
	});
	
	/* 树点击事件 */
	$("#gradeClassTree").tree({
		/*onlyLeafCheck: false,//仅叶子节点可以被选中*/		
		onSelect : function(selectedNode) {
			/* 点击叶子节点，查询当前年级的班级 */
			if (selectedNode && $("#gradeClassTree").tree('isLeaf',selectedNode.target)) {
				var id = selectedNode.id;
				var text = selectedNode.text;
				var type = selectedNode.type;
				//过滤年级，选中年级，表格无数据
				if(!(type == 'bj' && id != '' && text != '')){
					//设置表格数据为空
					$("#studentTable").datagrid({
						data : []
					});
					return;
				}
				//获得班级
				var bj_id = id;
				var bj_name = text;
				//获得年级
				var selectedNodeParent = $('#gradeClassTree').tree('getParent', selectedNode.target);
				var nj_id = selectedNodeParent.id;
				var nj_name = selectedNodeParent.text;
				
				/*增加面板年级\班级控件默认值为选中的年级、班级*/
				var nj_idOption = $("#nj_id option");
				if(nj_id != nj_idOption.val()) {
					nj_idOption.val(nj_id);
				}
				
				if(nj_name != nj_idOption.text()) {
					nj_idOption.text(nj_name);
				}
				
				var bj_idOption = $("#bj_id option");
				if(nj_id != bj_idOption.val()) {
					bj_idOption.val(bj_id);
				}
				
				if(bj_name != bj_idOption.text()) {
					bj_idOption.text(bj_name);
				}
				//查询当前班级的学生
				search(nj_id,bj_id);
			}
		}
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#studentTable").datagrid('getSelected');
				if (selectedRow) {
					var xs_id = selectedRow.xs_id;
					if (xs_id != null && xs_id != '') {
						//上传服务器
						var selectedRowIndex = $("#studentTable").datagrid('getRowIndex', selectedRow);
						remove(xs_id, selectedRowIndex);
					}
				}
	});
	
	/* 表格  */
	$("#studentTable").datagrid(
			{
				columns : [[ 
					{field : 'xs_id',title : 'ID',width : 80,hidden:'true'}, 
					{field : 'xs_code',title : '编码',width : 200},
					{field : 'xs_name',title : '名称',width : 200}, 
					{field : 'xs_sex',title : '性别',width : 80,formatter : typeFormat},
					{field : 'nj_id',title : '年级ID',width : 80,hidden:'true'},
					{field : 'nj_name',title : '年级',width : 200},
					{field : 'bj_id',title : '班级ID',width : 80,hidden:'true'},
					{field : 'bj_name',title : '班级',width : 200},
					{field : 'xs_phone',title : '手机',width : 100},
					{field : 'xs_email',title : '邮箱',width : 200}
                ]],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#studentToolbar',
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
					$('#studentTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					/*设置表单数据*/
					setForm(form,index,row);
					
					//编码获得焦点
					form.find('input[name=xs_name]').focus();
				}
			});
	
	/*查询年级*/
	searchGradeClass();
});

/**
 * 设置表单
 * @param form 当前表单
 * @param index 当前表单坐标
 * @param data 当前表单对应的数据
 * @returns
 */
function setForm(form,index,data) {
	//年级
	var nj_id = form.find('select[name=nj_id]');
	nj_id.val(data.nj_id);
	//设置班级
	setClass(nj_id);
	//赋值表单
	form.form('load', getData(index, data));
	$('#studentTable').datagrid('fixDetailRowHeight', index);
}

/**
 * 设置班级
 * @param _this
 * @returns
 */
function setClass(_this) {
	//需要设置的班级数据
	var classString = _this.find('option:selected').attr('array');
	var classArray =  JSON.parse(classString);//转换为json对象

	//获得班级
	var bj_id = _this.parent().parent().find('select[name=bj_id]');
	//删除现有数据
	bj_id.empty();
	//设置新数据
    for(var i=0;i < classArray.length;i++) {
    	var value = classArray[i].id;
    	var text = classArray[i].text;
    	
    	bj_id.append("<option value='"+value+"'>"+text+"</option>");
    }
}

/*删除事件*/
function remove(xs_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/student/remove.do",//url
		async : true,
		data : {
			"xs_id" : xs_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				/*alert(success.msg);*/
				$('#studentTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("学生信息删除失败！");
		}
	});
}

/**
 * 查询年级和班级列表
 * @returns
 */
function searchGradeClass() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/student/searchGradeClass.do",//url
		async : true,
		success : function(data, textStatus) {
			//赋值年级列表
			$("#gradeClassTree").tree({
				data : data,
				onLoadSuccess: function (node, data) {
				    if (data.length > 0) {
				          //找到第一个元素
				          var n = $('#gradeClassTree').tree('find', data[0].id);
				          //调用选中事件
				          $('#gradeClassTree').tree('select', n.target);
				    }
				}, 
			});
			//赋值修改面板年级\班级控件
			if(data==null || data.length==0) {
				return;
			}
			
	        for(var i=0;i < data.length;i++) {
	        	var gradeIndex = data[i];
	        	var value = gradeIndex.id;
	        	var text = gradeIndex.text;
	        	var children = gradeIndex.children;
	        	
	        	//年级
	        	$("#nj_id_edit").append("<option array="+JSON.stringify(children)+" value="+value+">"+text+"</option>");
	        }
	        
	        //班级
	        var classArray = data[0].children;
	        for(var i=0;i < classArray.length;i++) {
	        	var value = classArray[i].id;
	        	var text = classArray[i].text;
	        	
	        	$("#bj_id_edit").append("<option value='"+value+"'>"+text+"</option>");
	        }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("（班级/年级）列表获取失败！");
		}
	});
}

/**
 * 查询
 * @returns
 */
function search(nj_id,bj_id) {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/student/search.do",//url
		async : true,
		data : {
			"nj_id": nj_id,
			"bj_id": bj_id
		},
		success : function(data, textStatus) {
			$("#studentTable").datagrid({
				data : data
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("学生信息获取失败！");
		}
	});
}

/**
 * 格式化学生性别
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
	data.xs_id = row.xs_id;
	data.xs_code = row.xs_code;
	data.xs_name = row.xs_name;
	data.xs_phone = row.xs_phone;
	data.xs_email = row.xs_email;
	data.nj_id = row.nj_id;
	data.nj_name = row.nj_name;
	data.bj_id = row.bj_id;
	data.bj_name = row.bj_name;

	var xs_sex = row.xs_sex;
	if (xs_sex == '男') {
		xs_sex = 1;
	} else if (xs_sex == '女') {
		xs_sex = 2;
	} else if (xs_sex == '其他') {
		xs_sex = 3;
	}
	data.xs_sex = xs_sex;

	return data;
}

/**
 * 保存前校验
 * @returns
 */
function checkSaveBefore(type,xs_id,xs_code,xs_name,xs_phone,xs_email,nj_id,bj_id) {
	//类型
	var typeV = type.val().trim();
	if (typeV == "") {
		alert('业务类型为空！');
		return;
	}
	//ID
	if(xs_id != null){
		var xs_idV = xs_id.val().trim();
		if (xs_idV == "") {
			alert('ID为空！');
			return;
		}
	}
	//编码
	var xs_codeV = xs_code.val().trim();
	if (xs_codeV == "") {
		alert('请输入编码！');
		xs_code.focus();
		return false;
	}
	//名称
	var xs_nameV = xs_name.val().trim();
	if (xs_nameV == "") {
		alert('请输入名称！');
		xs_name.focus();
		return false;
	}
	//年级id
	var nj_idV = nj_id.val().trim();
	if (nj_idV == "") {
		alert('年级id为空！');
		nj_id.focus();
		return false;
	}
	//班级id
	var bj_idV = bj_id.val().trim();
	if (bj_idV == "") {
		alert('班级id为空！');
		bj_id.focus();
		return false;
	}

	/*2.正则校验*/
	//编码不能输入中文
	var patternC = new RegExp(/^[^\u4e00-\u9fa5]+$/);
	if (!patternC.test(xs_codeV)) {
		alert("编码不能输入中文！");
		xs_code.focus();
		return false;
	}
	//手机正则
	var xs_phoneV = xs_phone.val().trim();
	if(xs_phoneV != '') {
		var phonePattern = /^\d{11}$/;
		if(!phonePattern.test(xs_phoneV)){
			alert("只能输入11位数字");
			return;
		}
	}
	//邮箱正则
	var xs_emailV = xs_email.val().trim();
	if(xs_emailV != '') {
		var emailPattern = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
		if(!emailPattern.test(xs_emailV)){
			alert("邮箱格式不正确");
			return false;
		}
	}
	
	return true;
}