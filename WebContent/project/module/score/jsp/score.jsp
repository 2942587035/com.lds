<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成绩信息</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
</head>
<body>
    <div id="score">
        <table id="scoreTable"></table>
        <div id="scoreToolbar">
            <jsp:include page="/project/module/score/jsp/toolbar.jsp"></jsp:include>
            <jsp:include page="/project/module/score/jsp/add.jsp"></jsp:include>
		</div>
		<jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
		<jsp:include page="/project/module/score/jsp/edit.jsp"></jsp:include>
    </div>
    
<script src="<%=request.getContextPath()%>/project/module/score/js/score.js"></script>
</body>
</html>