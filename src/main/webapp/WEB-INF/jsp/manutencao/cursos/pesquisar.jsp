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
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title pull-left">Pesquisa por cursos</div>
					<div class="panel-title pull-right">
						<a href="incluir">Novo curso</a>
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
									<td class="text-center"><a href="editar/${lista.id}"
										class="btn btn-default btn-xs" title="Editar"> <span
											class="glyphicon glyphicon-edit"> </span>
									</a>
										<button
											class="glyphicon glyphicon-trash btn btn-default btn-xs"
											data-toggle="modal" data-target="#myModal${lista.id}"
											title="Excluir"></button> <form:form
											action="excluir/${lista.id}" method="post">
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
	<script type="text/javascript"
		src="<c:url value="/resources/js/manutencao.js" />"></script>
</body>
</html>