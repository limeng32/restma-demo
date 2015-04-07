<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>新增用户</title>
</head>
<body>
	<fmt:message key="testUpdate.title" />
	<br />
	<fmt:formatDate value="${article.updateTime }" pattern="yyyy-MM-dd" />
	：${user.name }
	<form method="post" action="<c:url value='/testUpdate/update?' />">
		姓名：<input type="text" name="name" />
		<form:errors path="user.name" />
		<br /> 地址：<input type="text" name="address" /><br /> 昵称：<input
			type="text" name="nickname" /><br /> 生日：<input type="text"
			name="birthday" value="2014-12-25" />
		<form:errors path="user.birthday" />
		<br /> 薪水：<input type="text" name="salary" value="9000" />
		<form:errors path="user.salary" />
		<br /> 状态：<input type="text" name="state" value="a" />
		<form:errors path="user.state" />
		<br /> <input type="submit" value="asd">
	</form>
</body>
</html>