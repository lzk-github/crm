<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="../base/base-css.jsp"%>
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
                    <h3 class="box-title">新增待办任务</h3>

                    <div class="box-tools pull-right">
                        <a href="/task/list" type="button" class="btn btn-github">
                            <i class="fa fa-list"></i> 返回列表
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="/task/new" method="post" id="addTaskForm">
                        <div class="form-group">
                            <label>任务名称</label>
                            <input type="text" class="form-control" name="taskName">
                        </div>
                        <div class="form-group">
                            <label>完成日期</label>
                            <input type="text" class="form-control" id="datepicker" name="endTime">
                        </div>
                        <div class="form-group">
                            <label>提醒时间</label>
                            <input type="text" class="form-control" id="datepicker2" name="remindTime">
                        </div>
                    </form>
                </div>
                <!-- /.box-body -->
                <div class="box-footer">
                    <button id="addTaskBtn" class="btn btn-primary">保存</button>
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
<script>
    $(function(){
        //表单提交
        $('#addTaskBtn').click(function(){
            $('#addTaskForm').submit();
        });
        //表单校验
        $("#addTaskForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                taskName:{
                    required:true
                },
                endTime:{
                    required:true
                },
                remindTime:{
                    required:true
                }
            },
            messages:{
                taskName:{
                    required:"请输入任务名称"
                },
                endTime:{
                    required:"请选择任务到期时间"
                },
                remindTime:{
                    required:"请选择任务提醒时间"
                }
            }
        });

        var picker = $('#datepicker').datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            startDate:moment().format("yyyy-MM-dd")
        });
        picker.on("changeDate",function (e) {
            var today = moment().format("YYYY-MM-DD");
            $('#datepicker2').datetimepicker('setStartDate',today);
            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
        });


        var timepicker = $('#datepicker2').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true
        });

    });

</script>


</body>
</html>