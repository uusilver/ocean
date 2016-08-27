/**
 * @author: junying.li
 * @date: 2016/8/27
 * to 杨洋 free for use and have fun
 * 下次记得帮我点两个大生蚝 补补
 */
$(function () {

    Highcharts.setOptions({
        lang: {
            drillUpText: "返回 > {series.name}"
        }
    });

    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']), small = $('#container').width() < 400;

    //
    $.each(data, function (i) {
        this.drilldown = this.properties['drill-key'];
        console.log("Province name:"+this.properties['hc-key']); //获取cn_name.js里的hc-key属性,作为ajax请求的key
        //TODO ajax load data and change this value
        this.value = i; //必须有，当前为随机数，map会根据值的不同来计算图的深浅，且会更新右侧的柱状栏
    });

    //必须有，不然地图无法点击进入，获取市信息
    function getPoint(e) {
        console.log(e.point.name);
    }

    //初始化地图
    $('#container').highcharts('Map', {

        chart: {
            spacingBottom: 30,

            events: {
                drilldown: function (e) {
                    if (!e.seriesOptions) {
                        var chart = this;
                        var cname = e.point.properties["cn-name"];
                        chart.showLoading('<i class="icon-spinner icon-spin icon-3x"></i>');
                        // 加载城市数据
                        $.ajax({
                            type: "GET",
                            //数据从highchart获得，当前路径下../json下，保存了一份geo.json的副本，可以用作未来替换
                            url: "http://data.hcharts.cn/jsonp.php?filename=GeoMap/json/" + e.point.drilldown + ".geo.json",
                            contentType: "application/json; charset=utf-8",
                            dataType: 'jsonp',
                            crossDomain: true,
                            success: function (json) {
                                data = Highcharts.geojson(json);

                                //重新获得城市内的数据
                                $.each(data, function (i) {
                                    console.log("city name:"+this.properties.name);
                                    //TODO ajax load data and change this value
                                    this.value = i;
                                    this.events = {};
                                    this.events.click = getPoint;

                                });
                                chart.hideLoading();

                                chart.addSeriesAsDrilldown(e.point, {
                                    name: e.point.name,
                                    data: data,
                                    events: {
                                        show: function () {
                                            alert(1);
                                        }
                                    },
                                    dataLabels: {
                                        enabled: true,
                                        format: '{point.name}'
                                    }
                                });
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {

                            }
                        });
                    }
                    this.setTitle(null, {text: cname});
                },
                drillup: function () {
                    this.setTitle(null, {text: '中国'});
                }
            }
        },
        tooltip: {
            formatter: function () {
                var htm = "扫码次数<br/>";

                if (this.point.drilldown) {
                    htm += this.point.properties["cn-name"];
                } else {
                    htm += this.point.name;
                }
                var times = this.point.value;
                htm += ":" + times;
                return htm;

            }
        },
        credits: {
            href: "javascript:goHome()",
            text: "www.315kc.com"
        },
        title: {
            text: '扫码次数全国统计 315kc(315快查数据中心)'
        },

        subtitle: {
            text: '中国',
            floating: true,
            align: 'right',
            y: 50,
            style: {
                fontSize: '16px'
            }
        },

        legend: small ? {} : {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        colorAxis: {
            min: 0,
            minColor: '#E6E7E8',
            maxColor: '#005645',
            labels: {
                style: {
                    "color": "red", "fontWeight": "bold"
                }
            }
        },

        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },

        plotOptions: {
            map: {
                states: {
                    hover: {
                        color: '#EEDD66'
                    }
                }
            }
        },

        series: [{
            data: data,
            name: '中国',
            dataLabels: {
                enabled: true,
                format: '{point.properties.cn-name}'
            }
        }],

        drilldown: {

            activeDataLabelStyle: {
                color: '#FFFFFF',
                textDecoration: 'none',
                textShadow: '0 0 3px #000000'
            },
            drillUpButton: {
                relativeTo: 'spacingBox',
                position: {
                    x: 0,
                    y: 60
                }
            }
        }
    });
});

var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
    -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);


function base64decode(str) {
    var c1, c2, c3, c4;
    var i, len, out;

    len = str.length;
    i = 0;
    out = "";
    while (i < len) {
        /* c1 */
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c1 == -1);
        if (c1 == -1)
            break;

        /* c2 */
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while (i < len && c2 == -1);
        if (c2 == -1)
            break;

        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

        /* c3 */
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if (c3 == 61)
                return out;
            c3 = base64DecodeChars[c3];
        } while (i < len && c3 == -1);
        if (c3 == -1)
            break;

        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

        /* c4 */
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if (c4 == 61)
                return out;
            c4 = base64DecodeChars[c4];
        } while (i < len && c4 == -1);
        if (c4 == -1)
            break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
}
function goHome() {
    window.open("http://www.315kc.com/");
}
