<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Transferências" />
<title>Transferências</title>
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
						<li><a href="conta"><span
								class="hidden-xs showopacity glyphicon glyphicon-user icones"></span>
								Conta</a></li>
						<li><a href="historicorefeicoes"><span
								class="hidden-xs showopacity glyphicon glyphicon-calendar icones"></span>
								Histórico</a></li>
						<li><a href="reservarefeicoes"><span
								class="hidden-xs showopacity glyphicon glyphicon-cutlery icones"></span>
								Reserva</a></li>
						<li class="active"><a href="transferencias"><span
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
				<div class="panel-body">
					<h3>Selecione o(s) dia(s) que deseja transferir:</h3>
					<p>*Somente serão mostrados os dias com reserva e pagamento
						efetuados.</p>
					<br> <br>
					<form action="#" class="form-horizontal">
						<div class="text-center">
							<input type="checkbox" id="segunda" value="segunda">&nbsp;Segunda-feira
							- 22/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Terça-feira
							- 23/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Quarta-feira
							- 24/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Quinta-feira
							- 25/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Sexta-feira
							- 26/05
						</div>
						<br> <br>
						<h4>Escreva os dados da pessoa a quem deseja tranferir:</h4>
						<br> <br>
						<div class="form-group">
							<div class="control-label col-sm-5" for="cpf">CPF:</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="cpf">
							</div>
						</div>
						<div class="form-group">
							<div class="control-label col-sm-5" for="cpf">Data de
								Nascimento:</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="data_nascimento">
							</div>
						</div>
						<br>
						<button type="submit" class="btn btn-primary center-block"><span class="glyphicon glyphicon-send" aria-hidden="true"></span> Transferir</button>
					</form>
				</div>
			</div>

		</div>
	</div>
</body>
</html>