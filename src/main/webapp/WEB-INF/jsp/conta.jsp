<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Conta" />
<title>Conta</title>
</head>
<body>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <h3>
	            <strong>Conta</strong>
	          </h3>
	        </div>
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
</body>
</html>