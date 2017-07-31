<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户详情</title>
    <%@ include file="../base/base-css.jsp" %>
    <link rel="stylesheet" href="/static/plugins/select2/select2.css">
    <style>
        .td_title {
            font-weight: bold;
            text-align: center;
            /*color: white;*/
            background-color: rgba(102, 184, 203, 0.76);
        }

        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="customer_my"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!--隐藏域,保存客户姓名-->
        <input type="hidden" id="forCustName" value="${customer.custName}">
        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>
                    <div class="box-tools">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/my/edit/${customer.id}" class="btn bg-purple btn-sm"><i
                                class="fa fa-pencil"></i> 编辑</a>
                        <button id="transform" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</button>
                        <button id="pullToSea" rel="${customer.id}" class="btn bg-maroon btn-sm"><i
                                class="fa fa-recycle"></i> 放入公海
                        </button>
                        <button id="delBtn" rel="${customer.id}" class="btn btn-danger btn-sm"><i
                                class="fa fa-trash-o"></i> 删除
                        </button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-bordered bg-info" style="margin-top:20px;">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td class="star">${customer.level}</td>
                        </tr>
                        <c:if test="${not empty customer.address}">
                            <tr>
                                <td class="td_title">地址</td>
                                <td>${customer.address}</td>
                                <td class="td_title">性别</td>
                                <td>${customer.sex}</td>
                                <td class="td_title">remainder</td>
                                <td>${customer.remainder}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customer.mark}">
                            <tr>
                                <td class="td_title">备注</td>
                                <td colspan="5">${customer.mark}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                       <%-- <fmt:formatDate value="${customer.lastContactTime}" pattern=""--%>
                        创建日期：<fmt:formatDate value="${customer.createTime}" type="date"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${not empty customer.updateTime}">
                            最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="yyyy-MM-dd HH:mm"/>
                        </c:if>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">销售机会</h3>
                        </div>
                        <div class="box-body">
                            <c:if test="${empty chanceList}">
                                <div class="list-group-item">暂无销售机会</div>
                            </c:if>
                            <ul class="list-group">
                                <c:forEach items="${chanceList}" var="chance">
                                    <li class="list-group-item"><a href="/sales/info/${chance.id}"
                                                                   target="_blank"><i class="fa fa-money"></i> ${chance.chanceName}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">

                    <%--客户信息二维码--%>
                    <div class="box">
                        <h4>客户二维码</h4>
                        <div class="box-body" style="text-align: center">
                            <img src="/customer/my/qrcode/${customer.id}" alt="">
                        </div>
                    </div>

                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                            <button id="addTaskBtn" class="btn btn-success btn-xs pull-right"><i class="fa fa-plus"></i>
                            </button>
                        </div>
                        <div class="box-body">
                            <ul class="todo-list">

                                <ul class="todo-list">
                                    <c:forEach items="${taskList}" var="task">
                                        <c:if test="${task.state == 0}">
                                            <li>
                                                <input type="checkbox">
                                                <span class="text">${task.taskName}</span>
                                                <a href=""><i class="fa fa-user-o"></i> ${task.custName}</a>
                                                <small class="label label-danger"><i
                                                        class="fa fa-clock-o"></i> ${task.endTime}</small>
                                                <div class="tools">
                                                    <i class="fa fa-edit"></i>
                                                    <i class="fa fa-trash-o"></i>
                                                </div>
                                            </li>
                                        </c:if>

                                    </c:forEach>
                                </ul>

                            </ul>

                        </div>
                    </div>

                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>

            <%------------------------------转交他人模态框开始--------------------%>
            <div class="modal fade" id="accountModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">选择转入员工</h4>
                        </div>
                        <div class="modal-body">
                            <select id="accountId" style="width: 100%">
                                <option value="">--请选择转交人--</option>
                                <c:forEach items="${accountList}" var="account">
                                    <option value="${account.id}">${account.userName} ( ${account.mobile} )</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="tranBtnOk">转交</button>
                        </div>
                    </div>
                </div>
            </div>
            <%--------------------------------转交他人模态框结束-----------------------------%>

            <%-------------------------------增加日程安排模态框开始-----------------------------%>
            <div class="modal fade" id="addTaskModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">&times;</span></button>
                            <h5 class="modal-title"><i class="fa fa-calendar-plus-o"></i> 增加对此客户日程安排</h5>
                        </div>
                        <div class="modal-body">
                            <form action="/customer/task/new" method="post" id="addTaskForm">
                                <input type="hidden" name="customerId" value="${customer.id}"/>
                                <input type="hidden" name="accountId" value="${sessionScope.curr_acc.id}">
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
                                <div class="form-group">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button class="btn btn-success" id="taskOkBtn">确定</button>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>

            <%--------------------------------增加日程安排模态框结束-----------------------------%>


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<script src="/static/plugins/select2/select2.js"></script>
<script>
    $(function () {

        var id = ${customer.id};
        var custName = "${customer.custName}";

        /**
         * 删除客户动作
         */
        $("#delBtn").click(function () {
            layer.confirm("删除客户? 确定吗?", function () {
                window.location.href = "/customer/my/del/" + id;
            });
        });

        /**
         * 放入公海动作
         */
        $('#pullToSea').click(function () {

            layer.confirm("是否将" + custName + "放入公海?", function () {
                window.location.href = "/customer/my/pullToSea/" + id;
            });

        });

        /**
         * 转交他人模态框显示
         */
        $('#transform').click(function () {
            $('#accountModal').modal({
                show: true,
                backdrop: 'static'
            });
        });
        /**
         * 转交动作
         */
        $('#tranBtnOk').click(function () {
            var accountId = $('#accountId').val();
            if (!accountId) {
                layer.msg("请选择转交人!");
                return;
            }
            window.location.href = "/customer/my/" + id + "/transform/" + accountId;
        });

        /**
         * select2插件
         */
        $('#accountId').select2();

        /**
         * 显示增加此客户日程安排的模态框
         */
        $('#addTaskBtn').click(function () {
            $('#addTaskModal').modal();
        });

        /**
         * 增加此客户日程安排的表单提交
         */
        $('#taskOkBtn').click(function () {
            $('#addTaskForm').submit();
        });
        /**
         * 增加客户日程安排的表单校验
         */
        $("#addTaskForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                taskName: {
                    required: true
                },
                endTime: {
                    required: true
                },
                remindTime: {
                    required: true
                }
            },
            messages: {
                taskName: {
                    required: "请输入任务名称"
                },
                endTime: {
                    required: "请输入任务结束日期"
                },
                remindTime: {
                    required: "请输入任务提醒时间"
                }
            }
        });


        /**
         * 日程安排的日期格式设置
         */
        var picker = $('#datepicker').datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true,
            startDate: moment().format("yyyy-MM-dd")
        });
        picker.on("changeDate", function (e) {
            var today = moment().format("YYYY-MM-DD");
            $('#datepicker2').datetimepicker('setStartDate', today);
            $('#datepicker2').datetimepicker('setEndDate', e.format('yyyy-mm-dd'));
        });


        var timepicker = $('#datepicker2').datetimepicker({
            format: "yyyy-mm-dd hh:ii",
            language: "zh-CN",
            autoclose: true,
            todayHighlight: true
        });

    })
</script>

</body>
</html>