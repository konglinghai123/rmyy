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
				<th data-options="field:'drugForm.commonNameContents.extractCommonName',width:150,
						formatter:function(val,row){
							if(row.drugForm.commonNameContents==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.commonNameContents.extractCommonName, row);
							}
						}">药品通用名</th>      
				<th data-options="field:'drugForm.commonNameContents.administrationName',width:100,
						formatter:function(val,row){
							if(row.drugForm.commonNameContents==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.commonNameContents.administrationName, row);
							}
						}">给药途径</th>  	
				<th data-options="field:'drugForm.commonNameContents.pill',width:100,
						formatter:function(val,row){
							if(row.drugForm.commonNameContents==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.commonNameContents.pill, row);
							}
						}">剂型</th> 											                  			    
				<th data-options="field:'drugForm.indicationsEffect',width:200,
						formatter:function(val,row){
							if(row.drugForm==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.indicationsEffect, row);
							}
						}">适应症及药理作用</th>
				<th data-options="field:'drugForm.commonNameContents.drugCategoryInfo',width:100,
						formatter:function(val,row){
							if(row.drugForm.commonNameContents==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.commonNameContents.drugCategoryInfo, row);
							}
						}">中西药类别</th>
				<th data-options="field:'drugForm.departName',width:100,
						formatter:function(val,row){
							if(row.drugForm==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.departName, row);
							}
						}">申报科室</th>
				<th data-options="field:'drugForm.commonNameContents.common.chemicalBigCategory',width:150,
						formatter:function(val,row){
							if(row.drugForm.commonNameContents==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.commonNameContents.common.chemicalBigCategory, row);
							}
						}">化药大类</th>  
				<th data-options="field:'drugForm.commonNameContents.common.chemicalSubCategory',width:150,
						formatter:function(val,row){
							if(row.drugForm.commonNameContents==null){
							 	return '';
							}else{
								return formatTooltip(row.drugForm.commonNameContents.common.chemicalSubCategory, row);
							}
						}">化药小类</th> 						 
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto;">
        <div class="toolbar" style="margin-bottom:2px">
			<font color=blue>最大投票通过数为50</font>&nbsp; &nbsp; 投票流程：<font color=red>新增通用名</font><img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">新增规格/剂型<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">新增通用名厂家<img  width=30 height=15 src="${ctx}/static/image/arrow.jpg">新增剂型/规格厂家   &nbsp; &nbsp; &nbsp; 
			<a id="icon-save" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add',toggle:true" onclick="javascript:saveVote();">保存</a>
			<a id="tb-edit" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit',toggle:true" onclick="javascript:submitVote();">提交</a>
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
			rownumbers:true,
			striped:true,
			border:false,
            singleSelect: true,
            onClickCell: onClickCell,
            onEndEdit: onEndEdit
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

    
    function onEndEdit(index, row){
        var ed = $(this).datagrid('getEditor', {
            index: index,
            field: 'voteTypeInfo'
        });
        row.voteTypeInfo = $(ed.target).combobox('getText');
    }
    
    function saveVote(){
    	onClickCell(0, 'voteTypeInfo');
    	var rows = $('#tt').datagrid('getChanges');
    	if(rows.length==0){
    		$.messager.alert('提示', '没有新改变的数据，无需保存!', 'info');
    		return;
    	}
	    var parameter='',voteTypeValue='';
	    $.each(rows,function(index,row){
	    	if(row.voteTypeInfo=='通过'){
	    		voteTypeValue='pass';
	    	}else if(row.voteTypeInfo=='反对'){
	    		voteTypeValue='oppose';
	    	}else{
	    		voteTypeValue='abstain';
	    	}
	    	parameter += 'selections=' + row.id +'@'+voteTypeValue+'&';
	    });

        $.post('${ctx}/yjk/re/voterecord/savevote', parameter, function (data) {
        	$.messager.alert('提示', data.message, 'info');
        });
    }
    
    function submitVote(){
		    	onClickCell(0, 'voteTypeInfo');
		    	var rows = $('#tt').datagrid('getRows');
			    var parameter='',voteTypeValue='',passTotal=0;
			    $.each(rows,function(index,row){
			    	if(row.voteTypeInfo=='通过'){
			    		voteTypeValue='pass';
			    		passTotal++
			    	}else if(row.voteTypeInfo=='反对'){
			    		voteTypeValue='oppose';
			    	}else{
			    		voteTypeValue='abstain';
			    	}
			    	parameter += 'selections=' + row.id +'@'+voteTypeValue+'&';
			    });
			    if(passTotal>3){
			    	$.messager.alert('提示', '投票通过的总数量超过3，不能提交！', 'info');
			    	return;
			    }
			    
		    	$.messager.confirm('提示', '确定要提交当前投票结果吗？<br/><font color="red">提交后不能再修改投票结果了！！！</font>', function(r) {
					if (r) {
						$.ewcms.addLoading();
				        $.post('${ctx}/yjk/re/voterecord/submitvote', parameter, function (data) {
				        	
				        	if(data.success){
				        		$.ewcms.removeLoading();
				        		alert(data.message);
				        		window.location.reload();
				        	}else{
				        		$.messager.alert('提示', data.message, 'info');
				        	}
				        });
					}
				});	
    }   
</script>