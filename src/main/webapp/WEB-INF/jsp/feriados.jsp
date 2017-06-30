<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Feriados" />
<title>Feriados</title>
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
						<li><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-calendar icones"></span>
								Reservas</a></li>
						<li><a href="#"><span
								class="hidden-xs showopacity glyphicon glyphicon-usd icones"></span>
								Vendas</a></li>
						<li><a href="usuarios"><span
								class="hidden-xs showopacity glyphicon glyphicon-user icones"></span>
								Usuários</a></li>
						<li class="dropdown active"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"><span
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
					<table class="table table-bordered table-condensed texto">
						<thead>
							<tr>
								<th>Data do feriado</th>
								<th>Descrição feriado</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>16/05/2017</td>
								<td>Corpus Christi</td>
							</tr>
						</tbody>
					</table>
					<form>
						<div class="row">
							<div class="col-xs-12 col-sm-5">
								<div class="form-group">
									<label for="data">Data</label> <input type="date"
										class="form-control" id="data" placeholder="Data">
								</div>
							</div>
							<div class="col-xs-10 col-sm-5">
								<div class="form-group">
									<label for="descricao">Descrição</label> <input type="text"
										class="form-control" id="descricao" placeholder="Descrição">
								</div>
							</div>
							<div class="col-xs-2 col-sm-2 pd">
								<button type="submit" class="btn btn-default mg-tp">Salvar</button>
							</div>
						</div>
						<button type="submit" class="btn btn-primary">Incluir</button>
						<button type="submit" class="btn btn-warning">Alterar</button>
						<button type="submit" class="btn btn-danger">Excluir</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>