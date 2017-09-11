<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome" />

<html>
<head>
<meta name="header" content="Entrar"/>
<title>Entrar</title>
</head>
<body>
	<div class="col-sm-4 col-sm-offset-4 text-center">
        <p>Você não está logado. Clique no botão abaixo para entrar.</p>
        <a href="<c:url value="/login"/>" class="btn btn-default fa fa-sign-in"> Entrar</a>
	</div>
</body>
</html>