$(function() {
	$.ajax({
		url : "./admin/data",
	}).done(function(data) {
		console.log(data);
		FusionCharts.ready(function() {
			var sportsChart = new FusionCharts({
				type : 'doughnut2d',
				renderAt : 'sports-chart-container',
				width : '100%',
				height : '500',
				dataFormat : 'json',
				dataSource : {
					"chart" : {
						"caption" : "Количество матчей по видам спорта",
						"bgColor" : "#ffffff",
						 "paletteColors": "#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000",
						"showBorder" : "0",
						"use3DLighting" : "0",
						"showShadow" : "0",
						"formatNumberScale":"0",
						"enableSmartLabels" : "0",
						"startingAngle" : "0",
						"showLabels" : "0",
						"showPercentValues" : "1",
						"showLegend" : "1",
						"legendShadow" : "0",
						"legendBorderAlpha" : "0",
						"centerLabel" : "Количество матчей в $label: $value",
						"centerLabelBold" : "1",
						"showTooltip" : "0",
						"decimals" : "0",
						"captionFontSize" : "14",
						"subcaptionFontSize" : "14",
						"subcaptionFontBold" : "0"
					},
					"data" : data.sports
				}
			}).render();
			
			var betTypesChart = new FusionCharts({
				type : 'doughnut2d',
				renderAt : 'betTypes-chart-container',
				width : '100%',
				height : '500',
				dataFormat : 'json',
				dataSource : {
					"chart" : {
						"caption" : "Распределение количества ставок по типу",
						"bgColor" : "#ffffff",
						 "paletteColors": "#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000",
						"showBorder" : "0",
						"use3DLighting" : "0",
						"formatNumberScale":"0",
						"showShadow" : "0",
						"enableSmartLabels" : "0",
						"startingAngle" : "0",
						"showLabels" : "0",
						"showPercentValues" : "1",
						"showLegend" : "1",
						"legendShadow" : "0",
						"legendBorderAlpha" : "0",
						"centerLabel" : "Количество ставок в $label: $value",
						"centerLabelBold" : "1",
						"showTooltip" : "0",
						"decimals" : "0",
						"captionFontSize" : "14",
						"subcaptionFontSize" : "14",
						"subcaptionFontBold" : "0"
					},
					"data" : data.betTypes
				}
			}).render();
			

			var betAmountsChart = new FusionCharts({
				type : 'doughnut2d',
				renderAt : 'betAmounts-chart-container',
				width : '100%',
				height : '500',
				dataFormat : 'json',
				dataSource : {
					"chart" : {
						"caption" : "Распределение суммы ставок по типу",
						"numberSuffix": "тг.",
						"bgColor" : "#ffffff",
						 "paletteColors": "#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000",
						"showBorder" : "0",
						"use3DLighting" : "0",
						"showShadow" : "0",
						"enableSmartLabels" : "0",
						"startingAngle" : "0",
						"showLabels" : "0",
						"showPercentValues" : "1",
						"formatNumberScale":"0",
						"showLegend" : "1",
						"legendShadow" : "0",
						"legendBorderAlpha" : "0",
						"centerLabel" : "Сумма ставок в $label: $value",
						"centerLabelBold" : "1",
						"showTooltip" : "0",
						"decimals" : "0",
						"captionFontSize" : "14",
						"subcaptionFontSize" : "14",
						"subcaptionFontBold" : "0"
					},
					"data" : data.betAmounts
				}
			}).render();
			
			var winAmountsChart = new FusionCharts({
				type : 'doughnut2d',
				renderAt : 'winAmounts-chart-container',
				width : '100%',
				height : '500',
				dataFormat : 'json',
				dataSource : {
					"chart" : {
						"caption" : "Распределение суммы выйгрышей по типу",
						"bgColor" : "#ffffff",
						 "paletteColors": "#0075c2,#1aaf5d,#f2c500,#f45b00,#8e0000",
						"numberSuffix": "тг.",
						"showBorder" : "0",
						"use3DLighting" : "0",
						"showShadow" : "0",
						"enableSmartLabels" : "0",
						"startingAngle" : "0",
						"showLabels" : "0",
						"showPercentValues" : "1",
						"formatNumberScale":"0",
						"showLegend" : "1",
						"legendShadow" : "0",
						"legendBorderAlpha" : "0",
						"centerLabel" : "Сумма выйгрышей в $label: $value",
						"centerLabelBold" : "1",
						"showTooltip" : "0",
						"decimals" : "0",
						"captionFontSize" : "14",
						"subcaptionFontSize" : "14",
						"subcaptionFontBold" : "0"
					},
					"data" : data.winAmounts
				}
			}).render();
		});
	});
	
});
