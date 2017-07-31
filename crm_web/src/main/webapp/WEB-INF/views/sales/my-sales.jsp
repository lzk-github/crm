<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-我的销售机会</title>
    <%@ include file="../base/base-css.jsp" %>

    <style>

        .everyline:hover {
            cursor:pointer;
        }

    </style>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="mysale"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会
                        <small class="bg-red"><u>( 点击下面各行可进入详情页 )</u></small>
                    </h3>
                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>
                    <div class="box-tools pull-right">
                        <a href="/sales/add" class="btn btn-yahoo">
                            <i class="fa fa-plus"></i> 添加机会
                        </a>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table table-hover">
                        <thead>
                            <tr class="bg-green-active">
                                <td>机会名称</td>
                                <td>关联客户</td>
                                <td>机会价值</td>
                                <td>当前进度</td>
                                <td>最后跟进时间</td>
                            </tr>
                        </thead>
                        <c:if test="${empty pageInfo.list}">
                            <div class="alert alert-warning">你暂时还没有销售机会哦,加油~</div>
                        </c:if>
                        <tbody>
                            <c:forEach items="${pageInfo.list}" var="chance">
                                <tr class="everyline" rel="${chance.id}">
                                    <td>${chance.chanceName}</td>
                                    <td>${chance.customer.custName}</td>
                                    <td><fmt:formatNumber value="${chance.worth}"></fmt:formatNumber> </td>
                                    <td>${chance.currProgress}</td>
                                    <td><fmt:formatDate value="${chance.lastContactTime}" type="both"></fmt:formatDate></td>
                                </tr>
                            </c:forEach>

                        </tbody>

                    </table>
                    <ul id="pagination-demo" class="pagination-sm pull-right"></ul>
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
<script src="/static/plugins/twbsPagination/jquery.twbsPagination.min.js"></script>
<script>
    /**
     * 显示提示性文字
     */
    if("${message}") {
        layer.msg("${message}");
    }

    /**
     * 分页初始化
     */
    $('#pagination-demo').twbsPagination({
        totalPages: ${pageInfo.pages},
        visiblePages: 7,
        first:'首页',
        last:'末页',
        prev:'上一页',
        next:'下一页',
        href:"?p={{number}}"
    });

    /**
     * 点击行,可以跳转至详情页
     */
    $('.everyline').click(function(){
        var id = $(this).attr("rel");
        window.location.href="/sales/info/" + id;
    });


</script>

</body>
</html>