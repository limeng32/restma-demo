<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>海贼王乐透</title>
<style>
* {
	margin: 0;
	padding: 0;
	background-color: #E9F1FC;
}

.content {
	width: 500px;
	height: 500px;
	margin: 60px auto 0;
	position: relative;
}

.img {
	height: 500px;
	position: relative;
}

.img:after {
	content: '';
	display: block;
	visibility: hidden;
	height: 0;
	clear: both;
}

ul {
	list-style: none;
}

ul li {
	display: inline-block;
	width: 100px;
	height: 100px;
	position: absolute;
}

ul li.list1 {
	top: -50px;
	left: 200px;
}

ul li.list2 {
	top: 8px;
	left: 39px;
}

ul li.list9 {
	top: 8px;
	left: 361px;
}

ul li.list3 {
	top: 157px;
	left: -46px;
}

ul li.list8 {
	top: 157px;
	left: 446px;
}

ul li.list4 {
	top: 325px;
	left: -17px;
}

ul li.list7 {
	top: 325px;
	left: 417px;
}

ul li.list5 {
	top: 435px;
	left: 114px;
}

ul li.list6 {
	top: 435px;
	left: 286px;
}

ul li img {
	width: 100px;
	height: 100px;
	border-radius: 50px;
	opacity: 0.2;
}

ul li.active img {
	opacity: 1;
}

button {
	position: absolute;
	top: 330px;
	left: 200px;
	width: 100px;
	height: 50px;
	text-align: center;
	margin: 50px auto;
	
	border-width: 1px;
	border-color: #a2a2a2;
	border-radius: 5px;
}

.detail {
	position: absolute;
	display: none;
	top: 80px;
	left: 158px;
	width: 183px;
	height: 275px;
	box-shadow: 0 0 5px #ccc;
}
</style>
<script src="http://g.tbcdn.cn/kissy/k/1.4.1/seed-min.js"
	data-config="{combine:true}"></script>
</head>
<body>
	<div class="content">111
	<div class="img">
		<ul class="img-list">
			<li class="list1 active"><img src="${resourceRoot}/image/op/1.min.png" /></li>
			<li class="list2"><img src="${resourceRoot}/image/op/2.min.png" /></li>
			<li class="list3"><img src="${resourceRoot}/image/op/3.min.png" /></li>
			<li class="list4"><img src="${resourceRoot}/image/op/4.min.png" /></li>
			<li class="list5"><img src="${resourceRoot}/image/op/5.min.png" /></li>
			<li class="list6"><img src="${resourceRoot}/image/op/6.min.png" /></li>
			<li class="list7"><img src="${resourceRoot}/image/op/7.min.png" /></li>
			<li class="list8"><img src="${resourceRoot}/image/op/8.min.png" /></li>
			<li class="list9"><img src="${resourceRoot}/image/op/9.min.png" /></li>
		</ul>
	</div>
	<button class="start">start</button>
	<div class="detail">
	</div>
	<script>
	KISSY.config({
	    packages: [
	        {
	            name: "module",
	            tag: "20140212",
	            path: "${resourceRoot}/js/kissy/", 
	            combine: false,
	            charset: "utf-8"
	        }
	    ]
	});
	
	KISSY.use('module/opLotto, module/flow, node, event', function(S, OP, Flow, N, E){
	
		S.ready(function(S){
			var $ = S.all;
			
			OP.init();
	
			E.on('button', 'click', function(ev){
				Flow.init();
				var self = this;
				if($(self).hasClass('start')){
					$(self).replaceClass('start', 'pause').html('pause');
					$('.detail').fadeOut();
					OP.start();
				}
				else{
					$(self).replaceClass('pause', 'start').html('start');
					OP.pause();
				}
			})
		})
	})
	</script>
</div>
</body>
</html>