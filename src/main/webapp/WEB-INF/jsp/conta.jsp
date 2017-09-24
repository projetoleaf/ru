<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<dandelion:bundle includes="font-awesome" />

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
			<div class="col-sm-12">
				<h4>Dados Pessoais</h4>
				<div class="col-sm-6">
					<p><b>CPF</b></p>
					<p>${cliente.cpf}</p>
				</div>
				<div class="col-sm-6">
					<p><b>Nome</b></p>
					<p>${cliente.nome}</p>
				</div>	
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-sm-12">
				<h4>Dados Acadêmicos</h4>
				<div class="col-sm-6">
					<p><b>RA / Matricula</b></p>
					<p>${categoria.raMatricula}</p>
				</div>
				<div class="col-sm-6">
					<p><b>Categoria(s)</b></p>
					<p>${categoria.categoria.descricao}</p>
				</div>
				<div class="col-sm-6">
					<p><b>Curso</b></p>
					<p>${curso.curso.descricao}</p>
				</div>
				<div class="col-sm-6">
					<p><b>Período</b></p>
					<p>${curso.curso.periodo}</p>
				</div>					
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-sm-12">
				<h4>Dados Financeiros</h4>
				<div class="col-sm-6">
					<p><b>Créditos</b></p>
					<p>${creditos}</p>
				</div>
				<div class="col-sm-6">
					<p><b>Última reserva</b></p>
					<p>${ultimaReserva}</p>
				</div>		
			</div>
		</div>
	  </div>
	</div>
</body>
</html>