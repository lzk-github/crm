<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="../base/base-css.jsp" %>
    <link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="/static/plugins/datatables/dataTables.bootstrap.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="accounts"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <%--隐藏域,保存被点击的部门的ID--%>
                <input type="hidden" id="deptOfclicked" rel="">

            <div class="row">
                <div class="col-md-2">
                    <div class="box">
                        <div class="box-body">
                            <button class="btn btn-default" id="addDept">添加部门</button>
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <!-- Default box -->
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">员工管理</h3>
                            <div class="box-tools pull-right">
                                <button class="btn btn-default" style="display:none;margin-right:30px;" type="button" id="delDeptBtn"></button>
                                <button type="button" class="btn btn-primary" title="Collapse" id="addAcc">
                                    <i class="fa fa-plus"></i> 添加员工
                                </button>
                            </div>
                        </div>
                        <div class="box-body">
                            <table class="table" id="accTable">
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>部门</th>
                                    <th>手机</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 添加员工的模态框开始-->
    <div class="modal fade" id="addAccountModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">模态框标题</h4>
                </div>
                <div class="modal-body">
                    <form id="addAccForm">
                        <div class="form-group">
                            <label>员工姓名</label>
                            <input type="text" class="form-control" name="userName" id="name">
                        </div>
                        <div class="form-group">
                            <label>密码(默认密码:123456)</label>
                            <input type="password" class="form-control" name="password" id="password" value="123456">
                        </div>
                        <div class="form-group">
                            <label>手机号</label>
                            <input type="text" class="form-control" name="mobile" id="mobile">
                        </div>
                        <div class="form-group">
                            <label>部门</label>
                            <div id="deptArea"></div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" id="saveAccountBtn">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!-- 添加员工的模态框结束 -->



    <%@ include file="../base/base-footer.jsp" %>


</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script>
    $(function () {

        /*dataTable显示员工数据*/
        var dataTable = $("#accTable").DataTable({
            paging: false,  //不分页
            "ordering": false,  //不排序
            "searching": false, //不搜索
            "processing": true, //显示数据时的动画
            "serverSide": true, //有服务端
            "ajax": {
                url: "/manage/account/load.json",   //服务器url
                data: function (data) {
                    data.deptId = $("#deptOfclicked").val();
                }
            },
            "columns": [
                {"data": "userName"},
                {
                    "data": function (row) {
                        var result = "";
                        for (var i = 0; i < row.deptList.length; i++) {
                            result += row.deptList[i].deptName + " &nbsp;&nbsp;";
                        }
                        return result;
                    }
                },
                {"data": "mobile"},
                {
                    "data": function (row) {
                        return "<a href='javascript:;' rel='" + row.id + "' class='delLink' rel2="+row.userName+"><i class='fa fa-trash text-danger'></i></a>";
                    }
                }
            ]
            /*改变语言显示
             language: {
             "info": "显示 _START_ 到 _END_ 共 _TOTAL_ 条数据",
             }*/
        });


        <!-- 添加员工按钮 -->
        $('#addAcc').click(function () {
            $.post("/manage/account/depts.json").done(function (data) {
                if (data.length == 0 || data.length == 1) {
                    layer.msg("请先添加部门");
                    return;
                }

                $('#addAccForm')[0].reset();
                $('#deptArea').html("");

                for (var i = 0; i < data.length; i++) {
                    var obj = data[i];
                    var html = '<label  class="checkbox-inline"><input type="checkbox" name="deptId" value="' + obj.id + '"> ' + obj.name + '</label>';
                    if (obj.id != 1) {
                        $('#deptArea').append(html);
                    }
                }

            }).error(function () {
                layer.alert("服务器异常 ! ");
            });

            $('#addAccountModal').modal({
                show: true,
                backdrop: 'static'
            });
        });


        /*ZTree部门树*/
        var setting = {

            data: {
                simpleData: {
                    enable: true
                }
            },

            /*异步向服务端请求数据*/
            async: {
                enable: true,
                url: "/manage/account/depts.json"
            },

            /*回调函数,当点击某个部门时将该部门id存入隐藏域*/
            callback: {
                onClick: function (event, treeId, treeNode, clickFlag) {
                    $('#deptOfclicked').val(treeNode.id);
                    $('#deptOfclicked').attr("rel",treeNode.name);


                    //点击某个部门的同时,显示删除部门按钮
                    if(treeNode.id != 1) {
                        $('#delDeptBtn').html("删除" + treeNode.name).show();
                    }

                    dataTable.ajax.reload();
                }
            }
        };

        /**
         * 点击删除部门按钮触发ajax请求
        */
        $('#delDeptBtn').click(function(){
            var delDeptId = $('#deptOfclicked').val();
            var delDeptName = $('#deptOfclicked').attr("rel");
            layer.confirm("确认删除"+delDeptName+"吗",function(){
                $.post("/manage/account/delDept/" + delDeptId).done(function (data) {
                    if (data.state == "success") {
                        layer.msg("删除" + delDeptName + "成功");
                        $('#delDeptBtn').hide();
                        tree.reAsyncChildNodes(null, "refresh");
                        $("#deptOfclicked").val("1");
                        dataTable.ajax.reload();
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });
            });
        });


        /*初始化部门树结构*/
        var tree = $.fn.zTree.init($("#ztree"), setting);


        //添加部门显示模态框
        $('#addDept').click(function () {
            layer.prompt({'title': '请输入你要添加的部门名称...'}, function (text, index) {
                layer.close(index);
                $.post("/manage/account/dept/new", {"deptName": text, "pId": 1}).done(function (data) {
                    if (data.state == "success") {
                        tree.reAsyncChildNodes(null, "refresh");
                        layer.msg("添加成功!");
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });

            });
        });

        //模态框表单提交
        $('#saveAccountBtn').click(function () {
            $('#addAccForm').submit();
        });
        //模态框表单校验
        $('#addAccForm').validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            rules: {
                "name": {required: true},
                "password": {required: true},
                "mobile": {required: true},
                "deptId": {required: true}
            },
            messages: {
                "name": {required: "请输入员工姓名"},
                "password": {required: "请输入员工密码"},
                "mobile": {required: "请输入员工电话号码"},
                "deptId": {required: "请选择员工部门"}
            },
            submitHandler: function () {
                $.post("/manage/account/new", $('#addAccForm').serialize()).done(function (data) {
                    if (data.state == "success") {
                        $('#addAccountModal').modal('hide');
                        dataTable.ajax.reload();
                        layer.msg("添加员工成功!");
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });

            }
        });

        /*
        * 删除按钮的点击事件,
        * 由于删除按钮是动态产生的,所以要使用事件委托机制
        */
        $(document).delegate(".delLink","click",function(){
            var id = $(this).attr("rel");
            var name = $(this).attr('rel2');
            layer.confirm("确认删除"+name+"吗?",function(){
//                layer.close(index);
                $.post("/manage/account/del/"+id).done(function (data) {
                    if (data.state == "success") {
                        layer.msg("删除成功!");
                        dataTable.ajax.reload();
                    } else {
                        layer.msg(data.message);
                    }
                }).error(function () {
                    layer.alert("服务器异常 ! ");
                });

            });
        });


    });
</script>
</body>
</html>