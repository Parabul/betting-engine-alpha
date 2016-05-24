function update() {
	$('.match-wrapper').each(function() {
		var currentWrapper = $(this);
		var id = currentWrapper.attr('id');
		$.ajax({
			url : "./one?matchId=" + id,
		}).done(function(data) {
			$('#' + id).html(data);
		});
	});
	setTimeout(update, 10000);
}

$(function() {
	setTimeout(function() {
		update();
	}, 10000);
});