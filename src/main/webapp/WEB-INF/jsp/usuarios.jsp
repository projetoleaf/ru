<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Usuários" />
<title>Usuários</title>
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
						<li class="active"><a href="usuarios"><span
								class="hidden-xs showopacity glyphicon glyphicon-user icones"></span>
								Usuários</a></li>
						<li class="dropdown"><a href="#"
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
				<div class="panel-heading">Usuarios</div>
				<div class="panel-body">
					<div>
						<table>
							<tr>
								<td><h4>Dados do usuario:</h4></td>
							</tr>
							<tr>
								<td>
									<p>Nome:</p>
								</td>
								<td>
									<p>Matheus Guermandi Ribeiro</p>
								</td>
							</tr>
							<tr>
								<td>
									<p>Tipo da conta:</p>
								</td>
								<td>
									<p>Discente</p>
								</td>
							</tr>
							<tr>
								<td>
									<p>Saldo:</p>
								</td>
								<td>
									<p>4 Refeições</p>
								</td>
							</tr>
						</table>
						<h4>
							<p>Enviar penalidade ao usuaruio</p>
						</h4>
						<br>
						<form class="form-horizontal" action="/action_page.php">
							<div class="form-group">
								<label class="control-label col-sm-1" for="email">Email:</label>
								<div class="col-sm-5">
									<input type="email" class="form-control" id="email"
										placeholder="Email" name="email">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-1" for="pwd">Mensagem:</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" id="msg"
										placeholder="Mensagem" name="msg">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-1 col-sm-5">
									<button type="submit" class="btn btn-primary">Entrar</button>
									<button type="reset" class="btn btn-primary">Limpar</button>
								</div>
							</div>
						</form>
						<hr>
						<table>
							<tr>
								<div>
									<button type="button" class="btn btn-primary">Excluir
										Conta</button>
									<button type="button" class="btn btn-primary">Adicionar
										conta como ADM</button>
									<button type="button" class="btn btn-primary">Alterar
										saldo</button>
								</div>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>