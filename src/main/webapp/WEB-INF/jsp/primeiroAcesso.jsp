<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/primeiroAcesso/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation" />

<html>
<head>
<meta name="header" content="Primeiro Acesso"/>
<title>Primeiro Acesso</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#primeiroAcesso").validate({
		         rules : {
		        	 categoria : { required : true },
		             raMatricula : { required : true },
		             curso : { required : true }
		         }
		     });
		     $("#categoria").focus();
		 });
  	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
		<div class="panel-body">
			<div class="page-header" style="margin-top: 10px;">
				<h3>
					<strong>Dados do primeiro acesso</strong>
				</h3>
			</div>

			<form:form action="${actionSalvar}" modelAttribute="primeiroAcesso">			  	
		        <h4>Oops! Vemos que é a primeira vez que acessa ao nosso sistema. Vamos preencher alguns dados?</h4>
		        <br>		        
		        <div class="row">
		          <spring:bind path="categoria">
		            <div class="form-group col-xs-12 col-offset-xs-0 col-sm-6 col-sm-offset-3">
		              <label for="categoria" class="control-label">Categoria</label>
			          <form:select path="categoria" class="form-control">
					  	<form:option value="" label="----- Selecione uma categoria -----"/>
	                      <form:options items="${categoria}" itemLabel="descricao" itemValue="id" />
					  </form:select>
					</div>
				  </spring:bind>
		        </div>
		        <div class="row">
		          <spring:bind path="curso">
		            <div class="form-group col-xs-12 col-offset-xs-0 col-sm-6 col-sm-offset-3">
		              <label for="curso" class="control-label">Curso</label>
			          <form:select path="curso" class="form-control">
					  	<form:option value="" label="----- Selecione um curso -----"/>
	                      <form:options items="${curso}" itemLabel="descricao" itemValue="id" />
					  </form:select>
					</div>
				  </spring:bind>
		        </div>
		        <div class="row">
		          <spring:bind path="raMatricula">
		            <div class="form-group col-xs-12 col-offset-xs-0 col-sm-6 col-sm-offset-3">
		              <label for="raMatricula" class="control-label">RA / Matricula</label>
		              <form:input path="raMatricula" class="form-control" placeholder="Digite seu número de RA / Matricula"/>
					</div>
				  </spring:bind>
		        </div>
		        <div class="form-group col-x-12 col-md-12" style="text-align: center; margin-top: 25px;">
		          <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> Salvar</button>
		        </div>
			</form:form>
		</div>
	</div>
</body>
</html>