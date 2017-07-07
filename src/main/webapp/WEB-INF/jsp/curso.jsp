<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta name="header" content="Cursos" />
<title>Cursos</title>
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
								class="glyphicon glyphicon-wrench icones"></span> Manutenção</a>
							<ul class="dropdown-menu forAnimate" role="menu">
								<li><a href="categoria"><span
										class="glyphicon glyphicon-edit icones"></span> Categorias</a></li>
								<li class="divider"></li>
								<li><a href="curso"><span
										class="glyphicon glyphicon-edit icones"></span> Cursos</a></li>
								<li class="divider"></li>
								<li><a href="feriado"><span
										class="glyphicon glyphicon-edit icones"></span> Feriados</a></li>
								<li class="divider"></li>
								<li><a href="tipo"><span
										class="glyphicon glyphicon-edit icones"></span> Tipos</a></li>
							</ul></li>
						<li class="dropdown bb"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span
								class="glyphicon glyphicon-stats icones"></span> Relatórios</a>
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
				<div class="panel-heading">
					<div class="panel-title pull-left">Pesquisa por cursos</div>
					<div class="panel-title pull-right">
						<a href="adicionarCurso">Novo curso</a>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-6">
							<div class="input-group">
								<input type="text" class="form-control" id="filtro"
									onkeyup="myFunction()"
									placeholder="Qual curso você está procurando?"> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</div>
					</div>

					<table id="tabela"
						class="table table-bordered table-condensed texto mg-tp">
						<thead>
							<tr>
								<th class="col-id">#</th>
								<th>Descrição</th>
								<th>Período</th>
								<th class="col-btn"></th>
							</tr>
						</thead>

						<c:forEach var="lista" items="${cursos}" varStatus="status">
							<tbody>
								<tr>
									<td class="text-center">${lista.id}</td>
									<td>${lista.descricao}</td>
									<td>${lista.periodo}</td>
									<td class="text-center"><a href="editarCurso/${lista.id}"
										class="btn btn-default btn-xs" title="Editar"> <span
											class="glyphicon glyphicon-edit"> </span>
									</a>
										<button
											class="glyphicon glyphicon-trash btn btn-default btn-xs"
											data-toggle="modal" data-target="#myModal${lista.id}"
											title="Excluir"></button> <form:form
											action="excluirCurso/${lista.id}" method="post">
											<!-- Modal -->
											<div id="myModal${lista.id}" class="modal fade text-left"
												role="dialog">
												<div class="modal-dialog">

													<!-- Modal content-->
													<div class="modal-content">
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal">&times;</button>
															<h4 class="modal-title">Você tem certeza?</h4>
														</div>
														<div class="modal-body">
															<p>
																Tem certeza que deseja excluir o curso <b>${lista.descricao}</b>?
															</p>
														</div>
														<div class="modal-footer">
															<button type="button" class="btn btn-default"
																data-dismiss="modal">Cancelar</button>
															<button type="submit" class="btn btn-primary">Excluir</button>
														</div>
													</div>
												</div>
											</div>
										</form:form></td>
								</tr>
							</tbody>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/js/manutencao.js" />"></script>
</body>
</html>