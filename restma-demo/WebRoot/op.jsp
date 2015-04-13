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
	left: 361px;
}

ul li.list9 {
	top: 8px;
	left: 39px;
}

ul li.list3 {
	top: 157px;
	left: 446px;
}

ul li.list8 {
	top: 157px;
	left: -46px;
}

ul li.list4 {
	top: 325px;
	left: 417px;
}

ul li.list7 {
	top: 325px;
	left: -17px;
}

ul li.list5 {
	top: 435px;
	left: 286px;
}

ul li.list6 {
	top: 435px;
	left: 114px;
}

ul li img {
	width: 100px;
	height: 100px;
	border-radius: 50px;
	opacity: 0.2;
}

ul li {
	display: inline-block;
	width: 100px;
	height: 100px;
	position: absolute;
}

ul li.active img {
	opacity: 1;
}

ul.checker li.checker1 {
	top: 20px;
	left: 10px;
}

ul.checker li.checker2 {
	top: 20px;
	left: 45px;
}

ul.checker li.checker3 {
	top: 20px;
	left: 80px;
}

ul.checker li.checker4 {
	top: 20px;
	left: 115px;
}

ul.checker li.checker5 {
	top: 20px;
	left: 150px;
}

ul.checker li.checker6 {
	top: 20px;
	left: 185px;
}

ul.checker li.checker7 {
	top: 20px;
	left: 220px;
}

ul.checker li.checker8 {
	top: 20px;
	left: 255px;
}

ul.checker li.checker9 {
	top: 20px;
	left: 290px;
}

ul.checker li.checkerAttempt {
	top: 25px;
	left: 335px;
}

ul.checker li img {
	width: 30px;
	height: 30px;
	border-radius: 50px;
	opacity: 0.2;
}

ul.checker li.active img {
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
	top: 125px;
	left: 158px;
	width: 183px;
	height: 183px;
	border-radius: 20px 5px 5px 5px;
	box-shadow: 0 0 1px solid #ccc;
}

.detail img {
	width: 181px;
	height: 181px;
	border-radius: 100px;
}

.echo {
	position: absolute;
	display: none;
	top: 15px;
	left: 950px;
	width: 180px; //
	height: 183px; //
	border-radius: 50px; //
	box-shadow: 0 0 5px #ccc;
}

.chars {
	color: #005491;
	font-size: 16px;
	font-weight: bolder;
	font-family: "黑体";
}

.digit {
	color: #f46306;
	font-size: 22px;
	font-weight: bolder;
	font-family: "黑体";
}
</style>
<script src="//g.alicdn.com/kissy/k/5.0.1/seed.js"
	data-config="{combine:true}"></script>
</head>
<body>
	<ul class="checker">
		<li class="checker1"><img src="${resourceRoot}/image/op/1.png" /></li>
		<li class="checker2"><img src="${resourceRoot}/image/op/2.png" /></li>
		<li class="checker3"><img src="${resourceRoot}/image/op/3.png" /></li>
		<li class="checker4"><img src="${resourceRoot}/image/op/4.png" /></li>
		<li class="checker5"><img src="${resourceRoot}/image/op/5.png" /></li>
		<li class="checker6"><img src="${resourceRoot}/image/op/6.png" /></li>
		<li class="checker7"><img src="${resourceRoot}/image/op/7.png" /></li>
		<li class="checker8"><img src="${resourceRoot}/image/op/8.png" /></li>
		<li class="checker9"><img src="${resourceRoot}/image/op/9.png" /></li>
		<li class="checkerAttempt"><span class="chars">Coin</span>:<span
			class="attemptsSpan digit">0</span></li>
	</ul>
	<div class="echo"></div>
	<div class="content">
		<div class="img">
			<ul class="img-list">
				<li class="list1 active"><img
					src="${resourceRoot}/image/op/1.png" /></li>
				<li class="list2"><img src="${resourceRoot}/image/op/2.png" /></li>
				<li class="list3"><img src="${resourceRoot}/image/op/3.png" /></li>
				<li class="list4"><img src="${resourceRoot}/image/op/4.png" /></li>
				<li class="list5"><img src="${resourceRoot}/image/op/5.png" /></li>
				<li class="list6"><img src="${resourceRoot}/image/op/6.png" /></li>
				<li class="list7"><img src="${resourceRoot}/image/op/7.png" /></li>
				<li class="list8"><img src="${resourceRoot}/image/op/8.png" /></li>
				<li class="list9"><img src="${resourceRoot}/image/op/9.png" /></li>
			</ul>
		</div>
		<button class="start">start</button>
		<div class="detail"></div>
		<script>
			require.config({
			    packages: [
			        {
			            name: "modulePkgName",
			            tag: "201504081517",
			            path: "${resourceRoot}/js/kissy/module", 
			            combine : false,
			            charset: "utf-8"
			        }
			    ]
			});
		
			require(['modulePkgName/opLotto','modulePkgName/flow', 'node'], function(OP, F, $){
				OP.init();

				$('.start').on('click', function(ev){
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
		</script>
	</div>
</body>
</html>