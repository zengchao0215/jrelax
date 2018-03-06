/**
 * 图表
 */
ns.component("view.charts", {
    import: function (type) {
        switch (type) {
            case 1:
                if (typeof(Chart) === "undefined")
                    ns.requireJS("/framework/plugins/charts/chartjs/Chart.min.js");
                break;
            case 2:
                if (typeof(echarts) === "undefined")
                    ns.requireJS("/framework/plugins/charts/echarts.min.js");
                break;
            case 3:
                if (typeof(Highcharts) === "undefined")
                    ns.requireJS("/framework/plugins/charts/highcharts/highcharts.js");
                break;
            case 4:
                if (!$.plot)
                    ns.requireJS("/framework/plugins/charts/flot/jquery.flot.js");
                break;
        }
    },
    chartjs: {//http://www.chartjs.org
        init: function (el, options) {
            ns.view.charts.import(1);

            var chart = $(el)[0].getContext('2d');
            chart = new Chart(chart, options);

            return chart;
        }
    },
    echarts: {//http://echarts.baidu.com
        init: function (el, options) {
            ns.view.charts.import(2);

            var chart = echarts.init($(el)[0]);
            chart.setOption(options);

            return chart;
        }
    },
    highcharts: {
        init: function (el, options) {
            ns.view.charts.import(3);

            return new Highcharts.Chart(el, options);
        }
    },
    flot: {
        init: function (el, data, options) {
            ns.view.charts.import(4);

            return $.plot(el, data, options);
        }
    }
});