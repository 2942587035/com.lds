<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学校信息</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
</head>
<body>
    <div id="school">
        <table id="schoolTable"></table>
        <div id="schoolToolbar">
            <jsp:include page="/project/module/school/jsp/toolbar.jsp"></jsp:include>
            <jsp:include page="/project/module/school/jsp/add.jsp"></jsp:include>
		</div>
		<jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
		<jsp:include page="/project/module/school/jsp/edit.jsp"></jsp:include>
    </div>
    
<script src="<%=request.getContextPath()%>/project/module/school/js/school.js"></script>
</body>
</html>