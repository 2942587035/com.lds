<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>课程信息</title>
<script src="<%=request.getContextPath()%>/project/common/js/datagrid-detailview.js"></script>
</head>
<body class="easyui-layout" scroll="no">
    <div region="west" border="false" title="年级列表" style="width: 120px">
		<div class="easyui-panel" fit="true">
			<div>
				<ul class="easyui-tree" id="gradeTree">
				</ul>
			</div>
		</div>
	</div>
    <div id="course" region="center" border="false" fit="true">
      	<table id="courseTable"></table>
        <div id="courseToolbar">
            <jsp:include page="/project/module/course/jsp/toolbar.jsp"></jsp:include>
            <jsp:include page="/project/module/course/jsp/add.jsp"></jsp:include>
		</div>
		<jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
	    <jsp:include page="/project/module/course/jsp/edit.jsp"></jsp:include>
    </div>
    
    <script src="<%=request.getContextPath()%>/project/module/course/js/course.js"></script>
</body>
</html>