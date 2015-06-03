<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>本页面用来测试kissy框架</title>
<script src="//g.alicdn.com/kissy/k/5.0.1/seed.js"
	data-config="{combine:true}"></script>
</head>
<body>
	<input type="button" value="articleJson" id="articleJson">
	<script>
		require.config({
		    packages: [
		        {
		            name: "mypkg",
		            tag: "201505242050",
		            path: "${resourceRoot}/js/kissy/module", 
		            combine : false,
		            charset: "utf-8"
		        }
		    ]
		});
		require([ 'node', 'io', 'json', 'mypkg/jsonx', 'util' ], function($, IO, JSON, JSONX, UTIL) {
			//var testStr = '{"maxPageNum":3,"pageItems":[{"content":"","id":9,"title":"ff","updateTime":1431565579447,"user":{"address":"west","article":[{"$ref":"$.pageItems[0]"},{"content":"asdasd","id":10,"title":"_","updateTime":1431565579455,"user":{"$ref":"$.pageItems[0].user"}}],"id":1,"iteratorArticle":{},"name":"zzhang3","nickname":"xiaozhang","publisher":[]}},{"$ref":"$.pageItems[0].user.article[1]"}],"pageNo":2}'; 
			//var j = JSON.parse(testStr);
			//console.log(j.pageItems[0].user.name);
			//console.log(JSONX.decode(j).pageItems[1].user.name);
			$('#articleJson').on(
					'click',
					function(ev) {
						IO.jsonp('http://localhost:8080/restma-demo/test/asd',
								function(d) {
									console.log(d.address);
								});
					})
			IO.get('test/showArticleMix?_content=json', {
				'id' : 1
			}, function(data) {
				console.log(JSONX.decode(data).pageItems[1].user.name);
				//console.log(data);
			}, 'json')
			IO.get('test/showArticle2Mix?_content=json', {
			}, function(data) {
				console.log('2-' + data);
			}, 'text')
			IO.get('test/showArticle3Mix?_content=json', {
			}, function(data) {
				console.log('3-' + data);
			}, 'text')
			IO.get('test/showArticle4Mix?_content=json', {
			}, function(data) {
				console.log('4-' + data);
			}, 'text')
			IO.get('test/showArticle5Mix?_content=json', {
			}, function(data) {
				console.log('5-' + data);
			}, 'text')
			IO.get('test/showArticle7Mix?_content=json', {
			}, function(data) {
				console.log('7-' + data);
			}, 'text')
		})
	</script>
</body>
</html>