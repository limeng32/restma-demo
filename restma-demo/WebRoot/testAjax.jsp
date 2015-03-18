<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>新增用户</title>
		<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
	</head>
	<body>
		用户数量：
		<span id="countHolder">${count }</span>
		<br />
		姓名：
		<input type="text" name="name" id="name" />
		<br />
		地址：
		<input type="text" name="address" id="address" />
		<br />
		昵称：
		<input type="text" name="nickname" id="nickname" />
		<br />
		<input type="button" value="click" id="click">
		<br />
		<br />
		文章数量： 
		<span id="articleCountHolder">${articleCount }</span>
		<br />
		标题：
		<input type="text" name="title" id="title" />
		<br />
		<input type="button" value="click2" id="click2">
		<script type="text/javascript"> 
    	$("#click").click(function() { 
            $.ajax( { 
                type : "POST", 
                url : "testAjax/test?", 
                data : {name:$('#name').val(),address:$('#address').val(),nickname:$('#nickname').val()}, 
                dataType: "text", 
                success : function(msg) { 
                   $('#countHolder').html(msg); 
                } 
            }); 
        });
        $("#click2").click(function() { 
            $.ajax( { 
                type : "POST", 
                url : "testAjax/test2?", 
                data : {'user.id':'2',title:$('#title').val(),content:'aaa'}, 
                dataType: "text", 
                success : function(msg) { 
                	$('#articleCountHolder').html(msg); 
                } 
            }); 
        });
    </script>
	</body>
</html>
