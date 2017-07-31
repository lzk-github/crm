<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-新增销售机会</title>
    <%@ include file="../base/base-css.jsp" %>
    <link rel="stylesheet" href="/static/plugins/select2/select2.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="home"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">添加销售机会</h3>

                    <div class="box-tools pull-right">
                        <a href="/sales/my" class="btn btn-info">
                            <i class="fa fa-list"></i> 返回销售机会列表
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <%--form表单开始--%>
                        <form method="post" id="addForm">
                            <div class="form-group">
                                <label>机会名称</label>
                                <input type="text" class="form-control" name="chanceName">
                            </div>
                            <div class="form-group">
                                <label>关联客户</label>
                                <select name="customerId" id="custSel" class="form-control">
                                    <option value="">--请选择关联客户--</option>
                                    <c:forEach items="${customerList}" var="customer">
                                        <option value="${customer.id}">${customer.custName}</option>

                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>机会价值</label>
                                <input type="text" class="form-control" name="worth">
                            </div>
                            <div class="form-group">
                                <label>当前进度</label>
                                <select name="currProgress" class="form-control">
                                    <option value="">--请选择进度--</option>
                                    <c:forEach items="${progressList}" var="progress">
                                        <option value="${progress}">${progress}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>详细内容</label>
                                <textarea name="content" class="form-control"></textarea>
                            </div>
                        </form>
                    <%--form表单结束--%>
                    <button class="btn btn-primary" type="button" id="save">添加</button>

                    <button class="btn btn-default" type="button" id="reset">重置</button>
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

<script src="/static/plugins/select2/select2.js"></script>
<script>
    $(function(){

        /**
         * 客户下拉列表提供搜索功能
         */
        $('#custSel').select2();

        /**
         * 点击确认提交表单
         */
        $('#save').click(function(){
            $('#addForm').submit();
        });
        /**
         * 点击重置,重置表单
         */
        $('#reset').click(function(){
            $('#addForm')[0].reset();
        });

        /**
         * 表单校验
         */
        $("#addForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                chanceName:{
                    required:true
                },
                custId:{
                    required:true
                },
                worth:{
                    required:true,
                    number:true
                },
                currProgress:{
                    required:true
                }
            },
            messages:{
                chanceName:{
                    required:"请输入销售机会名称"
                },
                custId:{
                    required:"请选择销售机会对应客户"
                },
                worth:{
                    required:"请输入销售机会价值",
                    number:"机会价值必须为数字"
                },
                currProgress:{
                    required:"请选择销售机会进度"
                }
            }
        });

    });

</script>


</body>
</html>