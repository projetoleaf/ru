<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Início"/>
<title></title>
</head>
<body>
	<div class="col-sm-4 col-sm-offset-4 text-center">
        <p>Você não está logado. Clique no botão abaixo apara entrar.</p>
        <a href="<c:url value="/login"/>" class="btn btn-default glyphicon glyphicon-log-in">Entrar</a>
	</div>
</body>
</html>