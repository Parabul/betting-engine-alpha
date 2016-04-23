function showSport() {
	$.ajax({
		url : "/cabinet/sports"
	}).done(function(data) {
		var items = [];
		for (key in data) {
			var value = data[key];
			items.push({
				id : key,
				text : value,
				value : key
			});
		}
		$("#sport").select2({
			data : null
		});
		var sportSelect = $("#sport").select2({
			data : items
		});

		sportSelect.on("select2:select", function(e) {
			showCategory();
		});
	});
}

function showCategory() {
	$.ajax({
		url : "/cabinet/categories?sportId=" + $("#sport").val()
	}).done(function(data) {
		var items = [];
		for (key in data) {
			var value = data[key];
			items.push({
				id : key,
				text : value,
				value : key
			});
		}
		$("#category-group").show();
		$("#category").select2({
			data : null
		});
		var categorySelect = $("#category").select2({
			data : items
		});

		categorySelect.on("select2:select", function(e) {
			showTournament();
		});
	});
}

function showTournament() {
	$.ajax({
		url : "/cabinet/tournaments?categoryId=" + $("#category").val()
	}).done(function(data) {
		var items = [];
		for (key in data) {
			var value = data[key];
			items.push({
				id : key,
				text : value,
				value : key
			});
		}
		$("#tournament-group").show();
		$("#tournament").select2({
			data : null
		});
		var tournamentSelect = $("#tournament").select2({
			data : items
		});

		tournamentSelect.on("select2:select", function(e) {
		});
	});
}

$(function() {
	$("#amount").TouchSpin({
		min : 1,
		max : 1000000,
		step : 100,
		postfix : 'тг.',
		initval : 100
	});
	showSport();
});