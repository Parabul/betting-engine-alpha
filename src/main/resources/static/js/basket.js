var max_calc = 500000000;
var MaxKoef = 1000;
var basket_divider = 1;
var alldevider = 0;
var offsetTop = 0;
function offsetPosition(e) {
	offsetTop = 0;
	do {
		offsetTop += e.offsetTop;
	} while (e = e.offsetParent);
	return offsetTop;
}
var bs = document.getElementById('betslip1'), offset = offsetPosition(bs);
var $widgetBetradar = $('#scroll_block');
window.onscroll = function() {
	if ((bs.offsetHeight + 10) >= document.body.clientHeight) {
		bs.className = '';
		return;
	}

	if ($widgetBetradar.length > 0) {
		if (offset < window.pageYOffset) {
			$widgetBetradar.addClass('fcl').css({
				top : ($(window).scrollTop() - 85) + 'px'
			});
		} else {
			$widgetBetradar.removeClass('fcl').css({
				top : 0
			});
		}
	}
}