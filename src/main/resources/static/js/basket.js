



$(function() {
	window.selectedOdds=[];
	window.totalOddValue=1;

	$( window ).scroll(function() {
		var offset=$(this).scrollTop()-50;
		offset=offset>0?offset:0;
		$('#nm-basket').css('top', offset);
	});

	var fastBetEnabled=$("#fastBetEnabled").val();
	console.log(fastBetEnabled);


	var liveBetAction=$('#liveBetAction').val();
	var liveInfoAction=$('#liveInfoAction').val();
	var liveBetsAction=$('#liveBetsAction').val();
	var prematchBetAction=$('#prematchBetAction').val();
	var prematchInfoAction=$('#prematchInfoAction').val();
	var prematchBetsAction=$('#prematchBetsAction').val();

	var saveDefaultBetAmount=$('#saveDefaultBetAmount').val();
	var saveFastBetEnabled=$('#saveFastBetEnabled').val();


	var regularMode=$('#regularMode');
	var fastrezult=$("#fastrezult");
	var fastBetInfo=$('#fastBetInfo');
	var busketNav=$('.busket-nav');


	if(fastBetEnabled=="false"||fastBetEnabled==""){
		$("#myonoffswitch").click();
		busketNav.show();
		fastBetInfo.hide();
		regularMode.removeClass('hide').show();
		fastrezult.hide();
	}

	$('#myonoffswitch').change(function(){
		var fastModeOn = $(this).is(':checked');

		$('#nm-basket').block({ 
			message: '<h4>Ждите</h4>', 
			css: { border: '3px solid #ССС' } 
		}); 
		$.ajax({
			url : saveFastBetEnabled,
			data : {fastBetEnabled:fastModeOn}
		}).done(function(data) {
			$('#nm-basket').unblock(); 
		}).fail(function(data) {
			$('#nm-basket').unblock(); 
		});


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
		window.totalOddValue=1;
		$('#ex_kef_sum').html(window.totalOddValue.toFixed(2));
		$('.busket-item').remove();
		window.selectedOdds=[];
		$('#bn2').removeClass('active');
		$('#bn1').addClass('active');
	}

	$('.clearAllbasket').click(function(){
		clear();
	});

	$('#regularMode').on('click', '.busket-item-delete', function (){
		var oid=$(this).attr('oid');
		var oddValue=$(this).attr('oddValue');

		window.totalOddValue=1.0*window.totalOddValue/oddValue;
		$('#ex_kef_sum').html(window.totalOddValue.toFixed(2));
		window.selectedOdds = $.grep(window.selectedOdds, function(value) {
			return value != +oid;
		});
		$(this).closest('.busket-item').remove();


		if(window.selectedOdds.length >1){
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
				if($.inArray(data.id,window.selectedOdds) == -1){
					var item=$("#busket-item-template").clone();
					item.find(".match-info-wrapper").html(data.matchInfoBean.shortInfo);
					item.find(".bet-info-wrapper").html(data.info);
					item.removeAttr('id').removeClass('hide').addClass('busket-item');
					item.find('.busket-item-delete').attr('oid',oddId).attr('oddValue',data.oddValue);
					regularMode.prepend(item);
					window.totalOddValue=window.totalOddValue*data.oddValue;
					$('#ex_kef_sum').html(window.totalOddValue.toFixed(2));
					window.selectedOdds.push(+oddId);
					console.log(window.selectedOdds);
					if(window.selectedOdds.length >1){
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


	$('#live-odds-container').on('click', '.live-odd',function() {


		$('#betok').removeClass('hide').hide();

		$('#nm-basket').block({ 
			message: '<h4>Ждите</h4>', 
			css: { border: '3px solid #ССС' } 
		}); 


		fastModeOn = $("#myonoffswitch").is(':checked');
		var oddId = $(this).prop('id');
		var amount = $("#oc_summ").val();



		$.ajax({
			url : liveInfoAction,
			data : {oddId,oddId}
		}).done(function(data) {
			console.log(data);
			if (fastModeOn) {			
				$("#fastrezult").html(data.matchInfoBean.shortInfo+": <b>"+data.info+"</b>");
			}else{
				if($.inArray(data.id,window.selectedOdds) == -1){
					var item=$("#busket-item-template").clone();
					item.find(".match-info-wrapper").html(data.matchInfoBean.shortInfo);
					item.find(".bet-info-wrapper").html(data.info);
					item.removeAttr('id').removeClass('hide').addClass('busket-item');
					item.find('.busket-item-delete').attr('oid',oddId).attr('oddValue',data.oddValue);
					regularMode.prepend(item);
					window.totalOddValue=window.totalOddValue*data.oddValue;
					$('#ex_kef_sum').html(window.totalOddValue.toFixed(2));
					window.selectedOdds.push(+oddId);
					console.log(window.selectedOdds);
					if(window.selectedOdds.length >1){
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
				url : liveBetAction,
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



	$('#formsubmit').click(function(e){
		e.preventDefault();
		if(window.selectedOdds.length <1){
			alert("Нет ставок");
			return false;
		}

		var amount = $("#oc_summ").val();

		$('#nm-basket').block({ 
			message: '<h4>Ждите</h4>', 
			css: { border: '3px solid #ССС' } 
		});	

		var props = {
				oddIds : window.selectedOdds,
				amount : amount
		};
		var action=$('.live-odd').length > 0?liveBetsAction:prematchBetsAction;
		$.ajax({
			url : action,
			data : props
		}).done(function(data) {
			$('#nm-basket').unblock(); 
			$('#betok').removeClass('hide').show();
		});
		clear();

	});

	$('#save-oc-button').click(function(e){
		e.preventDefault();
		var amount = $("#oc_summ").val();

		$('#nm-basket').block({ 
			message: '<h4>Ждите</h4>', 
			css: { border: '3px solid #ССС' } 
		});
		var props = {
				defaultBetAmount : amount
		};

		console.log(props);
		$.ajax({
			url : saveDefaultBetAmount,
			data : props
		}).done(function(data) {
			$('#nm-basket').unblock(); 			
		}).fail(function(data) {
			$('#nm-basket').unblock(); 
			alert("Невозможно сохранить")
		});
		clear();

	});



})