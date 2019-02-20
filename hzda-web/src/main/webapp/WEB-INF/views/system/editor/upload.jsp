<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/jspf/taglibs.jspf" %>

<ewcms:head title="上传 - 在线编辑"/>
	<%@ include file="/WEB-INF/views/jspf/import-fileupload-css.jspf" %>
	<div id="edit-form" class="easyui-layout" data-options="fit:true,border:false">
		<ewcms:showAlertMessage/>
		<div data-options="region:'center',fit:true,border:false">	
			<form:form id="fileupload" method="post" cssClass="form-horizontal" enctype="multipart/form-data">
	  			<h1 class="title">当前位置：<span style="color:red;">${empty parentPath ? '根' : parentPath}</span></h1>
	  			<h1 class="title">冲突时：<input type="radio" name="conflict" value="override">覆盖<input type="radio" name="conflict" value="ignore" checked="checked">跳过</h1>
	  			<input type="hidden" id="parentPath" name="parentPath" value="${parentPath}">
	  			<fieldset>
					<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
			        <div class="row fileupload-buttonbar">
			            <div class="col-lg-7">
			                <!-- The fileinput-button span is used to style the file input field as button -->
			                <span class="btn btn-success fileinput-button">
			                    <i class="glyphicon glyphicon-plus"></i>
			                    <span>添加文件...</span>
			                    <input type="file" name="files[]" multiple>
			                </span>
			                <button type="submit" class="btn btn-primary start">
			                    <i class="glyphicon glyphicon-upload"></i>
			                    <span>开始上传</span>
			                </button>
			                <button type="reset" class="btn btn-warning cancel">
			                    <i class="glyphicon glyphicon-ban-circle"></i>
			                    <span>取消上传</span>
			                </button>
			                <button type="button" class="btn btn-danger delete">
			                    <i class="glyphicon glyphicon-trash"></i>
			                    <span>删除</span>
			                </button>
			                <input type="checkbox" class="toggle">
			                <!-- The global file processing state -->
			                <span class="fileupload-process"></span>
			            </div>
			            <!-- The global progress state -->
			            <div class="col-lg-5 fileupload-progress fade">
			                <!-- The global progress bar -->
			                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
			                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
			                </div>
			                <!-- The extended global progress state -->
			                <div class="progress-extended">&nbsp;</div>
			            </div>
			        </div>
			        <!-- The table listing the files available for upload/download -->
			        <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
		  		</fieldset>
			</form:form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;height:30px">
	  		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onclick="javascript:parent.$('#edit-window').window('close');">关闭</a>
		</div>
	</div>
<ewcms:footer/>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">正在处理中...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>开始</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">错误</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>删除</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<%@ include file="/WEB-INF/views/jspf/import-fileupload-js.jspf" %>
<script type="text/javascript">
    $(function () {
        'use strict';
        // Initialize the jQuery File Upload widget:
        $('#fileupload').fileupload({
            url: '${ctx}/system/editor/upload',
            maxFileSize: 20000000, //20MB
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator && navigator.userAgent),
            acceptFileTypes: /(\.|\/)(bmp|gif|jpe?g|png|pdf|docx?|xlsx?|pptx|zip|rar|jsp|jspx|tag|tld|xml|java|html|css|js)$/i
        });
        // Enable iframe cross-domain access via redirect option:
        $('#fileupload').fileupload(
                'option',
                'redirect',
                window.location.href.replace(
                        /\/[^\/]*$/,
                        '/cors/result.html?%s'
                )
        );
        $('#fileupload').bind('fileuploadsubmit', function (e, data) {
            var conflict = $('[name=conflict]:checked').val();
            var parentPath = $('[name=parentPath]').val();
            data.formData = {conflict: conflict, parentPath : parentPath};
        });
    });
</script>