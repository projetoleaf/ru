<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Histórico" />
<title>Histórico</title>
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
						<li class="active"><a href="historicorefeicoes"><span
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
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-12 col-sm-5">
							<div class="form-group">
								<label for="data-ini">Data inicial</label> <input type="date"
									class="form-control" id="data-ini">
							</div>
						</div>
						<div class="col-xs-9 col-sm-5">
							<div class="form-group">
								<label for="data-fin">Data final</label> <input type="date"
									class="form-control" id="data-fin">
							</div>
						</div>
						<div class="col-xs-3 col-sm-2 pd">
							<button type="submit" class="btn btn-primary mg-tp">
								<span class="glyphicon glyphicon-search"></span> Buscar
							</button>
						</div>
					</div>
					<table class="table table-condensed table-bordered texto">
						<thead>
							<tr>
								<th>Nº.</th>
								<th>Data</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>16/05/2017</td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
							</tr>
							<tr>
								<td>2</td>
								<td>17/05/2017</td>
								<td><span class="glyphicon glyphicon-time"
									aria-hidden="true"></span></td>
							</tr>
							<tr>
								<td>3</td>
								<td>18/05/2017</td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
							</tr>
						</tbody>
					</table>
					<div class="row">
						<div class="col-sm-9 text-center">
							<ul class="pagination pagination">
								<li><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">></a></li>
							</ul>
						</div>
						<div class="col-sm-3">
							<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;Pago<br>
							<span class="glyphicon glyphicon-time" aria-hidden="true"></span>&nbsp;Aguardando
							Pagamento<br> <span
								class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>&nbsp;Não
							Pago<br> <span class="glyphicon glyphicon-download"
								aria-hidden="true"></span>&nbsp;Transferido<br> <span
								class="glyphicon glyphicon-upload" aria-hidden="true"></span>&nbsp;Transferente
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>