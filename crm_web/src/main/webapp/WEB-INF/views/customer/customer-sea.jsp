<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../base/base-css.jsp" %>
    <style>
        .name-avatar {
            display: inline-block;
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            color: #FFF;
        }

        .bg-female {
            background-color: deeppink;
        }

        .table > tbody > tr:hover {
            cursor: pointer;
        }

        .table > tbody > tr > td {
            vertical-align: middle;
        }

        .star {
            font-size: 20px;
            color: #ff7400;
        }

        #tip {
            font-size: 8px;
            color: yellowgreen;
        }
    </style>
    <title>凯盛软件CRM-公海客户</title>


</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="sea"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">
            <%--搜索功能--%>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">搜索</h3>
                </div>
                <div class="box-body">
                    <form class="form-inline">
                        <input type="text" name="keyword" value="${keyword}" class="form-control"
                               placeholder="姓名 或  联系电话">
                        <button class="btn btn-default"><i class="fa fa-search"></i></button>
                    </form>
                </div>
            </div>

            <%--已经由js的提示框代替
            <c:if test="${not empty message}">
                <div class="alert alert-info">
                        ${message}
                </div>
            </c:if>--%>

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">公海客户 <i id="tip">( 鼠标点击对应客户可跳转至详情页 )</i></h3>
                    <div class="box-tools pull-right">
                        <a id="newSea" href="javascript:;" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增公海客户</a>
                        <a href="/customer/sea/export" class="btn btn-primary btn-sm"><i class="fa fa-file-excel-o"></i>
                            导出Excel</a>
                    </div>
                </div>

                <!--公海客户列表-->
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        <c:if test="${empty pageInfo.list}">
                            <tr>
                                <td colspan="6" class="text-danger">这会儿还没有公海客户哦~~</td>
                            </tr>
                        </c:if>

                        <c:forEach items="${pageInfo.list}" var="customer">
                            <tr class="clickedRow" rel="${customer.id}">
                                <td><span
                                        class="name-avatar ${customer.sex == '女' ? 'bg-female' : ''}">${fn:substring(customer.custName, 0, 1)}</span>
                                </td>
                                <td>${customer.custName}</td>
                                <td>${customer.jobTitle}</td>
                                <td><fmt:formatDate value="${customer.lastContactTime}"/></td>
                                <td class="star">${customer.level}</td>
                                <td><i class="fa fa-phone"></i> ${customer.mobile} <br></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%--分页--%>
                    <ul id="pagination-demo" class="pagination-sm pull-right"></ul>
                </div>
                <!-- /.box-body -->
            </div>

                <!-- 新增公海客户模态框开始-->
            <div class="modal fade" id="newSeaModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">新增公海客户</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" id="addSeaCustomerForm">
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
                                        <option value="">--请选择级别--</option>
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
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="addSeaCustomer">确认</button>
                        </div>
                    </div>
                </div>
            </div>
                <!-- 新增公海客户模态框结束-->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<!-- 分页js -->
<script src="/static/plugins/twbsPagination/jquery.twbsPagination.min.js"></script>
<script>
    $(function () {

        /**
         * 显示提示信息
         *
         */
        if("${message}") {
            layer.msg("${message}");
        }



        /**
         * 分页标签初始化
         */
        $('#pagination-demo').twbsPagination({
            totalPages: ${pageInfo.pages},
            visiblePages: 7,
            first: '首页',
            last: '末页',
            prev: '上一页',
            next: '下一页',
            href: "?p={{number}}&keyword=${keyword}"
        });

        /**
         * 鼠标点击某个客户所在行,跳转到该客户的详情页面
         */
        $('.clickedRow').click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/customer/sea/" + id;
        });

        /**
         * 新增公海客户模态框显示
         */
        $('#newSea').click(function(){
            $('#newSeaModal').modal({
                show:true,
                backdrop:'static'
            });
        });
        /**
         * 确认增加公海客户
         */
        $('#addSeaCustomer').click(function(){
            $('#addSeaCustomerForm').submit();
        });

        /**
         * 新增公海客户表单校验
         */
        $("#addSeaCustomerForm").validate({
            errorElement:'span',
            errorClass:'text-danger',
            rules:{
                custName:{
                    required:true
                },
                sex:{
                    required:true
                },
                mobile:{
                    required:true
                }
            },
            messages:{
                custName:{
                    required:"请输入客户姓名"
                },
                sex:{
                    required:"请选择客户性别"
                },
                mobile:{
                    required:"请输入客户电话号码"
                }
            },
            submitHandler:function(){
                $.post("/customer/sea/new", $('#addSeaCustomerForm').serialize()).done(function (data) {
                    if (data.state == "success") {
                        layer.alert("保存公海客户成功!",function(){

                            window.history.go(0);
                        });
                    } else {
                        layer.alert(data.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });

            }
        });


    });

</script>


</body>
</html>