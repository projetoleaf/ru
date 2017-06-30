<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Reserva" />
<title>Reserva</title>
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
						<li class="active"><a href="reservarefeicoes"><span
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
				<div class="panel-body">
					<h3>Selecione o(s) dia(s) que deseja reservar:</h3>
					<br> <br>
					<div class="text-center">
						<input type="checkbox">&nbsp;Segunda-feira - 20/05&nbsp; <input
							type="checkbox">&nbsp;Terça-feira - 21/05&nbsp; <input
							type="checkbox">&nbsp;Quarta-feira - 22/05&nbsp; <input
							type="checkbox">&nbsp;Quinta-feira - 23/05&nbsp; <input
							type="checkbox">&nbsp;Sexta-feira - 24/05
					</div>
					<div class="text-center">
						<br> <br>
						<button type="submit" class="btn btn-primary" data-toggle="modal"
							data-target="#myModal">
							<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span>
							Reservar
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Reservas</h4>
				</div>
				<div class="modal-body">
					<p>Confirme os dias da sua reserva...</p>
					<p>&#10004...</p>
					<p>&#10004...</p>
					<p>&#10004...</p>
				</div>
				<div class="modal-footer">
					<div class="text-center">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary" data-toggle="modal"
							data-target="#myModal">Confirmar reserva</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>