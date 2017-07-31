        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-首页</title>
    <%@ include file="base/base-css.jsp"%>
    <style>
        #myhome {
            background-color: #ff87bc;
            position:absolute;
            height:200px;
            width:200px;
            border-radius: 50%;
        }

    </style>

</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="base/base-side.jsp">
        <jsp:param name="active" value="home"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <h3>首页</h3>

            <%--<div id="myhome"><h3 align="center">我是首页哦</h3></div>--%>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="base/base-footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="base/base-js.jsp"%>
<script>
//    just for fun
//    var count = 20;
//    setInterval(function(){
//        $('#myhome').css("margin-top",count + "px");
//        count += 10;
//        if(count > 500) {
//            count -= 20;
//        } else {
//            count += 10;
//        }
//    },100);

</script>
</body>
</html>