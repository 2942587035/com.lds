<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/student/js/edit.js"></script>
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
				<input type="text" name="xs_id" />
				</td>
			</tr>
			<tr>
				<td class="label">*编码：</td>
				    <td class="text"><input type="text" class="disableInputMethod" name="xs_code" 
				    maxlength="8" required="true" readonly="readonly"
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">*名称：</td>
				    <td class="text"><input type="text" name="xs_name" maxlength="10" required="true" 
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">*性别：</td>
				<td>
				    <select name="xs_sex" class="text" onkeypress="parent.transferFocus(event,this)">
						<option value="1">男</option>
						<option value="2">女</option>
						<option value="3">其他</option>
				    </select>
				</td>
			</tr>
			<tr>
			    <td class="label">年级：</td>
			    <td>
				    <select id="nj_id_edit" name="nj_id" class="text" onchange="setClass($(this))"
				    onkeypress="parent.transferFocus(event,this)">
				    </select>
				</td>
				<td class="label">班级：</td>
				<td>
				    <select id="bj_id_edit" name="bj_id" class="text" onkeypress="parent.transferFocus(event,this)">
				    </select>
				</td>
				<td class="label">手机：</td>
				<td class="text">
				    <input type="text" name="xs_phone" maxlength="20" onkeypress="parent.transferFocus(event,this)"/>
				</td>
			</tr>
			<tr>
				<td class="label">邮箱：</td>
				<td class="text">
				    <input type="text" name="xs_email" maxlength="30" onkeypress="parent.transferFocus(event,this)"/>
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
