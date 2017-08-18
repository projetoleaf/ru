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
				<div class="col-sm-4">
					<p><b>CPF</b></p>
					<p>${usuario.cpf}</p>
				</div>
				<div class="col-sm-4">
					<p><b>Nome</b></p>
					<p>${usuario.nome}</p>
				</div>
				<div class="col-sm-4">
					<p><b>Data de nascimento</b></p>
					<p>23 de Janeiro de 1987</p>
				</div>	
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-sm-12">
				<h4>Dados Acadêmicos</h4>
				<div class="col-sm-4">
					<p><b>Matricula</b></p>
					<p>1111111111</p>
				</div>
				<div class="col-sm-4">
					<p><b>Categoria</b></p>
					<p>Discente de Graduação</p>
				</div>
				<div class="col-sm-4">
					<p><b>Refeição</b></p>
					<p>Tradicional  <a href="#" title="Editar"> <span class="fa fa-pencil-square-o"></span></a></p>
				</div>	
				<div class="col-sm-4">
					<p><b>Curso</b></p>
					<p>Ciência da Computação</p>
				</div>
				<div class="col-sm-8">
					<p><b>Período</b></p>
					<p>Integral</p>
				</div>						
			</div>
		</div>
		<hr />
		<div class="row">
			<div class="col-sm-12">
				<h4>Dados Financeiros</h4>
				<div class="col-sm-6">
					<p><b>Créditos</b></p>
					<p>R$ 50,75</p>
				</div>
				<div class="col-sm-6">
					<p><b>Última reserva</b></p>
					<p>27 de Agosto de 2017</p>
				</div>		
			</div>
		</div>
	  </div>
	</div>
</body>
</html>