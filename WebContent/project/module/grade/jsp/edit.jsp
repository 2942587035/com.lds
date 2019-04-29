<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/grade/js/edit.js"></script>
<div class="easyui-panel">
	<form>
		<table>
			<tr style="display: none">
				<td class="label">行号：</td>
				<td class="text" colspan="5">
				    <input type="text" name="rowNumber" />
				</td>
			</tr>
			<tr style="display: none">
				<td class="label">操作类型：</td>
				<td class="text" colspan="5">
				    <input type="text" name="type" value="edit" />
				</td>
			</tr>
			<tr style="display: none">
				<td class="label">ID：</td>
				<td class="text" colspan="5">
				    <input type="text" name="nj_id" />
				</td>
			</tr>
			<tr>
				<td class="label">编码：</td>
				    <td class="text"><input type="text" class="disableInputMethod" name="nj_code" 
				    maxlength="7" required="true" readonly="readonly"
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">名称：</td>
				    <td class="text"><input type="text" name="nj_name" maxlength="5" required="true" 
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
			</tr>
			<tr>
				<td colspan="6" style="text-align: right">
				    <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="editSave($(this))">保存</a> 
				    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="editCancel($(this))">取消</a>
				</td>
			</tr>
		</table>
	</form>
</div>
