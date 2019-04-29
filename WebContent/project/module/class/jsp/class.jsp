<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/project/common/jsp/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>班级信息</title>
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
    <div id="class" region="center" border="false" fit="true">
      	<table id="classTable"></table>
        <div id="classToolbar">
            <jsp:include page="/project/module/class/jsp/toolbar.jsp"></jsp:include>
            <jsp:include page="/project/module/class/jsp/add.jsp"></jsp:include>
		</div>
		<jsp:include page="/project/common/jsp/removeMenu.jsp"></jsp:include>
	    <jsp:include page="/project/module/class/jsp/edit.jsp"></jsp:include>
    </div>
    
    <script src="<%=request.getContextPath()%>/project/module/class/js/class.js"></script>
</body>
</html>