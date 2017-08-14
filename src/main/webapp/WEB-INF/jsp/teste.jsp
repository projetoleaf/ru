<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Spring MVC checkbox</title>
</head>

<body>
	<h2>As datas selecionadas são:</h2>
	<br>
	<c:forEach items="${datasSelecionadas.data}" var="dias">  
		<c:out value="${dias}"/><br>
	</c:forEach>
</body>