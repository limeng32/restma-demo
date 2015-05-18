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
	require(['node', 'io'], function($, IO){
		//var testStr = '{"maxPageNum":3,"pageItems":[{"content":"","id":9,"title":"ff","updateTime":1431565579447,"user":{"address":"west","article":[{"$ref":"$.pageItems[0]"},{"content":"asdasd","id":10,"title":"_","updateTime":1431565579455,"user":{"$ref":"$.pageItems[0].user"}}],"id":1,"iteratorArticle":{},"name":"zzhang3","nickname":"xiaozhang","publisher":[]}},{"$ref":"$.pageItems[0].user.article[1]"}],"pageNo":2}'; 
       	//var j = JSON.parse(testStr);
       	//console.log(j.pageItems[0].user.name);

		$('#articleJson').on('click', function(ev){
			IO.get('test/showArticleMix?_content=json', {'id': 1}, function(data){
				//console.log(data._content.pageItems[1]);	
				console.log(data);
			}, 'text')
		})
	})
</script>
</body>
</html>