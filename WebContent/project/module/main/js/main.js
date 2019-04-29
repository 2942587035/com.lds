/**
 * 主界面
 */

/* 界面初始化完毕 */
$(function() {
	search();
	searchMenuPower();
	/* 加载欢迎页 */
	addTab('欢迎页', $("#projectPath").text()+'/project/module/welcomePage/jsp/welcomePage.jsp');
	/* 点击事件 */
	$("#menuTree").tree({
		onClick : function() {
			/* 点击叶子节点，显示对应的面板 */
			var selectedNode = $("#menuTree").tree('getSelected');
			if (selectedNode && $("#menuTree").tree('isLeaf',selectedNode.target)) {
				var text = selectedNode.text;
				switch (text) {
				case '修改密码':
					addTab('修改密码',$("#projectPath").text()+'/project/module/modifyPassword/jsp/modifyPassword.jsp');
					break;
				case '分配教师':
					addTab('分配教师',$("#projectPath").text()+'/project/module/classCourseTeacher/jsp/classCourseTeacher.jsp');
					break;
				case '测试信息':
					addTab('测试信息',$("#projectPath").text()+'/project/module/test/jsp/test.jsp');
					break;
				case '成绩信息':
					addTab('成绩信息',$("#projectPath").text()+'/project/module/score/jsp/score.jsp');
					break;
				case '学生信息':
					addTab('学生信息',$("#projectPath").text()+'/project/module/student/jsp/student.jsp');
					break;
				case '教师信息':
					addTab('教师信息',$("#projectPath").text()+'/project/module/teacher/jsp/teacher.jsp');
					break;
				case '学校信息':
					addTab('学校信息',$("#projectPath").text()+'/project/module/school/jsp/school.jsp');
					break;
				case '年级信息':
					addTab('年级信息',$("#projectPath").text()+'/project/module/grade/jsp/grade.jsp');
					break;
				case '班级信息':
					addTab('班级信息',$("#projectPath").text()+'/project/module/class/jsp/class.jsp');
					break;
				case '课程信息':
					addTab('课程信息',$("#projectPath").text()+'/project/module/course/jsp/course.jsp');
					break;
				default:
					alert('该树节点没有配置！');
					break;
				}
			}
		}
	});
    /* 退出登录点击事件，删除cookie,重新登录 */
    $("#exitLogin").click(function() {
    	logout();
	});
});

/**
 * 新增选项卡
 * @param title
 * @param url
 * @returns
 */
function addTab(title, url) {
	var $tab = $("#contentPanel");
	if ($tab.tabs('exists', title)) {
		$tab.tabs('select', title);
	} else {
		$tab.tabs('add', {
			title : title,
			content : createFrame(url),
			closable : true
		})
	}
}

/**
 * 创建内部界面
 * @param url
 * @returns
 */
function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0" src="' + url
			+ '" style="width:100%;height:100%;"></iframe>';
	return s;
}

/* 查询 */
function search() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath").text()+"/school/search.do",//url
		async : true,
		success : function(data, textStatus) {
			if(data.length > 1) {
				alert("数据库中只能存在一个学校！");
			} else if(data.length == 1) {
				$("#xx_id").text(data[0].xx_id);
				$("#xx_name").text(data[0].xx_name);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("信息获取失败！");
		}
	});
}

function searchMenuPower() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath").text()+"/menuPower/menuPower.do",
		async : true,
		data : {
			"yh_type" : $("#yh_type").text()
		},
		success : function(data, textStatus) {
			$("#menuTree").tree({
				data : data,
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("列表获取失败！");
		}
	});
}

function logout() {
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型 
		url : $("#projectPath").text()+"/login/logout.do",
		async : true,
		success : function(data, textStatus) {
			window.location.href = $("#projectPath").text()+"/project/module/login/jsp/login.jsp?cookie=delete";
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("登出失败！");
		}
	});
}

/**
 * 焦点跳转
 * @param thisInput
 * @returns
 */
function transferFocus(event,currComponent){
	if(event.keyCode!=13) {return;}
	var parent = $(currComponent).parent().parent().parent();
    var components = $('input,select,a',parent);
    for(var i = 0;i<components.length;i++){
      // 如果是最后一个，则焦点回到第一个
      if(i==(components.length-1)){
        components[0].focus();
        break;
      }else if(currComponent == components[i]){
        components[i+1].focus();
        break;
      }
    }
} 