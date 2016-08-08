$(function() {

	console.log('basket loaded');

	$('.prematch-odd').css('cursor', 'pointer');
	$('.prematch-odd').click(function() {
		console.log("click");
		fastModeOn = $("#myonoffswitch").is(':checked');
		var oddId = $(this).prop('id');
		var amount = $("#oc_summ").val();
		var props = {
			oddId : oddId,
			amount : amount
		};
		console.log(props);
		$.ajax({
			url : "/betting-engine/client/prematch/oddInfo?oddId=" + oddId
		}).done(function(data) {
			console.log(data);
			$("#fastrezult").html(data);
		});
		if (fastModeOn) {
			$.ajax({
				url : "/betting-engine/client/prematch/bet/create",
				data : props
			}).done(function(data) {
				console.log(data);
			});
		}

	});
})