<html>
	<head>
		<title>testSpring中文</title>
	</head>
	<body>
		testSpring中文
		<table>
			<#list userList as user>
				<tr>
					<td>${user.name}</td>
				</tr>
			</#list>
		</table>
	</body>
</html>