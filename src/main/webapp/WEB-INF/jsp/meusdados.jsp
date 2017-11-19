<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome"/>

<html>
<head>
	<meta name="header" content="Meus Dados"/>
	<title>Meus Dados</title>
	<link rel="stylesheet" href="<c:url value="resources/css/geral.css"/>" type="text/css"/>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<script type="text/javascript">
			$(document).ready(function() {
				$("#curso").text("${curso.curso.descricao}".replace(/-(.*)/g,''));
			});
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header">
          <h3>
            <strong>Meus Dados</strong>
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
		<hr/>
		<div class="row">
			<div class="col-sm-12">
				<h4>Dados Acadêmicos</h4>
				<div class="col-sm-6">
					<p><b>RA / Matrícula</b></p>
					<p>${categoria.raMatricula}</p>
				</div>
				<div class="col-sm-6">
					<p><b>Categoria</b></p>
					<p>${categoria.categoria.descricao}</p>
				</div>
				<c:if test="${curso.curso.descricao != null}">
					<div class="col-sm-6">
						<p><b>Curso</b></p>
						<p id="curso"></p>
					</div>
					<div class="col-sm-6">
						<p><b>Período</b></p>
						<p>${curso.curso.periodo}</p>
					</div>		
				</c:if>			
			</div>
		</div>
		<hr/>
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
	<jsp:include page="verifica.jsp"/>
</body>
</html>