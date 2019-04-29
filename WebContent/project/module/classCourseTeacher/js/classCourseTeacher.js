/**
 * 班级
 */
$(function() {
	/*增加面板和修改面板不显示*/
	$("#classCourseTeacher .easyui-panel").css({
		"display" : "none"
	});
	
	/* 树点击事件 */
	$("#gradeClassAndCourseTree").tree({
		/*onlyLeafCheck: false,//仅叶子节点可以被选中*/		
		onSelect : function(selectedNode) {
			/* 点击叶子节点，查询当前年级的班级 */
			if (selectedNode && $("#gradeClassAndCourseTree").tree('isLeaf',selectedNode.target)) {
				//获得年级
				var nj_id = selectedNode.id;
				var nj_name = selectedNode.text;
				
				/*增加面板年级控件默认值为选中的年级*/
				var nj_idOption = $("#nj_id option");
				if(nj_id != nj_idOption.val()) {
					nj_idOption.val(nj_id);
				}
				
				if(nj_name != nj_idOption.text()) {
					nj_idOption.text(nj_name);
				}
				//赋值班级和课程
				//获得班级
				var classArray = selectedNode.classArray;
				//获得课程
				var courseArray = selectedNode.courseArray;
				setClassCourse(classArray,$('#bj_id'),courseArray,$('#kc_id'));
				//查询生
				search(nj_id);
			}
		}
	});

	/*右键删除按钮点击事件*/
	$('#removeMenu').click(
			function() {
				var selectedRow = $("#classCourseTeacherTable").datagrid('getSelected');
				if (selectedRow) {
					var nbkj_id = selectedRow.nbkj_id;
					if (nbkj_id != null && nbkj_id != '') {
						//上传服务器
						var selectedRowIndex = $("#classCourseTeacherTable").datagrid('getRowIndex', selectedRow);
						remove(nbkj_id, selectedRowIndex);
					}
				}
	});
	
	/* 表格  */
	$("#classCourseTeacherTable").datagrid(
			{
				columns : [[ 
					{field : 'nbkj_id',title : 'ID',width : 80,hidden:'true'}, 
					{field : 'js_id',title : '教师ID',width : 80,hidden:'true'},
					{field : 'js_name',title : '教师',width : 200},
					{field : 'js_code',title : '教师编码',width : 200},
					{field : 'kc_id',title : '课程ID',width : 200,hidden:'true'}, 
					{field : 'kc_name',title : '课程',width : 200}, 
					{field : 'bj_id',title : '班级ID',width : 80,hidden:'true'},
					{field : 'bj_name',title : '班级',width : 200},
					{field : 'nj_id',title : '年级ID',width : 80,hidden:'true'},
					{field : 'nj_name',title : '年级',width : 200}

                ]],
				rownumbers : true,
				singleSelect : true,
				toolbar : '#classCourseTeacherToolbar',
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
					$('#classCourseTeacherTable').datagrid('selectRow', index);
					var form = $(this).datagrid('getRowDetail', index).children("div form");
					
					//赋值教师
					form.find('input[name=js_id]').combobox({
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
								if (item.js_id == row.js_id) {
									$(this).combobox("select", item.js_id);
									break;
								}
							}
							/*设置表单数据*/
							setForm(form,index,row);
							//编码获得焦点
							$(this).focus();
						}
					});
				}
			});
	
	/*查询年级*/
	searchGradeClassCourse();
	//查询教师
	searchTeacher();
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
	setClassAndCourse(nj_id);
	//赋值表单
	//行号
	var rowNumber = form.find('input[name=rowNumber]');
	rowNumber.val(index);
	//类型
	var type = form.find('input[name=type]');
	type.val('edit');
	//ID
	var nbkj_id = form.find('input[name=nbkj_id]');
	nbkj_id.val(data.nbkj_id);
	//课程
	var kc_id = form.find('select[name=kc_id]');
	kc_id.val(data.kc_id);
	//班级
	var bj_id = form.find('select[name=bj_id]');
	bj_id.val(data.bj_id);

	$('#classCourseTeacherTable').datagrid('fixDetailRowHeight', index);
}

/**
 * 赋值班级和课程
 * @returns
 */
function setClassCourse(classArray,bj_id,courseArray,kc_id) {
	//删除现有数据
	bj_id.empty();
	//设置新数据
    if(classArray != null){
		 for(var i=0;i < classArray.length;i++) {
		    	var value = classArray[i].id;
		    	var text = classArray[i].text;
		    	
		    	bj_id.append("<option value='"+value+"'>"+text+"</option>");
		 }
	}
    
	//删除现有数据
	kc_id.empty();
	if(courseArray != null) {
		//设置新数据
	    for(var i=0;i < courseArray.length;i++) {
	    	var value = courseArray[i].id;
	    	var text = courseArray[i].text;
	    	
	    	kc_id.append("<option value='"+value+"'>"+text+"</option>");
	    }
	}
}

/**
 * 修改面板设置班级和课程
 * @param _this
 * @returns
 */
