<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
					<div class="panel-title pull-left">
			         Novo curso
			         </div>
			        <div class="panel-title pull-right"><a href="curso">Voltar para a pesquisa</a></div>
			        <div class="clearfix"></div>
				</div>
				<div class="panel-body">					
					<form:form method="POST" action="incluirCurso" modelAttribute="curso" class="form-horizontal">
						<div class="form-group">
							<label for="descricao" class="col-sm-2 control-label">Descrição</label>
							<div class="col-sm-4">
								<form:input type="text"	class="form-control" path="descricao" placeholder="Descrição" />
							</div>
						</div>
						<div class="form-group">
							<label for="periodo" class="col-sm-2 control-label">Período</label>
							<div class="col-sm-4">
								<form:select path="periodo" class="btn btn-default"
									required="required">
									<option value="0">-</option>
								</form:select>
							</div>
						</div>
						 <div class="form-group">
						    <div class="col-sm-offset-2 col-sm-10">
						      <button type="submit" class="btn btn-primary">Salvar</button>
						    </div>
						 </div>				
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>