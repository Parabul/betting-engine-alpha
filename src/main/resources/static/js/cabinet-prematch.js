function showDropDown(url, select, callback) {
	$.ajax({
		url : url,
	}).done(function(data) {
		$(select).find('option').remove().end().append('<option value="null">Укажите значение</option>').val('whatever');
		$.each(data, function(key, value) {
			$(select).append($("<option></option>").attr("value", key).text(value));
		});

		var select2 = $(select).select2();

		select2.on("select2:select", function(e) {
			callback();
		});
	});
}

function showCategory() {
	showDropDown("/cabinet/categories?sportId=" + $("#sport").val(), '#category', showTournament);
}

function showTournament() {
	showDropDown("/cabinet/tournaments?categoryId=" + $("#category").val(), '#tournament', showMatch);
}

function showMatch() {
	showDropDown("/cabinet/matches?tournamentId=" + $("#tournament").val(), '#match', showOdd);
}
function showOdd() {
	showDropDown("/cabinet/matches/odds?matchId=" + $("#match").val(), '#odd', enable);
}

function enable() {
	$("#create").removeClass("disabled");
	$("#create").attr("disabled", false);
}

$(function() {
	$("#amount").TouchSpin({
		min : 1,
		max : 1000000,
		step : 100,
		postfix : 'тг.',
		initval : 100
	});
	showDropDown("/cabinet/sports", '#sport', showCategory);// showSport();
});