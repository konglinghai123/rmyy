<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="专家评审投票"/>
	<table id="tt">
		<thead>
			<tr>
			    <th data-options="field:'id',hidden:true">编号</th>
                <th data-options="field:'voteTypeInfo',width:100,
                        editor:{
                            type:'combobox',
                            options:{
                                valueField:'value',
                                textField:'text',
                                panelHeight:100,
                                data:[{ 'value': 'pass', 'text': '通过' }, { 'value': 'oppose', 'text': '反对' }, { 'value': 'abstain', 'text': '弃权' }],
                                required:true
                            }
                        }">投票操作</th>			    
				<th data-options="field:'drugForm.indicationsEffect',width:200,
						formatter:function(val,row){
							return row.drugForm==null?'':row.drugForm.indicationsEffect;
						}">适应症及药理作用</th>
				<th data-options="field:'drugForm.declareReason',width:100,
						formatter:function(val,row){
							return row.drugForm==null?'':row.drugForm.declareReason;
						}">申请理由</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			投票流程：<font color=red>新增通用名</font><img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">新增规格/剂型<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">新增通用名厂家<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">新增剂型/规格厂家   &nbsp; &nbsp; &nbsp; 
			<a id="icon-save" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="">保存</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="">提交</a>
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<font color=blue>最大投票通过数为50</font>
		</div>
	</div>
	<ewcms:editWindow/>
<ewcms:footer/>
<script type="text/javascript">
	$(function(){
		$('#tt').datagrid({
			url:'${ctx}/yjk/re/voterecord/${reviewProcessId}/query',
			toolbar:'#tb',
			fit:true,
			nowrap:true,
			striped:true,
			border:false,
            singleSelect: true,
            onClickCell: onClickCell,
		});
	});

	function formatTooltip(val, row){
		return val != null ? '<span title="' + val + '" class="easyui-tooltip">' + val + '</span>' : '';
	}
	
    var editIndex = undefined;
    function endEditing(){
        if (editIndex == undefined){return true}
        if ($('#tt').datagrid('validateRow', editIndex)){
            $('#tt').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }
    function onClickCell(index, field){
        if (editIndex != index){
            if (endEditing()){
                $('#tt').datagrid('selectRow', index)
                        .datagrid('beginEdit', index);
                var ed = $('#tt').datagrid('getEditor', {index:index,field:field});
                if (ed){
                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                }
                editIndex = index;
            } else {
                setTimeout(function(){
                    $('#tt').datagrid('selectRow', editIndex);
                },0);
            }
        }
    }
	
</script>