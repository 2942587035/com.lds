<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/grade/js/add.js"></script>
<div id="addPanel" class="easyui-panel">
	<form id="addForm">
		<table id="addTable">
			<tr style="display: none">
				<td class="label">操作类型：</td>
				<td class="text" colspan="5">
				<input type="text" id="type" name="type" value="add" />
				</td>
			</tr>
			<tr>
				<td class="label">编码：</td>
				<td class="text">
				    <input type="text" class="disableInputMethod" id="nj_code" name="nj_code" 
				    maxlength="7" required="true" readonly="readonly"
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">名称：</td>
				<td class="text">
				    <input type="text" id="nj_name" name="nj_name" maxlength="5" required="true" 
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="text-align: right">
				    <a href="#" id="addSave" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="addSave()">保存</a> 
				    <a href="#" id="addCancel" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="addCancel()">取消</a>
				</td>
			</tr>
		</table>
	</form>
</div>
