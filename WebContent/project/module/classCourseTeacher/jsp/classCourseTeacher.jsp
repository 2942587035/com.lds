<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>分配教师</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
<script src="<%=request.getContextPath()%>/project/module/classCourseTeacher/js/classCourseTeacher.js"></script>
</head>
<body class="easyui-layout" scroll="no">
    <div region="west" border="false" title="年级列表" style="width: 150px">
		<div class="easyui-panel" fit="true">
			<div>
				<ul class="easyui-tree" id="gradeClassAndCourseTree">
				</ul>
			</div>
		</div>
	</div>
    <div id="classCourseTeacher" region="center" border="false" fit="true">
		<table id="classCourseTeacherTable"></table>
		<div id="classCourseTeacherToolbar">
               <jsp:include page="/project/module/classCourseTeacher/jsp/toolbar.jsp"></jsp:include>
               <jsp:include page="/project/module/classCourseTeacher/jsp/add.jsp"></jsp:include>
	    </div>
	    <jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
        <jsp:include page="/project/module/classCourseTeacher/jsp/edit.jsp"></jsp:include>
    </div>
</body>
</html>