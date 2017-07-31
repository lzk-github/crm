<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-公司网盘</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
    <style>
        tr{

            height: 50px;
            line-height: 50px;
        }
        .table>tbody>tr>td{
            vertical-align: middle;
        }
        .file_icon {
            font-size: 30px;
            text-align: center;
        }
        .table>tbody>tr:hover{
            cursor: pointer;
        }
        .webuploader-container {
            display: inline-block;
        }
        .webuploader-pick {
            padding: 5px 10px;
            overflow: visible;
            font-size: 12px;
            line-height:1.5;
            font-weight: 400;
        }

    </style>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="disk"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">${parentDisk.name}</h3>

                    <div class="box-tools pull-right">
                        <c:if test="${not empty parentDisk}">
                            <a href="/disk/list?pid=${parentDisk.pid}" class="btn btn-default btn-sm"><i class="fa fa-arrow-left"></i> 返回上级</a>
                        </c:if>
                        <div id="picker"><i class="fa fa-upload"></i> 上传文件</div>
                        <button id="newFolderBtn" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新建文件夹</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover" >
                        <thead>
                            <tr class="bg-danger">
                                <td>类型</td>
                                <td>文件名</td>
                                <td>最后更新时间</td>
                                <td>文件大小</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody id="dataTable">

                        <c:if test="${empty diskList}">
                            <tr><td colspan="4">暂无内容</td></tr>
                        </c:if>
                        <c:forEach items="${diskList}" var="disk">
                            <tr class="${disk.type == 'dir' ? 'tr' : ''}" rel="${disk.id}">
                                <td width="50" class="file_icon">
                                    <c:choose>
                                        <c:when test="${disk.type == 'dir'}">
                                            <i class="fa fa-folder-o"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-file-o"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td width="300">${disk.name}</td>
                                <td><fmt:formatDate value="${disk.updateTime}" pattern="YYYY-MM-dd"/> </td>
                                <td width="200">
                                    <c:if test="${disk.type == 'file'}">
                                        ${disk.fileSize}
                                    </c:if>
                                </td>
                                <td width="200">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                            <i class="fa fa-ellipsis-h"></i>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li><a href="/disk/list?pid=${disk.id}">打开</a></li>
                                            <li><a class="rename" rel="${disk.id}">重命名</a></li>
                                            <li><a class="del" rel="${disk.id}">删除</a></li>
                                        </ul>
                                        <c:if test="${disk.type == 'file'}">
                                            <button class="btn btn-success download" rel="${disk.id}"><i class="fa fa-download"></i></button>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>

            </div>
            <!-- /.box -->


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/art-template/art-template.js"></script>
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<%--定义模板--%>
<script type="text/template" id="trTemplate">

    <tr class="
    <? if (type == 'dir') { ?>
        tr
    <? } ?> "
    rel="{{id}}">
        <td width="50" class="file_icon">

            <? if (type == 'dir') { ?>
                <i class="fa fa-folder-o"></i>
            <? } else { ?>
                <i class="fa fa-file-o"></i>
            <? } ?>

        </td>
        <td width="300">{{name}}</td>
        <td>{{updateTime}}</td>
        <td width="200">

            <? if(type == 'file') { ?>
                {{fileSize}}
            <? } ?>

        </td>
        <td width="200">
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-ellipsis-h"></i>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="/disk/list?pid={{id}}">打开</a></li>
                    <li><a class="rename" rel="{{id}}">重命名</a></li>
                    <li><a class="del" rel={{id}}>删除</a></li>
                </ul>

                <? if(type == 'file') { ?>
                    <button class="btn btn-success download" rel="{{id}}"><i class="fa fa-download"></i></button>
                <? } ?>

            </div>
        </td>
    </tr>
</script>

