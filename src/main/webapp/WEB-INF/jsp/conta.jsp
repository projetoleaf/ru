<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Conta" />
<title>Conta</title>
<link href="<c:url value="/resources/css/ru.css"/>" rel="stylesheet" />
</head>
<body>
	<div class="row">
		<div class="col-sm-3">
			<nav class="navbar navbar-default sidebar panel panel-primary"
				role="navigation">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#bs-sidebar-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-sidebar-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="active"><a href="conta"><span
								class="hidden-xs showopacity glyphicon glyphicon-user icones"></span>
								Conta</a></li>
						<li><a href="historicorefeicoes"><span
								class="hidden-xs showopacity glyphicon glyphicon-calendar icones"></span>
								Histórico</a></li>
						<li><a href="reservarefeicoes"><span
								class="hidden-xs showopacity glyphicon glyphicon-cutlery icones"></span>
								Reserva</a></li>
						<li><a href="transferencias"><span
								class="hidden-xs showopacity glyphicon glyphicon-transfer icones"></span>
								Transferências</a></li>
						<li><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-usd icones"></span>
								Saldo</a></li>
						<li><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-cutlery icones"></span>
								Remanescentes</a></li>
						<li class="bb"><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-alert icones"></span>
								Penalidades</a></li>
					</ul>
				</div>
			</nav>
		</div>
		<div class="col-sm-9">
			<div class="panel panel-primary">
				<div class="panel-heading">Olá, @Fulano!</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-4">
							<legend class="conta">Dados Pessoais</legend>
							<div class="form-group">
								<label for="nome">Nome</label> <input type="text"
									class="form-control" id="nome" placeholder="Nome" required>
							</div>
							<div class="form-group">
								<label for="cpf">CPF</label> <input type="text"
									class="form-control" id="cpf" placeholder="CPF" required>
							</div>
							<div class="form-group">
								<label for="datanasc">Data de nascimento</label> <input
									type="date" class="form-control" id="datanasc" required>
							</div>
							<div class="text-center">
								<button type="button" class="btn btn-primary">Editar
									dados</button>
								<button type="button" class="btn btn-warning">Editar
									senha</button>
							</div>
						</div>
						<div class="col-sm-4">
							<legend class="conta">Dados Acadêmicos</legend>
							<div class="form-group">
								<label for="categoria">Categoria</label> <input type="text"
									class="form-control" id="categoria" placeholder="Categoria">
							</div>
							<div class="form-group">
								<label for="ra">RA | Matrícula</label> <input type="text"
									class="form-control" id="ra" placeholder="RA | Matrícula">
							</div>
							<div class="form-group">
								<label for="curso">Curso</label> <input type="text"
									class="form-control" id="curso" placeholder="Curso">
							</div>
							<div class="form-group">
								<label for="periodo">Período</label> <input type="text"
									class="form-control" id="periodo" placeholder="Período">
							</div>
							<div class="text-center">
								<button type="button" class="btn btn-primary">Editar
									dados</button>
							</div>
						</div>
						<div class="col-sm-4">
							<legend class="conta">Dados Financeiros</legend>
							<div class="form-group">
								<label for="saldo">Saldo</label> <input type="text"
									class="form-control" id="saldo" placeholder="R$ 25,90">
							</div>
							<div class="form-group">
								<label for="ultReserva">Última Reserva</label> <input
									type="text" class="form-control" id="ultReserva">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>