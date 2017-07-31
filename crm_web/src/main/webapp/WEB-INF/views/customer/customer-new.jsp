<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <jsp:param name="active" value="home"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">新增客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="/customer/new" method="post" id="addCustomerForm">
                        <div class="form-group">
                            <label>姓名 (<strong class="text-danger">*</strong>)</label>
                            <input type="text" class="form-control" name="custName">
                        </div>
                        <div class="form-group">
                            <label>性别 (<strong class="text-danger">*</strong>)</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="sex" value="男" checked> 男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="sex" value="女"> 女
                                </label>
                            </div>

                        </div>
                        <div class="form-group">
                            <label>职位</label>
                            <input type="text" class="form-control" name="jobTitle">
                        </div>
                        <div class="form-group">
                            <label>联系电话 (<strong class="text-danger">*</strong>)</label>
                            <input type="text" class="form-control" name="mobile">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" class="form-control" name="address">
                        </div>
                        <div class="form-group">
                            <label>所属行业</label>
                            <select class="form-control" name="trade">
                                <option value="">--请选择行业--</option>
                                <c:forEach items="${tradeList}" var="trade">
                                    <option value="${trade}">${trade}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select name="source" class="form-control">
                                <option value="">--请选择客户来源--</option>
                                <c:forEach items="${sourceList}" var="source">
                                    <option value="${source}">${source}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>级别</label>
                            <select class="form-control" name="level">
                                <option value=""></option>
                                <option value="★">★</option>
                                <option value="★★">★★</option>
                                <option value="★★★">★★★</option>
                                <option value="★★★★">★★★★</option>
                                <option value="★★★★★">★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" class="form-control" name="mark">
                        </div>
                        <div class="form-group">
                            <label>remainder</label>
                            <input type="text" class="form-control" name="remainder">
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary" id="saveBtn">保存</button>
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
    $(function () {
        $("#saveBtn").click(function () {
            $('#addCustomerForm').submit();
        });

        $("#addCustomerForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                custName: {
                    required: true
                },
                sex: {
                    required: true
                },
                mobile: {
                    required: true
                }
            },
            messages: {
                custName: {
                    required: "请输入客户姓名"
                },
                sex: {
                    required: "请选择客户性别"
                },
                mobile: {
                    required: "请输入客户手机号"
                }
            }
        });

    });

</script>

</body>
</html>