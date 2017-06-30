<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Consulta" />
<title>Consulta</title>
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
						<li class="active"><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-calendar icones"></span>
								Reservas</a></li>
						<li><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-usd icones"></span>
								Vendas</a></li>
						<li><a href="usuarios"><span
								class="hidden-xs showopacity glyphicon glyphicon-user icones"></span>
								Usuários</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span
								class="glyphicon glyphicon-wrench icones"></span> Manutenção</span></a>
							<ul class="dropdown-menu forAnimate" role="menu">
								<li><a href="categorias"><span
										class="glyphicon glyphicon-edit icones"></span> Categorias</a></li>
								<li class="divider"></li>
								<li><a href="cursos"><span
										class="glyphicon glyphicon-edit icones"></span> Cursos</a></li>
								<li class="divider"></li>
								<li><a href="feriados"><span
										class="glyphicon glyphicon-edit icones"></span> Feriados</a></li>
								<li class="divider"></li>
								<li><a href="tipos"><span
										class="glyphicon glyphicon-edit icones"></span> Tipos</a></li>
							</ul></li>
						<li class="dropdown bb"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span
								class="glyphicon glyphicon-stats icones"></span> Relatórios</span></a>
							<ul class="dropdown-menu forAnimate" role="menu">
								<li><a href="#"><span
										class="glyphicon glyphicon-list-alt icones"></span> Planilhas</a></li>
								<li class="divider"></li>
								<li><a href="#"><span
										class="glyphicon glyphicon-picture icones"></span> Gráficos</a></li>
							</ul></li>
					</ul>
				</div>
			</nav>
		</div>
		<div class="col-sm-9">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form method="post">
						<div class="row">
							<div class="col-xs-10 col-sm-6">
								<div class="form-group">
									<label for="nome">Nome</label> <input type="text"
										class="form-control" id="nome" placeholder="Nome" required>
								</div>
							</div>
							<div class="col-xs-2 col-sm-2 pd">
								<button type="submit" class="btn btn-primary mg-tp">Pesquisar</button>
							</div>
						</div>
					</form>
					<table class="table table-condensed table-bordered">
						<thead>
							<tr>
								<th>#</th>
								<th>Nome</th>
								<th>22/05</th>
								<th>23/05</th>
								<th>24/05</th>
								<th>25/05</th>
								<th>26/05</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>2517</td>
								<td>Thiago Teixeira de Castro Piovan</td>
								<td><span class="glyphicon glyphicon-time"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-time"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-time"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-time"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-time"
									aria-hidden="true"></span></td>
							</tr>
							<tr>
								<td>1527</td>
								<td>Giovana Carolini</td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
							</tr>
							<tr>
								<td>7521</td>
								<td>Victor Ribeiro</td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
							</tr>
							<tr>
								<td>5217</td>
								<td>Matheus Guermandi</td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-download"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-download"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-remove-circle"
									aria-hidden="true"></span></td>
							</tr>
							<tr>
								<td>1257</td>
								<td>Gabriel Orbeli</td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-upload"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-upload"
									aria-hidden="true"></span></td>
								<td><span class="glyphicon glyphicon-ok-circle"
									aria-hidden="true"></span></td>
							</tr>
						</tbody>
					</table>
					<div class="text-center">
						<ul class="pagination">
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>