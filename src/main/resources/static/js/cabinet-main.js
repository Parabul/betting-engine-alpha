Array.prototype.remove = function() {
	var what, a = arguments, L = a.length, ax;
	while (L && this.length) {
		what = a[--L];
		while ((ax = this.indexOf(what)) !== -1) {
			this.splice(ax, 1);
		}
	}
	return this;
};

function showDropDown(url, select, callback) {
	$.ajax({
		url : url,
	}).done(
			function(data) {
				$(select).find('option').remove().end().append(
						'<option value="null">Укажите значение</option>').val(
						'whatever');
				$.each(data, function(key, value) {
					$(select).append(
							$("<option></option>").attr("value", value.id)
									.text(value.info));
				});

				var select2 = $(select).select2();

				select2.on("select2:select", function(e) {
					callback();
				});
			});
}

function showOdd(matchId) {
	showDropDown("./matches/odds?matchId=" + matchId, '#odd', enable);
}

function enable() {

}
function updateMatch(sportId) {
	var matchesBloodhound = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.nonword('description'),
		queryTokenizer : Bloodhound.tokenizers.nonword,

		remote : {
			url : './matches?sportId=' + sportId + '&q=QUERY',
			wildcard : 'QUERY'
		},
		sufficient : 20
	});
	$("#match-typeahead").typeahead('val', '');
	$("#match-typeahead").typeahead("destroy");
	$('#match-typeahead').typeahead(null, {
		name : 'best-pictures',
		display : 'description',
		source : matchesBloodhound,
		limit : 30
	});
	$('#match-typeahead').bind('typeahead:select', function(ev, suggestion) {
		showOdd(suggestion.matchId)
	});
}
var selectedOdds = [];

function check() {
	$("#oddIds").val(selectedOdds.join());
	var betInfo = $("#bet-info").clone();
	$('.delete-odd', betInfo).remove();
	$("#preview").val(betInfo.html());

	if (selectedOdds.length > 0) {
		if (selectedOdds.length > 1) {
			$('#bet-type').html("Экспресс");
		} else {
			$('#bet-type').html("Простой");
		}
		$("#create").removeClass("disabled");
		$("#create").attr("disabled", false);
	} else {
		$("#create").addClass("disabled");
		$("#create").attr("disabled", true);
	}
	updateRemote();
}

function updateRemote() {
	localStorage.setItem('preview', $('#bet-info').html());
}

function clearRemote() {
	localStorage.setItem('preview', '<h1>Ставка оформлена!</h2>');
}

function storageAvailable(type) {
	try {
		var storage = window[type], x = '__storage_test__';
		storage.setItem(x, x);
		storage.removeItem(x);
		return true;
	} catch (e) {
		return false;
	}
}

$(function() {

	$("#amount").TouchSpin({
		min : 1,
		max : 1000000,
		step : 100,
		postfix : 'тг.',
		initval : 100
	});

	$("#amount").on("change", function() {
		$("#amount-copy").html($("#amount").val());
		updateRemote();
	});

	$("#bet-info").on('click', '.delete-odd', function(e) {
		var oddInfo = $(this).parent('div.odd-info');
		var oddId = oddInfo.attr("id").replace("odd", "");
		selectedOdds.remove(oddId);
		oddInfo.detach();
		check();
	});

	$("#create").click(function(e) {
		e.preventDefault();
		$("#main-form").submit();
		clearRemote();
	});
	$("#add")
			.click(
					function(e) {
						e.preventDefault();
						var oddId = $("#odd").val();
						if (oddId != undefined && oddId != '') {
							var data = $("#odd").select2('data');
							selectedOdds.push(oddId);
							$('#odd-info').html();
							$("#odds-wrapper")
									.append(
											"<div class='odd-info' id='odd"
													+ oddId
													+ "'><div class='delete-odd btn btn-danger btn-xs'>Удалить</div><span>"
													+ $("#match-typeahead")
															.val()
													+ "</span><br /> <b class='pull-right'>"
													+ data[0].text
													+ "</b><hr /></div>");

							updateMatch($("#sport-icons li.selected")
									.attr("id"));
							$("#odd").select2('destroy');
							$("#odd").find('option').remove();
							check();
						}
					});

	$('#change-sport-list').click(function() {
		console.log('#change-sport-list');
		$('#save-sport-list').show();
		$('#revert-sport-list').show();
		$('.remove-sport').show();
		$('#change-sport-list').hide();
		$("#sport-icons").sortable({
			tolerance : 'pointer',
			revert : 'invalid',
			placeholder : 'span2 well placeholder tile',
			forceHelperSize : true
		});

		$('.remove-sport').click(function() {
			$(this).closest('.sport-item').hide();
		});
	});

	$('#save-sport-list').click(function() {
		$('#save-sport-list').hide();
		$('#revert-sport-list').hide();
		$('.remove-sport').hide();
		$('#change-sport-list').show();

		if (storageAvailable('localStorage')) {
			localStorage.setItem('sport-menu', $('#sport-icons').html());
		}
	});

	$('#revert-sport-list').click(function() {
		$('#save-sport-list').hide();
		$('#revert-sport-list').hide();
		if (storageAvailable('localStorage')) {
			localStorage.removeItem('sport-menu');
			window.location.reload();
		}

	});

	var sportMenu = localStorage.getItem('sport-menu');
	if (sportMenu != null) {
		$('#sport-icons').html(sportMenu);
		$("#sport-icons li").click(function() {
			$("#sport-icons li").removeClass("selected");
			$(this).addClass("selected");
			updateMatch($(this).attr("id"));
		});
	}

});