<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="${resourceRoot}/css/common.css"
	type="text/css">
<script type="text/javascript"
	src="${resourceRoot}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${resourceRoot}/js/asd.js"></script>
</head>
<body>
	<fmt:message key="test.title" />
	:
	<fmt:message key="test.vol" />
	<input type="button" value="显示中文" onclick="setClientCookie('zh_CN');">
	<input type="button" value="显示英文" onclick="setClientCookie('en_US');">
	<br /> ${count } ||| ${s }
	<a href="test/asd.html">asdf</a>
	<input type="text" value="asdasd" id="asd">
	<input type="button" value="click" id="click">
	<input type="button" value="userJson" id="userJson">
	<input value="" id="articleId" />
	<input type="button" value="articleJson" id="articleJson">

	<form method="post" action="<c:url value="/test/handle41"/>">
		姓名：<input type="text" name="name" /><br /> 地址：<input type="text"
			name="address" /><br /> 昵称：<input type="text" name="nickname" /><br />
		呵呵：<input type="text" name="hehe" /><br /> <input type="submit"
			value="提交">
	</form>

	----------------------------------------------

	<form method="post" action="<c:url value="/test?"/>">
		s：<input type="text" name="s" /><br /> <input type="submit"
			value="提交2">
	</form>

	----------------------------------------------

	<form:form modelAttribute="user">
		<form:input path="name" />
		<form:radiobuttons path="sex" delimiter=" | " items="${genders}"
			itemValue="value" itemLabel="label" />
	</form:form>

	<form method="post" action="<c:url value="/test/upload?"/>"
		enctype="multipart/form-data">
		<input type="text" name="name" /> <input type="file" name="file" />
		<input type="submit" />
	</form>

	<input type="button" value="asd" onclick="asd();">
	<a class="error" href="${resourceRoot}/success.html">success</a>
	<img src="${resourceRoot}/image/table_head.jpg" align="left">

	<script type="text/javascript"> 
    	$("#click").click(function() { 
            $.ajax( { 
                type : "POST", 
                url : "test/asd?", 
                data : {name:"b"}, 
                dataType: "text", 
                success : function(msg) { 
                    alert(msg); 
                } 
            }); 
        });
        
        $("#userJson").click(function() { 
        	var testStr = '{ a:{$ref:"#"},id:"root", c:{d:"e",f:{$ref:"root.c"}},b:{$ref:"#.c"},"an array":["a string"],"a string":{$ref:"#an array.0"}}'; 
        	console.log(testStr);
        	var j = eval("("+testStr+")");
        	alert(j.a == j);
           // $.ajax( { 
           //     type : "POST", 
           //     url : "test/handle51.xml?", 
           //     data : {id:'1'}, 
           //     dataType: "text", 
           //     success : function(user) { 
           //         alert(user); 
           //     } 
           // }); 
        });
        
        $("#articleJson").click(function() { 
            $.ajax( { 
                type : "POST", 
                //url : "test/handle53.xml?", 
                url : "test/showArticleMix?_content=json", 
                data : {id:$("#articleId").val()}, 
                dataType : "text", 
                success : function(data) { 
                	console.log(data);
                	//alert(data.pageItems[0].user.article[1].title);
                    //alert(article.pageItems[0].user == null);
                    //alert($(article).find('title').text());
                } 
            }); 
        });
        
        function setClientCookie(v){
        	document.cookie  = 'clientLanguage=' + v;
        }
    </script>
</body>
</html>