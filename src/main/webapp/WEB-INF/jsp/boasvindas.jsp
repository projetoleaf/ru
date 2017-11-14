<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="sweetalert2" />

<html>
<head>
	<meta name="header" content="Boas Vindas"/>
	<title>Boas Vindas</title>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<div class="col-sm-4 col-sm-offset-4 text-center">
		<p>Seja bem-vindo(a) ao novo Sistema RU!</p>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>