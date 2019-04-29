<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生信息</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
<script src="<%=request.getContextPath()%>/project/module/student/js/student.js"></script>
</head>
<body class="easyui-layout" scroll="no">
    <div region="west" border="false" title="（班级/年级）列表" style="width: 150px">
		<div class="easyui-panel" fit="true">
			<div>
				<ul class="easyui-tree" id="gradeClassTree">
				</ul>
			</div>
		</div>
	</div>
    <div id="student" region="center" border="false" fit="true">
		<table id="studentTable"></table>
		<div id="studentToolbar">
               <jsp:include page="/project/module/student/jsp/toolbar.jsp"></jsp:include>
               <jsp:include page="/project/module/student/jsp/add.jsp"></jsp:include>
	    </div>
	    <jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
        <jsp:include page="/project/module/student/jsp/edit.jsp"></jsp:include>
    </div>
</body>
</html>