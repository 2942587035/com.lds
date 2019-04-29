<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/teacher/js/edit.js"></script>
<div class="easyui-panel">
	<form>
		<table>
			<tr>
				<td rowspan="7">
				    <img name="img" src="<%=request.getContextPath()%>/project/module/student/img/21.jpg" style="width:120px;height:150px"/>
				</td>
			</tr>
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
				<input type="text" name="js_id" />
				</td>
			</tr>
			<tr>
				<td class="label">*编码：</td>
				    <td class="text"><input type="text" class="disableInputMethod" name="js_code" 
				    maxlength="8" required="true" readonly="readonly"
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">*名称：</td>
				    <td class="text"><input type="text" name="js_name" maxlength="10" required="true" 
				    onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">*性别：</td>
				<td>
				    <select name="js_sex" class="text" onkeypress="parent.transferFocus(event,this)">
						<option value="1">男</option>
						<option value="2">女</option>
						<option value="3">其他</option>
				    </select>
				</td>
			</tr>
			<tr>
				<td class="label">手机：</td>
				<td class="text">
				    <input type="text" name="js_phone" maxlength="20" onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">邮箱：</td>
				<td class="text">
				    <input type="text" name="js_email" maxlength="30" onkeypress="parent.transferFocus(event,this)"/>
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
	<form name="imgForm" action="<%=request.getContextPath()%>/UploadImgStudentServlet" method="post" enctype="multipart/form-data">
	  <input type="file" name="fileUpload" accept="image/png,image/gif,image/jpg" onchange="editSaveImg(this)"/>
	  <input type="submit" name="submit">
	</form>
</div>
