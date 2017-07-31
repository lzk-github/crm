<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="../base/base-css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="task"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">计划任务</h3>
                    <c:if test="${not empty message}">
                        <hr>
                        <div class="alert alert-info">${message}</div>
                        <hr>
                    </c:if>
                    <div class="box-tools pull-right">
                        <a href="/task/new" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增任务</a>
                        <button id="showAll" class="btn btn-primary btn-sm"><i class="fa fa-eye"></i> 显示所有任务</button>
                    </div>
                </div>
                <div class="box-body">

                    <ul class="todo-list not-done-list" >

                        <c:forEach items="${taskList}" var="task" >

                            <li class="${task.state == 1 ? 'done' : ''}" ${task.state == 1 ? 'style="display:none;"' : ''} >
                                <input  value="${task.id}" type="checkbox" class="clickForCheck" ${task.state == 1 ? 'checked' : ''} />
                                <span class="text">${task.taskName}</span>
                                <c:if test="${not empty task.custName}">
                                    <a href=""><i class="fa fa-user-o"></i> ${task.custName}</a>
                                </c:if>
                                <small class="label label-danger"><i class="fa fa-clock-o"></i>${task.endTime}</small>
                                <div class="tools">
                                    <a href="/task/edit/${task.id}?accountId=${sessionScope.curr_acc.id}" class="fa fa-edit"></a>
                                    <i class="fa fa-trash-o delTask" rel="${task.id}"></i>
                                </div>
                            </li>

                        </c:forEach>

                    </ul>


                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<script>
    $(function(){
        //显示所有任务 + 仅显示未完成任务
        $('#showAll').click(function(){
            var html = $(this).html();
            if(html == "<i class=\"fa fa-adjust\"></i> 仅显示已完成") {
                $('.done').css("display","none");
                $('#showAll').html('<i class="fa fa-eye"></i> 显示所有任务');
            } else {
                $('li').show();
                $('#showAll').html('<i class="fa fa-adjust"></i> 仅显示已完成');
            }
        });
        //需求,
        // 1. 当点击checkbox框时,发起ajax请求,改变任务的状态
        // 2. 传值,是哪个任务?要传任务id; 改变被点击任务的状态
        $('.clickForCheck').click(function(){
            var taskId = $(this).val();
            var accountId = "${sessionScope.curr_acc.id}";
            $.post("/task/state/change", {"taskId":taskId,"accountId":accountId}).done(function (data) {
                if (data.state == "success") {
                    window.history.go(0);
                } else {
                    layer.msg(data.message);
                }
            }).error(function () {
                layer.alert("服务器异常 ! ");
            });

        });

        /**
         * 点击删除图标动作
         */
        $('.delTask').click(function(){
            var taskId = $(this).attr("rel");
            layer.confirm("确认删除该任务吗?",function(){
                window.location.href="/task/del/" + taskId;
            });
        });



    });

</script>



</body>
</html>