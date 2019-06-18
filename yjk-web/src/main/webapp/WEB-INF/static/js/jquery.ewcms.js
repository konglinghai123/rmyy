(function($){
	$.fn.serializeObject = function() {
		  var arrayData, objectData;
		  arrayData = this.serializeArray();
		  objectData = {};

		  $.each(arrayData, function() {
		    var value;

		    if (this.value != null) {
		      value = this.value;
		    } else {
		      value = '';
		    }

		    if (objectData[this.name] != null) {
		      if (!objectData[this.name].push) {
		        objectData[this.name] = [objectData[this.name]];
		      }

		      objectData[this.name].push(value);
		    } else {
		      objectData[this.name] = value;
		    }
		  });
		  return objectData;
	};
	function hasElementFor(id){
		var ids = $.isArray(id) ? id : [id];
		$.each(ids,function(index,i){
			if($(i).length == 0){
				alert(i + '编号的元素不存在');
				return false;
			}
		});
		return true;
	};
	
	$.extend({
		ewcms:{
			openTab : function (options){
				var defaults = {
					tabsMenu : 'tabsMenu',
					id : '#systemtab',
					src : 'javascript:void(0);',
					refresh : true
				};
				var opts = $.extend({}, defaults, options); 
			    if(!opts.content){
			    	opts.content = '<div class="easyui-layout" data-options="fit:true" style="padding:2px;"><iframe src="' + opts.src + '" width="100%" height="100%" frameborder="0" scrolling="auto"/></div>'; 
			    }
			    if(!hasElementFor(opts.id)){
			    	return ;
			    }
			    var t = $(opts.id);
			    var title = opts.title;
			    
			    t.tabs({
			    	onSelect : function(title){
			    		var theme = $.cookie('theme');
						if (theme == null) theme = 'default';
						var childLink = $('iframe').contents().find('link:first');
						childLink.attr('href', ctx + '/static/easyui/themes/' + theme + '/easyui.css');
			    	},
			    	onContextMenu : function (e, title) {
			            e.preventDefault();
			            $(tabsMenu).menu('show', {
			                left : e.pageX,
			                top : e.pageY
			            }).data({'parentTabId': opts.id,'tabTitle':title});
			        }
			    });
			    
			    if (!t.tabs('exists', title)) {
			        t.tabs('add', {
			            title : title,
			            content : opts.content,
			            closable : true
			        });
			        return;
			    }
			    t.tabs('select', title);
		    	if(opts.refresh){
		  	        t.tabs('update', {
		  	            tab : t.tabs('getTab', title),
		  	            options : {
		  	                content : opts.content
		  	            }
		  	        });
		    	}
			},
			openWindow : function(options){
				var defaults = {
						windowId : '#edit-window', 
						iframeId : '#editifr',
						grid : 'datagrid',
						gridId : '#tt',
						title : '新窗口',
						width : 800,
						height : 500,
						isRefresh : true,
						modal:true,
						maximizable:false,
						minimizable:true,
						closable:true
				};
				var opts = $.extend({}, defaults, options);  
				if(!hasElementFor(opts.windowId)){
					return;
				}
				$(opts.windowId).removeAttr('style');
				$(opts.windowId).window({
					   title: opts.title,
					   width: opts.width,
					   height: opts.height,
					   left:(opts.left ? opts.left : ($(window).width() - opts.width)/2),
					   top:(opts.top ? opts.top : ($(window).height() - opts.height)/2),
					   modal: opts.modal,
					   maximizable:opts.maximizable,
					   shadow: false, 
					   closable: opts.closable,
				       closed: true,
				       minimizable: false,
				       collapsible: false,
				       onOpen : function(){
				    	   if(opts.src){
								opts.iframeId ? $(opts.iframeId).attr('src',opts.src) : $(opts.windowId).find('iframe').attr('src',opts.src);
							}
				       },
				       onBeforeClose:function(){
						   	$(opts.windowId).find('iframe').attr('src','');
					   },
					   onClose:function(){
						   if (opts.isRefresh){
							   if (opts.grid == 'treegrid'){
								   $(opts.gridId).treegrid('clearSelections');
								   $(opts.gridId).treegrid('reload');
							   } else {
								   $(opts.gridId).datagrid('clearSelections');
								   $(opts.gridId).datagrid('reload');
							   }
						   }
				       }
				});
				$(opts.windowId).window('open');
			},
			openTopWindow: function(options){
				var defaults = {
						title : '新窗口',
						width : 800,
						height : 500,
						modal:true,
						maximizable:false,
						minimizable:true,
						isRefresh : true,
						grid : 'datagrid',
						gridId:'#tt'
				};
				var opts = $.extend({}, defaults, options);  
//				if(!hasElementFor(opts.windowId)){
//					return;
//				}
				var win = window.top.document.createElement('div');
				win.setAttribute('id', 'open_window_top');
				win.setAttribute('style', 'padding:0px;overflow:hidden;');
		        window.top.document.body.appendChild(win);
		        var position = options.position ? options.position : '';
		        window.top.$(win).window({
		        	title: opts.title,
					width: opts.width,
					height: opts.height,
					//left:(opts.left ? opts.left : ($(window).width() - opts.width)/2),
					//top:(opts.top ? opts.top : ($(window).height() - opts.height)/2),
					modal: true,
					maximizable:opts.maximizable,
					shadow: false, 
					closable: true,
			        closed: true,
			        minimizable: false,
			        collapsible: false,
			        draggable:true,  
			        zIndex:999,  
			        inline:true,
			        content:function(){
			        	return position + '<iframe scrolling="no" frameborder="0"  src="' + options.src + '" style="width:100%;height:100%;"></iframe>';
			        },
			        onClose:function(){
			        	if (opts.isRefresh){
							   if (opts.grid == 'treegrid'){
								   $(opts.gridId).treegrid('clearSelections');
								   $(opts.gridId).treegrid('reload');
							   } else {
								   $(opts.gridId).datagrid('clearSelections');
								   $(opts.gridId).datagrid('reload');
							   }
						}
			            window.setTimeout(function(){
			            	window.top.$(win).window('destroy', false);
			            },  100);
			            
			        }
		        });
		        window.top.$(win).window('open');
			},
			query : function(options) {
				var defaults = {
						grid : 'datagrid',
						gridId : '#tt',
						formId : '#queryform',
						url: 'query',
						selections : []
				};
				var opts = $.extend({}, defaults, options);
				if(!hasElementFor(opts.gridId)){
					return ;
				}
				if (opts.grid == 'treegrid'){
					$(opts.gridId).treegrid({
						onBeforeLoad:function(param){
							if(opts.selections.length > 0){
								$.each(opts.selections,function(i,v){
									param['selections[' + i + ']'] = v;
								});
							}else{
								//param['parameters']=$(opts.formId).serializeObject();
							}
						}
					});
					//$(opts.gridId).treegrid('reload');
				} else {
					$(opts.gridId).datagrid({
						queryParams: {},
						onBeforeLoad:function(param){
							if(opts.selections.length > 0){
								$.each(opts.selections,function(i,v){
									param['selections[' + i + ']'] = v;
								});
							}else{
								param['parameters']=$(opts.formId).serializeObject();
							}
						}
					});
					//$(opts.gridId).datagrid('reload');
				}
			},
			add : function(options){
				var defaults = {
						src: 'save',
						grid : 'datagrid',
						gridId : '#tt',
					    windowId:'#edit-window'
				};
				var opts = $.extend({}, defaults, options);
				if(!hasElementFor(opts.windowId)){
					return;
				}
				$.ewcms.openWindow(opts);	
			},
			edit : function(options){
				var defaults = {
						src: 'save',
						grid : 'datagrid',
						gridId : '#tt',
						windowId:'#edit-window',
						getId : function(row){
							return row.id;
						}
				};
				var opts = $.extend({}, defaults, options);
				if(!hasElementFor([opts.gridId,opts.windowId])){
					return;
				}
				
			    var rows = $(opts.gridId).datagrid('getSelections');
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择修改记录','info');
			        return;
			    }
			    
			    var src = ((opts.src.indexOf('?') == -1) ? opts.src + '?' : opts.src + '&');
			    $.each(rows,function(index,row){
			    	src += 'selections=' + opts.getId(row) +'&';
			    });
			    opts.src = src;
			    $.ewcms.openWindow(opts);
			},
			remove:function(options){
				var defaults = {
						baseUrl: '',
						src: 'delete',
						grid : 'datagrid',
						gridId : '#tt',
						confirm : '确定要删除所选记录吗?',
						getId : function(row){
							return row.id;
						}
				};
				var opts = $.extend({}, defaults, options);
				if(!hasElementFor([opts.gridId])){
					return;
				}
				
				if (opts.grid == 'treegrid'){
					var selectNode = $(opts.gridId).treegrid('getSelected');
					if (selectNode){
						$.messager.confirm('提示', opts.confirm, function(r){
							if (r){
								$.post(opts.baseUrl + selectNode.id + '/' + opts.src, {}, function(data){
									if (data.success){
				            			$(opts.gridId).treegrid('unselectAll');
			    						$(opts.gridId).treegrid('reload');
				            		}
				            		$.messager.alert('删除', data.message, 'info');
								});
							}
						});
					} else {
						$.messager.alert('提示', '请选择删除记录', 'info');
					}
				} else{
			    	var rows = $(opts.gridId).datagrid('getSelections');
			    	
			    	if(rows.length == 0){
				        $.messager.alert('提示','请选择删除记录','info');
				        return;
				    }
				    
				    var parameter='';
				    $.each(rows,function(index,row){
				    	parameter += 'selections=' + opts.getId(row) +'&';
				    });
				    
				    $.messager.confirm('提示', opts.confirm, function(r){
				        if (r){
				            $.post(opts.src, parameter ,function(data){
				            	if (data.success){
			    					$(opts.gridId).datagrid('clearSelections');
			    					$(opts.gridId).datagrid('reload');
				            	}
		    					$.messager.alert('删除', data.message, 'info');
				            });
				        }
				    });
				}
			},
			status:function(options){
				var defaults = {
						src: 'changeStatus',
						grid : 'datagrid',
						gridId : '#tt',
						prompt : false,
						getId : function(row){
							return row.id;
						}
				};
				
				var opts = $.extend({}, defaults, options);
				if(!hasElementFor([opts.gridId])){
					return;
				}
				
				var rows;
				if (opts.grid == 'treegrid'){
					rows = $(opts.gridId).treegrid('getSelections');
				} else{
			    	rows = $(opts.gridId).datagrid('getSelections');
				}
				
			    if(rows.length == 0){
			        $.messager.alert('提示','请选择要改变为【' + opts.info + '】的记录','info');
			        return;
			    }
			    
			    var parameter='';
			    $.each(rows,function(index,row){
			    	parameter += 'selections=' + opts.getId(row) + '&';
			    });
			    
			    $.messager.confirm('提示', '你确定要对所选记录进行【' + opts.info + '】操作吗？' ,function(r){
			    	if (r) {
			    		if (opts.prompt){
					    	$.messager.prompt('状态', '原因：', function(reason){
					    		if (reason){
					    			parameter += 'reason=' + reason;
					    			$.post(opts.src + '/' + opts.status, parameter, function(data) {
					    				if (data.success){
					    					if (opts.grid == 'treegrid'){
					    						$(opts.gridId).treegrid('reload');
					    					} else {
					    						$(opts.gridId).datagrid('reload');
					    					}
					    				}
					    				$.messager.alert('提示', opts.info + data.message, 'info');
					    			});
					    		}
					    	});
					    } else {
					    	$.post(opts.src + '/' + opts.status, parameter, function(data) {
			    				if (data.success){
			    					if (opts.grid == 'treegrid'){
			    						$(opts.gridId).treegrid('reload');
			    					} else {
			    						$(opts.gridId).datagrid('reload');
			    					}
			    				}
			    				$.messager.alert('提示', opts.info + data.message, 'info');
			    			});
					    }
		    			
		    		}
			    });
			},
			readonlyForm : function(form, removeButton) {
		        var inputs = $(form).find(':input');
		        inputs.not(':submit,:button').prop('readonly', true);
		        if(removeButton) {
		            inputs.remove(':button,:submit');
		            $('.easyui-linkbutton').remove();
		        }
		    },
		    refresh : function(options){
		    	var defaults = {
					grid : 'datagrid',
					gridId : '#tt',
					operate : '',
					data : ''
				};
				
				var opts = $.extend({}, defaults, options);
				
				if (opts.data == null || opts.data == '') return;
				
				if (opts.operate == 'add'){
					if (opts.grid == 'datagrid'){
						parent.$(opts.gridId).datagrid('reload');
					} else if (opts.grid == 'treegrid'){
						parent.$(opts.gridId).treegrid('reload');
					}
				} else if (opts.operate == 'update'){
					var rowData = eval('(' + opts.data + ')');
					if (opts.grid == 'datagrid'){
						var rows = parent.$(opts.gridId).datagrid('getSelections');
						$.each(rows,function(index,row){
					    	if (row.id == rowData.id){
					    		parent.$(opts.gridId).datagrid('updateRow', {
									index : parent.$(opts.gridId).datagrid('getRowIndex', row),
									row : rowData
								});
					    		return;
					    	}
					    });
					} else if (opts.grid == 'treegrid'){
						var rows = parent.$(opts.gridId).treegrid('getSelections');
						$.each(rows,function(index,row){
					    	if (row.id == rowData.id){
					    		parent.$(opts.gridId).treegrid('update', {
									id : row.id,
									row : rowData
								});
					    		return;
					    	}
						});
					}
					var opts = $.extend({}, defaults, options);
				}
		    },
		    autoComplete : function(options){
		    	var defaults = {
		    		minLength : 1,
		    		enterSearch : false,
		    		focus : function(event, ui){
		    			$(config.input).val(ui.item.label);
		    			return false;
		    		},
		    		renderItem : function(ul, item){
		    			return $('<li>').data('ui-autocomplete-item', item).append('<a>' + item.label + '</a>').appendTo(ul);
		    		}
		    	};
		    	
		    	opts = $.extend({}, defaults, options);
		    	
		    	$(opts.input)
		    		.bind('keydown', function(event){
		    			if (opts.enterSearch && event.keyCode == $.ui.keyCode.ENTER){
		    				opts.select(event, {item:{value:$(this).val()}});
		    			}
		    		})
		    		.autocomplete({
		    			source : opts.source,
		    			minLength : opts.minLength,
		    			foucus : opts.focus,
		    			select : opts.select
		    		}).data('ui-autocomplete')._renderItem = opts.renderItem;
		    },
			addLoading :function(){
				$('<div class="datagrid-mask"></div>').css({display:'block',width:'100%',height:$(window).height()}).appendTo('body');
				$('<div class="datagrid-mask-msg" style="height:40px"></div>').html('正在处理，请稍候。。。').appendTo('body').css({display:'block',left:(($(document.body).outerWidth(true) - 190) / 2),top:(($(window).height() - 45) / 2)}); 
			},
			removeLoading : function(){
				$('.datagrid-mask').remove();
				$('.datagrid-mask-msg').remove();
			},
			moreQuery:function(options){
				var defaults = {
						grid:'datagrid',
						gridId:'#tt'
				};
				var opts = $.extend({}, defaults, options);  
				
			    var showHideLabel_value = $('#showHideLabel').text();
			    $('form table tr').next('tr').toggle();
			    if (showHideLabel_value == '收缩'){
			    	$('#showHideLabel').text('更多...');
			    }else{
			    	$('#showHideLabel').text('收缩');
			    }
			    
				if (opts.grid == 'datagrid'){
					$(opts.gridId).datagrid('resize');
				} else if (opts.grid == 'treegrid'){
					$(opts.gridId).treegrid('resize');
				}
			}
		}
	});
})(jQuery);

/*
 * jeasyui中的datagrid的行数宽度自动伸缩
 */
$.extend($.fn.datagrid.methods, {
    fixRownumber : function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position" : "absolute",
                left : -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //var width = clone.width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
                $(this).datagrid("resize");
            }
        });
    }
});