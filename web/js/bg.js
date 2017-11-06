function f($){
	var a = document.getElementById("bg-canvas");
	var b = document.body.scrollWidth;	
	var c = $(".form").height() + 150;
	if (c==null | c<document.body.clientHeight+150)c = window.screen.height;
	a.width = b, a.height = c;
	var d = a.getContext("2d");
	d.strokeStyle = "#CCC", d.lineWidth = 1, d.beginPath(), d.translate(.5, .5);
	for (var e = 30; e <= c; e += 30) d.moveTo(0, e), d.lineTo(b, e);
	for (var e = 30; e <= b; e += 30) d.moveTo(e, 0), d.lineTo(e, c);
	d.closePath(), d.stroke()
}

jQuery(document).ready(function($){
	//初始化绘制
	f($);
	//窗体大小改变，重新绘制
	$(window).resize(function(){
		f($);
	});
	//滚动条改变，重新绘制
	$(window).scroll(function () {
		f($);
	});
});
