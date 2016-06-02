$(function() {

	window.addEventListener('storage', function(e) {
		if (e.key == 'preview')
			$('#bet-info').html(e.newValue);
	});

});