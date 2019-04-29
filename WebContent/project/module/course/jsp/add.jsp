<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/course/js/add.js"></script>
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
				    <input type="text" class="disableInputMethod" id="kc_code" name="kc_code" 
				    maxlength="8" required="true" readonly="readonly"
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">名称：</td>
				<td class="text">
				    <input type="text" id="kc_name" name="kc_name" maxlength="10" required="true" 
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">年级：</td>
				<td>
					<select id="nj_id" name="nj_id" class="text" onkeypress="parent.transferFocus(event,this)">
					    <option value="0">空</option>
					</select>
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
