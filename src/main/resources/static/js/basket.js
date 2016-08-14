$(function() {
	var liveBetAction=$('#liveBetAction').val();
	var liveInfoAction=$('#liveInfoAction').val();
	var prematchBetAction=$('#prematchBetAction').val();
	var prematchInfoAction=$('#prematchInfoAction').val();
	var selectedOdds=[];
	console.log('basket loaded');
	
	$('#myonoffswitch').change(function(){
		var busketNav=$('.busket-nav');
		var fastBetInfo=$('#fastBetInfo');
		var fastModeOn = $("#myonoffswitch").is(':checked');
		console.log('#myonoffswitch change: ' +fastModeOn);
		if(fastModeOn){
			busketNav.hide();
			fastBetInfo.show();
		}else{
			busketNav.show();
			fastBetInfo.hide();
		}
	});

	$('.prematch-odd').css('cursor', 'pointer');
	$('.prematch-odd').click(function() {
		
		
		$('#betok').removeClass('hide').hide();
		
		$('#nm-basket').block({ 
	        message: '<h4>Ждите</h4>', 
	        css: { border: '3px solid #ССС' } 
	    }); 
		
		
		fastModeOn = $("#myonoffswitch").is(':checked');
		var oddId = $(this).prop('id');
		var amount = $("#oc_summ").val();
		
		
		
		
		$.ajax({
			url : prematchInfoAction,
			data : {oddId,oddId}
		}).done(function(data) {			
			$("#fastrezult").append(data+"<br/>");
		});
		
		if (fastModeOn) {
			
			var props = {
					oddId : oddId,
					amount : amount
				};
			
			$.ajax({
				url : prematchBetAction,
				data : props
			}).done(function(data) {
				$('#nm-basket').unblock(); 
				console.log(data);
				$('#betok').removeClass('hide').show();
			}).fail(function(data) {
				$('#nm-basket').unblock(); 
				alert("Невозможно оформить ставку. Проверьте баланс")
			});
		}else{
			selectedOdds.push(oddId);
			if(selectedOdds.length >1){
				$('#bn1').removeClass('active');
				$('#bn2').addClass('active');
			}else{
				$('#bn2').removeClass('active');
				$('#bn1').addClass('active');
			}
			$('#nm-basket').unblock(); 
		}

	});
})