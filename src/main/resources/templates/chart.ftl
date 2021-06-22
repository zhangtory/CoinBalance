<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Echart Learn</title>
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.1.2/dist/echarts.min.js"></script>
</head>

<body>

<h1>Coin Balance Record</h1>
<h1 style="color: red">总资产:</h1>
<div id="sumChart" style="width: 1500px;height:600px;"></div>


<script type="text/javascript">
    // 总资产折线图
    var sumChart = echarts.init(document.getElementById('sumChart'));
    var sumOption = {
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ${timeList}
        },
        yAxis: {
            type: 'value',
            scale: true,
        },
        series: [{
            data: ${sumChartData},
            type: 'line',
            smooth: true
        }]
    };
    sumChart.setOption(sumOption);
</script>

</body>
</html>

