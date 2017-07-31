<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户数据分析</title>
    <%@ include file="../base/base-css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <jsp:include page="../base/base-side.jsp">
        <jsp:param name="active" value="charts_customer"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">


                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">客户等级分析图</h3>
                        </div>
                        <div class="box-body">
                            <div id="customer-level-bar" style="height: 300px;width: 100%"></div>
                        </div>
                    </div>


        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../base/base-footer.jsp" %>

</div>
<!-- ./wrapper -->

<%@include file="../base/base-js.jsp" %>
<script src="/static/plugins/echarts/echarts.min.js"></script>
<script>
    $(function () {
        var customerAnalyzeChart = echarts.init($('#customer-level-bar')[0]);
        customerAnalyzeChart.setOption({
            color:['#1bab08'],
            title:{
                text:'客户等级分析图',
                left:'center'
            },
            legend: {
                data: ['客户等级'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "客户等级",
                type: 'bar',
                data:[]
            }
        });

        $.post("/chart/customer/analyze.json").done(function (resp) {
            if (resp.state == "success") {
                var levelArray = [];
                var countArray = [];

                for(var i=0; i<resp.data.length;i++) {
                    var obj = resp.data[i];
                    levelArray.push(obj.level);
                    countArray.push(obj.count);
                }
                customerAnalyzeChart.setOption({
                    xAxis: {
                        data: levelArray
                    },
                    series: {
                        data: countArray
                    }
                });

            } else {
                layer.msg(resp.message);
            }
        }).error(function () {
            layer.alert("服务器异常 ! ");
        });


    });
</script>

</body>
</html>