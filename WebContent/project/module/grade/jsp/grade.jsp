<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>年级信息</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
</head>
<body>
    <div id="grade">
        <table id="gradeTable"></table>
        <div id="gradeToolbar">
            <jsp:include page="/project/module/grade/jsp/toolbar.jsp"></jsp:include>
            <jsp:include page="/project/module/grade/jsp/add.jsp"></jsp:include>
		</div>
		<jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
		<jsp:include page="/project/module/grade/jsp/edit.jsp"></jsp:include>
    </div>
    
<script src="<%=request.getContextPath()%>/project/module/grade/js/grade.js"></script>
</body>
</html>