<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Echart Learn</title>
    <script src="https://cdn.jsdelivr.net/npm/echarts@4.9.0/dist/echarts.js" integrity="sha256-ferwUEq7cQcMM2B18rhipCnqqLRq2xOEVco+9n40m3U=" crossorigin="anonymous"></script>
</head>

<body>
<h1>hello world</h1>
<h1 style="color: red">123</h1>
<div id="main" style="width: 2200px;height:1000px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    // 指定图表的配置项和数据
    var option = {
        legend: {
            data: ${market}
        },
        xAxis: {
            type: 'category',
            data: ${timeList}
        },
        yAxis: {
            type: 'value',
            scale:true,
        },
        series: ${series}
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>

</body>
</html>

