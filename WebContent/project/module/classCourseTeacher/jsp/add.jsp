<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/classCourseTeacher/js/add.js"></script>
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
			    <td class="label">教师：</td>
				<td>
					<input id="js_id" name="js_id" class="text" onkeypress="parent.transferFocus(event,this)"/>
				</td>
				<td class="label">课程：</td>
				<td>
					<select id="kc_id" name="kc_id" class="text" onkeypress="parent.transferFocus(event,this)">
					</select>
				</td>
				<td class="label">班级：</td>
				<td>
					<select id="bj_id" name="bj_id" class="text" onkeypress="parent.transferFocus(event,this)">
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
