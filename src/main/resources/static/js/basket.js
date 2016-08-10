$(function() {
	var liveBetAction=$('#liveBetAction').val();
	var liveInfoAction=$('#liveInfoAction').val();
	var prematchBetAction=$('#prematchBetAction').val();
	var prematchInfoAction=$('#prematchInfoAction').val();

	console.log('basket loaded');

	$('.prematch-odd').css('cursor', 'pointer');
	$('.prematch-odd').click(function() {
		
		$('#nm-basket').block({ 
	        message: '<h4>Ждите</h4>', 
	        css: { border: '3px solid #ССС' } 
	    }); 
		
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
			url : prematchInfoAction,
			data : {oddId,oddId}
		}).done(function(data) {
			console.log(data);
			$("#fastrezult").html(data);
		});
		if (fastModeOn) {
			$.ajax({
				url : prematchBetAction,
				data : props
			}).done(function(data) {
				$('#nm-basket').unblock(); 
				console.log(data);
			}).fail(function(data) {
				$('#nm-basket').unblock(); 
				alert("Невозможно оформить ставку. Проверьте баланс")
			});
		}else{
			$('#nm-basket').unblock(); 
		}

	});
})