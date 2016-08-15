$(function() {
	
	$( window ).scroll(function() {
		var offset=$(this).scrollTop()-50;
		offset=offset>0?offset:0;
		$('#nm-basket').css('top', offset);
	});
	
	
	
	var liveBetAction=$('#liveBetAction').val();
	var liveInfoAction=$('#liveInfoAction').val();
	var prematchBetAction=$('#prematchBetAction').val();
	var prematchInfoAction=$('#prematchInfoAction').val();
	var prematchBetsAction=$('#prematchBetsAction').val();

	
	var regularMode=$('#regularMode');
	var fastrezult=$("#fastrezult");
	var fastBetInfo=$('#fastBetInfo');
	
	var totalOddValue=1;
	
	var selectedOdds=[];
	console.log('basket loaded');
	
	$('#myonoffswitch').change(function(){
		var busketNav=$('.busket-nav');
		
		var fastModeOn = $(this).is(':checked');
		if(fastModeOn){
			busketNav.hide();
			fastBetInfo.show();
			regularMode.hide();
			fastrezult.show();
		}else{
			busketNav.show();
			fastBetInfo.hide();
			regularMode.removeClass('hide').show();
			fastrezult.hide();
		}
	});
	
	function clear(){
		totalOddValue=1;
		$('#ex_kef_sum').html(totalOddValue.toFixed(2));
		$('.busket-item').remove();
		selectedOdds=[];
		$('#bn2').removeClass('active');
		$('#bn1').addClass('active');
	}
	
	$('.clearAllbasket').click(function(){
		clear();
	});

	$('#regularMode').on('click', '.busket-item-delete', function (){
        var oid=$(this).attr('oid');
        var oddValue=$(this).attr('oddValue');

        totalOddValue=1.0*totalOddValue/oddValue;
		$('#ex_kef_sum').html(totalOddValue.toFixed(2));
		selectedOdds = $.grep(selectedOdds, function(value) {
			  return value != oid;
			});
		$(this).closest('.busket-item').remove();
		

		if(selectedOdds.length >1){
			$('#bn1').removeClass('active');
			$('#bn2').addClass('active');
		}else{
			$('#bn2').removeClass('active');
			$('#bn1').addClass('active');
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
			console.log(data);
			if (fastModeOn) {			
				$("#fastrezult").html(data.matchInfoBean.shortInfo+": <b>"+data.info+"</b>");
			}else{
				console.log({
					inArray:$.inArray(data.id+"",selectedOdds),
					id:data.id,
					selectedOdds:selectedOdds
				});
				if($.inArray(data.id+"",selectedOdds) == -1){
					var item=$("#busket-item-template").clone();
					item.find(".match-info-wrapper").html(data.matchInfoBean.shortInfo);
					item.find(".bet-info-wrapper").html(data.info);
					item.removeAttr('id').removeClass('hide').addClass('busket-item');
					item.find('.busket-item-delete').attr('oid',oddId).attr('oddValue',data.oddValue);
					regularMode.prepend(item);
					totalOddValue=totalOddValue*data.oddValue;
					$('#ex_kef_sum').html(totalOddValue.toFixed(2));
					selectedOdds.push(oddId);
					if(selectedOdds.length >1){
						$('#bn1').removeClass('active');
						$('#bn2').addClass('active');
					}else{
						$('#bn2').removeClass('active');
						$('#bn1').addClass('active');
					}
				}
			}
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
			
			
			$('#nm-basket').unblock(); 
		}

	});
})