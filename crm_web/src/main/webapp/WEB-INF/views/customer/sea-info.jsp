<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-公海客户详情</title>
    <%@ include file="../base/base-css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/select2/select2.css">
    <style>
        .td_title {
            font-weight: bold;
            text-align:center;
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
                    <h3 class="box-title">公海客户资料</h3>
                    <c:if test="${not empty message}" >
                        <div class="alert alert-info">${message}</div>
                    </c:if>
                    <div class="box-tools">
                        <a href="/customer/sea" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/sea/edit/${customer.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <button id="transform" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转为我的客户</button>
                        <button id="delBtn" rel="${customer.id}" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
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

                            <tr>
                                <td class="td_title">地址</td>
                                <td>${customer.address}</td>
                                <td class="td_title">性别</td>
                                <td>${customer.sex}</td>
                                <td class="td_title">remainder</td>
                                <td>${customer.remainder}</td>
                            </tr>


                            <tr>
                                <td class="td_title">备注</td>
                                <td colspan="5">${customer.mark}</td>
                            </tr>

                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" class="pull-right">
                       <%-- <fmt:formatDate value="${customer.lastContactTime}" pattern=""--%>
                        创建日期：<fmt:formatDate value="${customer.createTime}" type="date"/> &nbsp;&nbsp;&nbsp;&nbsp;

                            最后修改日期：<fmt:formatDate value="${customer.updateTime}" pattern="yyyy-MM-dd HH:mm"/>

                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排</h3>
                        </div>
                        <div class="box-body">

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

        </section>

    </div>


    <%@ include file="../base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp"%>
<script src="/static/plugins/select2/select2.js"></script>
<script>
    $(function () {

        var id = ${customer.id};
        var custName = "${customer.custName}";

        /**
         * 将公海客户转为自己客户的按钮
         */
        $('#transform').click(function(){
            layer.confirm("确定转为我的客户吗?",function(){
                window.location.href="/customer/sea/transform/" + id;
            });
        });


        /**
         * 删除客户动作
         */
        $("#delBtn").click(function () {
            layer.confirm("确定删除此公海客户吗?",function(){
                window.location.href = "/customer/sea/del/"+id;
            });
        });
    })
</script>

</body>
</html>