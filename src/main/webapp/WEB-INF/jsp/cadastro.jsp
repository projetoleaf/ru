<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="header" content="Cadastro" />
<title>Cadastro</title>
<link href="<c:url value="/resources/css/ru.css"/>" rel="stylesheet" />
<link href="<c:url value="/resources/css/bootstrap-datepicker3.min.css"/>" rel="stylesheet" />
</head>
<body>
	<div class="row pt">
		<div class="col-sm-8 col-sm-offset-2">
			<div class="panel panel-primary">
				<div class="panel-heading text-center subtitulo">Insira seus
					dados para cadastro</div>
				<div class="panel-body">
					<form:form method="POST" action="salvar" class="form-horizontal" modelAttribute="usuario">
						<div class="form-group">
							<label for="cpf" class="col-sm-3 control-label">CPF</label>
							<div class="col-sm-6">
								<form:input type="text" class="form-control" path="cpf"
									placeholder="Digite seu CPF" required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for="cpf" class="col-sm-3 control-label">Email</label>
							<div class="col-sm-6">
								<form:input type="email" class="form-control" path="email"
									placeholder="Digite seu email" required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for="senha" class="col-sm-3 control-label">Senha</label>
							<div class="col-sm-6">
								<form:input type="password" class="form-control" path="senha"
									placeholder="Digite sua senha" required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for="nome" class="col-sm-3 control-label">Nome</label>
							<div class="col-sm-6">
								<form:input type="text" class="form-control" path="nome"
									placeholder="Digite seu nome completo" required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for="data_nascimento" class="col-sm-3 control-label">Data
								de nascimento</label>
							<div class="col-sm-6">
								<form:input type="text" class="form-control"
									path="data_nascimento" required="required"
									placeholder="dd/mm/aaaa" />
							</div>
						</div>
						<div class="form-group">
							<label for="matricula" class="col-sm-3 control-label">Matricula</label>
							<div class="col-sm-6">
								<form:input type="text" class="form-control" path="matricula"
									placeholder="Digite sua matricula" required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for="curso" class="col-sm-3 control-label">Curso</label>
							<div class="col-sm-6">
							
								<form:select path="curso" class="form-control">
								   <form:option value="" label="----- Selecione uma categoria -----"/>
                                   <form:options items="${cursos}" itemLabel="descricao" itemValue="id" />
								</form:select>
							
							</div>
						</div>
						<div class="form-group">
							<label for="tipo" class="col-sm-3 control-label">Tipo</label>
							<div class="col-sm-6">
							
								<%-- <form:select path="tipo" class="btn btn-default"
									required="required">
									<c:forEach var="lista" items="${tipos}" varStatus="status">
									<option value="0">-</option>
									<option value="${lista.descricao}"></option>
									</c:forEach>	
								</form:select> --%>
							
							</div>
						</div>
						<div class="text-center">
							<button type="submit" class="btn btn-primary">
								<span class="glyphicon glyphicon-send" aria-hidden="true"></span>
								Enviar
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-3.2.1.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/bootstrap-datepicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/bootstrap-datepicker.pt-BR.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/data.js" />"></script>
</body>
</html>