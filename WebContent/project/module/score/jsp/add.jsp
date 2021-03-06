<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/project/module/score/js/add.js"></script>
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
				<input type="text" class="disableInputMethod" id="xx_code" name="xx_code" maxlength="6" required="true" />
				</td>
				<td class="label">名称：</td>
				<td class="text">
				<input type="text" id="xx_name" name="xx_name" maxlength="20" required="true" />
				</td>
				<td class="label">类型：</td>
				<td>
				<select id="xx_type" name="xx_type" class="text">
						<option value="1">小学</option>
						<option value="2">初中</option>
						<option value="3">高中</option>
				</select>
				</td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td class="text" colspan="5">
				<input type="text" id="xx_remark" name="xx_remark" maxlength="255" />
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
