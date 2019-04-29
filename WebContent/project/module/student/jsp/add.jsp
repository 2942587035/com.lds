<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/student/js/add.js"></script>
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
				<td class="label">*编码：</td>
				<td class="text">
				    <input type="text" class="disableInputMethod" id="xs_code" name="xs_code" 
				    maxlength="8" required="true" readonly="readonly"
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">*名称：</td>
				<td class="text">
				    <input type="text" id="xs_name" name="xs_name" maxlength="10" required="true" 
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">*性别：</td>
				<td>
					<select id="xs_sex" name="xs_sex" class="text" onkeypress="parent.transferFocus(event,this)">
						<option value="1">男</option>
						<option value="2">女</option>
						<option value="3">其他</option>
					</select>
				</td>
			</tr>
			<tr>
			    <td class="label">年级：</td>
				<td>
					<select id="nj_id" name="nj_id" class="text" onkeypress="parent.transferFocus(event,this)">
					    <option value="0">空</option>
					</select>
				</td>
				<td class="label">班级：</td>
				<td>
					<select id="bj_id" name="bj_id" class="text" onkeypress="parent.transferFocus(event,this)">
					    <option value="0">空</option>
					</select>
				</td>
				<td class="label">手机：</td>
				<td class="text">
				    <input type="text" id="xs_phone" name="xs_phone" maxlength="20" onkeypress="parent.transferFocus(event,this)"/>
				</td>
			</tr>
			<tr>
				<td class="label">邮箱：</td>
				<td class="text">
				    <input type="text" id="xs_email" name="xs_email" maxlength="30" onkeypress="parent.transferFocus(event,this)"/>
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
