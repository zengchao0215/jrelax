var dashboard = function() {
	var sparkData = [ 4, 6, 7, 1, 4, 5, 7, 9, 6, 5, 3, 7, 1, 2, 8, 7, 3, 8, 9,
			2, 1, 7, 4, 9, 1, 7 ], data = [], totalPoints = 100, updateInterval = 6000;
	var plot_cpu, plot_mem, plot_users;
	var plot_data_cpu = [], plot_data_mem = [], plot_data_users = [];
	function events() {
		$(window).on("resize", initSparkline)
	}
	
	//请求服务器数据
	function requestRemoteData(){
		$.ajax({
			url : ns.getBasePath()+"/index/status",
			async:false,
			success:function(data){
				plot_data_cpu.push([plot_data_cpu.length+1,data.cpu]);
				plot_data_mem.push([plot_data_mem.length+1,data.mem]);
				plot_data_users.push([plot_data_users.length+1,data.users]);
			}
		});
	}
	function getRemoteData(type){
		if(type=="cpu"){
			return plot_data_cpu;
		}else if(type == "mem"){
			return plot_data_mem;
		}else if(type == "users"){
			return plot_data_users;
		}
	}
	
	function getRandomData() {
		if (data.length > 0) {
			data = data.slice(1)
		}
		while (data.length < totalPoints) {
			var prev = data.length > 0 ? data[data.length - 1] : 50, y = prev
					+ Math.random() * 10 - 5;
			if (y < 0) {
				y = 0
			} else {
				if (y > 100) {
					y = 100
				}
			}
			data.push(y)
		}
		var res = [];
		for ( var i = 0; i < data.length; ++i) {
			res.push([ i, data[i] ])
		}
		return res
	}
	function update() {
		requestRemoteData();
		plot_cpu.setData([ getRemoteData("cpu") ]);
		plot_cpu.draw();
		$(".tile-stats-cpu").text(plot_data_cpu[plot_data_cpu.length-1][1]+"%");
		
		plot_mem.setData([ getRemoteData("mem") ]);
		plot_mem.draw();
		$(".tile-stats-mem").text(plot_data_mem[plot_data_mem.length-1][1]+"%");
		
		plot_users.setData([ getRemoteData("users") ]);
		plot_users.draw();
		$(".tile-stats-users").text(plot_data_users[plot_data_users.length-1][1]);
		
		setTimeout(update, updateInterval)
	}
	function initMap() {
		$(".map").vectorMap({
			map : "map_format_cn",
			scaleColors : [ "#C8EEFF", "#0071A4" ],
			hoverOpacity : 0.7,
			hoverColor : true,
			zoomOnScroll : true,
			markerStyle : {
				initial : {
					fill : "#F8E23B",
					stroke : "#383f47"
				}
			},
			regionStyle : {
				initial : {
					fill : "#9f9f9f",
					"fill-opacity" : 0.9,
					stroke : "#fff",
				},
				hover : {
					"fill-opacity" : 0.7
				},
				selected : {
					fill : "#1A94E0"
				}
			},
			markerStyle : {
				initial : {
					fill : "#e04a1a",
					stroke : "#FF604F",
					"fill-opacity" : 0.5,
					"stroke-width" : 1,
					"stroke-opacity" : 0.4,
				},
				hover : {
					stroke : "#C54638",
					"stroke-width" : 2
				},
				selected : {
					fill : "#C54638"
				},
			},
			backgroundColor : "#f1f4f9",
			markers : []
		});
	}
	function initSparkline() {
		$(".dash-line").sparkline(sparkData, {
			type : "line",
			width : "100%",
			height : "40",
			lineWidth : 1,
			lineColor : "#fff",
			spotColor : "#f1f4f9",
			fillColor : "",
			spotRadius : "2",
		})
	}
	function initFlot() {
		var options = {
			colors : [ "#000" ],
			series : {
				shadowSize : 0,
				lines : {
					show : true,
					lineWidth : 0.5,
				}
			},
			grid : {
				borderWidth : 0
			},
			yaxis : {
				min : 0,
				show : false
			},
			xaxis : {
				show : false
			}
		};
		plot_cpu = $.plot(".tile-line-cpu", [ getRemoteData("cpu") ], options);
		options.colors = [ "#fff" ];
		plot_users = $.plot(".tile-line-users", [ getRemoteData("users") ], options);
		plot_mem = $.plot(".tile-line-mem", [ getRemoteData("mem") ], options);
	}
	return {
		init : function() {
			events();
			initMap();
			initSparkline();
			initFlot();
			update();
		}
	}
}();
$(function() {
	dashboard.init()
});