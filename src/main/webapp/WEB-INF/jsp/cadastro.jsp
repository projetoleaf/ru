<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="jquery.validation,jquery.inputmask,jquery.datetimepicker" />

<html>
<head>
<meta name="header" content="Cadastro" />
<title>Cadastro</title>
</head>
<body>
	<script type="text/javascript">
	  $(document).ready(function() {
	      var formValidator = $("#usuario").validate({
	          rules : {
	        	  cpf : { required : true },
	        	  email : { required : true },
	        	  senha : { required : true },
	        	  nome : { required : true },
	              dataNascimento : { required : true },
	              matricula : { required : true },
	              tipo : { required : true },	              
	              curso : { required : true }
	          }
	      });
	      $("#cpf").focus();
	      $("#div-data-nascimento").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
	      $("#dataNascimento").inputmask("99/99/9999");
	      $("#cpf").inputmask("999.999.999-99");
	  });
	</script>
	<form:form action="salvar" modelAttribute="usuario">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="usuario"/></jsp:include>
	          <h3>
	            <strong>Cadastro</strong>
	          </h3>
	        </div>
	        <div class="row">
	          <spring:bind path="cpf">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="cpf" class="control-label">CPF</label>
	              <form:input path="cpf" class="form-control" placeholder="Digite o seu CPF"/>
	              <span class="has-error"><form:errors path="cpf" class="help-block"/></span>
	            </div>
	          </spring:bind>
	         </div>
	         <div class="row">
	          <spring:bind path="email">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="email" class="control-label">Email</label>
	              <form:input type="email" path="email" class="form-control" placeholder="Digite o seu email"/>
	              <span class="has-error"><form:errors path="email" class="help-block"/></span>
	            </div>
	          </spring:bind>
	          <spring:bind path="senha">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="senha" class="control-label">Senha</label>
	              <form:input type="password" path="senha" class="form-control" placeholder="Digite a sua senha"/>
	              <span class="has-error"><form:errors path="senha" class="help-block"/></span>
	            </div>
	          </spring:bind>
	         </div>
	         <div class="row">
	          <spring:bind path="nome">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="nome" class="control-label">Nome</label>
	              <form:input path="nome" class="form-control" placeholder="Digite o seu nome completo"/>
	              <span class="has-error"><form:errors path="nome" class="help-block"/></span>
	            </div>
	          </spring:bind>
	          <spring:bind path="dataNascimento">
	            <div class="form-group col-xs-12 col-md-3">
	              <label for="dataNascimento" class="control-label">Data de nascimento</label>
		          <div class="input-group date" id="div-data-nascimento">
		          	<form:input path="dataNascimento" class="form-control" extra="placeholder=Data de nascimento" />
		            <span class="input-group-addon">
		                <span class="glyphicon glyphicon-calendar"></span>
		            </span>
		          </div>
	            </div>
	          </spring:bind>
	          <spring:bind path="matricula">
	            <div class="form-group col-xs-12 col-md-3 ${status.error ? 'has-error' : ''}">
	              <label for="matricula" class="control-label">Matricula</label>
	              <form:input path="matricula" class="form-control" placeholder="Digite a sua matricula"/>
	              <span class="has-error"><form:errors path="matricula" class="help-block"/></span>
	            </div>
	          </spring:bind>
	         </div>
	         <div class="row">
	          <spring:bind path="tipo">
	            <div class="form-group col-xs-12 col-md-3">
	              <label for="tipo" class="control-label">Tipo</label>
	              <form:select path="tipo" class="form-control">
				  	<form:option value="" label="----- Selecione um tipo -----"/>
	                <form:options items="${tipos}" itemLabel="descricao" itemValue="id" />
				  </form:select>
	            </div>
	          </spring:bind>
	          <spring:bind path="curso">
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="curso" class="control-label">Curso</label>
	              <form:select path="curso" class="form-control">
				  	<form:option value="" label="----- Selecione um curso -----"/>
	                <form:options items="${cursos}" itemLabel="descricao" itemValue="id" />
				  </form:select>
	            </div>
	          </spring:bind>
	          <spring:bind path="curso">
	            <div class="form-group col-xs-12 col-md-3">
	             <label for="curso" class="control-label">Período</label>
	              <form:select path="curso" class="form-control">
	                <option value="">----- Selecione um período -----</option>
					<option value="Diurno">Diurno</option>
					<option value="Integral">Integral</option>
					<option value="Matutino">Matutino</option>
					<option value="Vespertino">Vespertino</option>
				  	<option value="Noturno">Noturno</option>
				  </form:select>
	            </div>
	          </spring:bind>
	         </div>
	      </div>
	      <div class="form-group col-x-12 col-md-12" style="text-align: center; margin-top: 25px;">
	        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> Salvar</button>
	      </div>
	    </div>
	</form:form>
</body>
</html>