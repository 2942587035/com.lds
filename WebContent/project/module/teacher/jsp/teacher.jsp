<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>教师信息</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
<script src="<%=request.getContextPath()%>/project/module/teacher/js/teacher.js"></script>
</head>
<body>
    <div id="teacher">
        <table id="teacherTable"></table>
        <div id="teacherToolbar">
            <jsp:include page="/project/module/teacher/jsp/toolbar.jsp"></jsp:include>
            <jsp:include page="/project/module/teacher/jsp/add.jsp"></jsp:include>
		</div>
		<jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
		<jsp:include page="/project/module/teacher/jsp/edit.jsp"></jsp:include>
    </div>
</body>
</html>