function setClassAndCourse(_this) {
	var nj_idSelected = _this.find('option:selected');
	var top = _this.parent().parent().parent();
	//需要设置的班级数据
	var classString = nj_idSelected.attr('classArray');
	var classArray =  JSON.parse(classString);
	//获得班级
	var bj_id = top.find('select[name=bj_id]');
    
	//需要设置的课程数据
	var courseString = nj_idSelected.attr('courseArray');
	var courseArray =  JSON.parse(courseString);
	//获得课程
	var kc_id = top.find('select[name=kc_id]');
	setClassCourse(classArray,bj_id,courseArray,kc_id);
}

/*删除事件*/
function remove(nbkj_id, selectedRowIndex) {
	if (!confirm("确定删除？")) {
		return;
	}
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/classCourseTeacher/remove.do",//url
		async : true,
		data : {
			"nbkj_id" : nbkj_id
		},
		success : function(data, textStatus) {
			var success = data.success;
			var error = data.error;
			if (error != null) {
				alert(error.msg);
			} else if (success != null) {
				/*alert(success.msg);*/
				$('#classCourseTeacherTable').datagrid('deleteRow', selectedRowIndex);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("信息删除失败！");
		}
	});
}

/**
 * 查询所有教师
 */
function searchTeacher() {
	$.ajax({
		type : "POST",//方法性别
		dataType : "json",//预期服务器返回的数据性别 
		url : $("#projectPath",parent.document).text()+"/teacher/search.do",//url
		async : true,
		success : function(data, textStatus) {
			for(var i=0; i < data.length; i++) {
				var object = data[i];
				object.js_name = object.js_name +" ["+ object.js_code+"]";
			};
			//增加教师
			$('#js_id').combobox({
				valueField : 'js_id',
				textField : 'js_name',
				data : data,
				filter: function(q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) >= 0 || row.js_code.indexOf(q) >= 0;
				},
				onLoadSuccess: function (data){
					var val = $(this).combobox("getData");
					$(this).combobox("select", val[0].js_id);
				}
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("教师信息获取失败！");
		}
	});
}

/**
 * 查询年级和班级列表
 * @returns
 */
function searchGradeClassCourse() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath",parent.document).text()+"/classCourseTeacher/searchGradeClassCourse.do",//url
		async : true,
		success : function(data, textStatus) {
			//赋值年级列表
			$("#gradeClassAndCourseTree").tree({
				data : data,
				onLoadSuccess: function (node, data) {
				    if (data.length > 0) {
				          //找到第一个元素
				          var n = $('#gradeClassAndCourseTree').tree('find', data[0].id);
				          //调用选中事件
				          $('#gradeClassAndCourseTree').tree('select', n.target);
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
	        	//班级
	        	var classArray = gradeIndex.classArray;
	        	//课程
	        	var courseArray = gradeIndex.courseArray;
	        	
	        	//年级
	        	$("#nj_id_edit").append("<option classArray="+JSON.stringify(classArray)+ " courseArray="+JSON.stringify(courseArray)+" value="+value+">"+text+"</option>");
	        }
	        
	        //班级
	        var classArray = data[0].classArray;
	        if(classArray != null) {
		        for(var i=0;i < classArray.length;i++) {
		        	var value = classArray[i].id;
		        	var text = classArray[i].text;
		        	
		        	$("#bj_id_edit").append("<option value='"+value+"'>"+text+"</option>");
		        }
	        }
	        
	        //课程
	        var courseArray = data[0].courseArray;
	        if(courseArray != null) {
		        for(var i=0;i < courseArray.length;i++) {
		        	var value = courseArray[i].id;
		        	var text = courseArray[i].text;
		        	
		        	$("#kc_id_edit").append("<option value='"+value+"'>"+text+"</option>");
		        }
	        }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("年级列表获取失败！");
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
		url : $("#projectPath",parent.document).text()+"/classCourseTeacher/search.do",//url
		async : true,
		data : {
			"nj_id": nj_id
		},
		success : function(data, textStatus) {
			$("#classCourseTeacherTable").datagrid({
				data : data
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("信息获取失败！");
		}
	});
}

/**
 * 保存前校验
 * @returns
 */
function checkSaveBefore(type,nbkj_id,js_id,kc_id,nj_id,bj_id) {
	//ID
	if(nbkj_id != null){
		var nbkj_idV = nbkj_id.val();
		if (nbkj_idV == "") {
			alert('ID为空！');
			return;
		}
	}
	//教师ID
	var js_idV = js_id.val();
	if (js_idV == "") {
		alert('请选择教师！');
		js_id.focus();
		return false;
	}
	//课程ID
	var kc_idV = kc_id.val();
	if (kc_idV == "") {
		alert('请选择课程！');
		kc_id.focus();
		return false;
	}
	//年级id
	var nj_idV = nj_id.val();
	if (nj_idV == "") {
		alert('年级id为空！');
		nj_id.focus();
		return false;
	}
	//班级id
	var bj_idV = bj_id.val();
	if (bj_idV == "") {
		alert('班级id为空！');
		bj_id.focus();
		return false;
	}

	return true;
}