<script>
    $(function(){
        //获取当前页面的父路径对应的id
        var parId = "${not empty parentDisk ? parentDisk.id : 0}";
        var accountId = ${sessionScope.curr_acc.id};

        //重命名事件
        $(document).delegate('.rename','click',function(event){
            var id = $(this).attr("rel");
            layer.prompt({title:'请输入新名字'},function(text,index){
                //发起ajax请求,改变文件/夹名字
                $.post("/disk/rename", {"id":id,"newName":text}).done(function (resp) {
                    if (resp.state == "success") {

                        $('#dataTable').html("");
                        //2, 再将新数据追加至表格
                        for(var i=0; i<resp.data.length; i++) {
                            var lineData = resp.data[i];
                            lineData.updateTime = moment(lineData.updateTime).format("YYYY-MM-DD");
                            var html = template("trTemplate",lineData);
                            $('#dataTable').append(html);
                        }
                        //提示重命名文件夹成功
                        layer.msg("重命名成功");


                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });
                layer.close(index);
            });

            event.stopPropagation();
        });

        //删除按钮点击动作,开始
        $(document).delegate('.del','click',function(event){
            var id = $(this).attr("rel");
            layer.confirm("确认删除吗?",function(index){
                //发起ajax请求,执行删除
                $.post("/disk/del", {"id":id}).done(function (resp) {
                    if (resp.state == "success") {
                        $('#dataTable').html("");
                        //2, 再将新数据追加至表格
                        for(var i=0; i<resp.data.length; i++) {
                            var lineData = resp.data[i];
                            lineData.updateTime = moment(lineData.updateTime).format("YYYY-MM-DD");
                            var html = template("trTemplate",lineData);
                            $('#dataTable').append(html);
                        }
                        //提示删除成功
                        layer.msg("删除成功");
                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });
                layer.close(index);
            });

            event.stopPropagation();
        });
        //删除按钮动作结束

        //下载动作事件的触发
        $(document).delegate('.download','click',function(event){
            var id = $(this).attr("rel");

            window.location.href = "/disk/download?id=" + id;

            //阻止事件冒泡
            event.stopPropagation();
        });

        //点击每一行文件夹,进入对应的子目录
        $(document).delegate(".tr",'click',function(){
            var id = $(this).attr("rel");
            window.location.href="/disk/list?pid=" + id;
        });

        //新建文件夹按钮点击事件,当点击确认后发起ajax请求,新增文件夹
        $('#newFolderBtn').click(function(){
            layer.prompt({title:"请输入文件夹名称"},function(text,index){

                //关闭弹出层
                layer.close(index);

                //发起新建文件夹的ajax请求
                $.post("/disk/new/folder", {"parId":parId,"folderName":text,"accountId":accountId}).done(function (resp) {
                    if (resp.state == "success") {

                        //异步刷新页面的表格,显示所有的文件(包括新增的文件夹)
                        //1,先清空表格
                        $('#dataTable').html("");
                        //2, 再将新数据追加至表格
                        for(var i=0; i<resp.data.length; i++) {
                            var lineData = resp.data[i];
                            lineData.updateTime = moment(lineData.updateTime).format("YYYY-MM-DD");
                            var html = template("trTemplate",lineData);
                            $('#dataTable').append(html);
                        }

                        //提示新建文件夹成功
                        layer.msg("新建文件夹成功");
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });


            });
        });



        //使用webuploader文件上传
        var uploader = WebUploader.create({
            pick:"#picker",
            swf:'/static/plugins/webuploader/Uploader.swf',
            server:'/disk/upload', //上传服务器
            auto:true, //自动上传
            fileVal:'file',
            formData:{  //文件上传额外的数据
                "parId":parId,
                "accountId":accountId
            }
        });

        //定义上传显示的动作图标
        var loadIndex = -1;
        uploader.on('uploadStart',function (file) {
            loadIndex = layer.load(0);
        });

        //上传成功
        uploader.on('uploadSuccess',function(file,resp){

            $('#dataTable').html("");
            //2, 再将新数据追加至表格
            for(var i=0; i<resp.data.length; i++) {
                var lineData = resp.data[i];
                lineData.updateTime = moment(lineData.updateTime).format("YYYY-MM-DD");
                var html = template("trTemplate",lineData);
                $('#dataTable').append(html);
            }

            //提示新建文件成功
            layer.msg("上传文件成功");
        });

        //上传失败
        uploader.on('uploadError',function(){
            layer.msg("哎呀,上船失败了~~~~")
        });
        //无论上传成功还是失败都会执行的动作
        uploader.on('uploadComplete',function(){
            layer.close(loadIndex);
        });

    });

</script>
</body>
</html>