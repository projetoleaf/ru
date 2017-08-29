<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/primeiroAcesso/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask,jquery.datetimepicker" />

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
		             data : { required : true }
		         }
		     });
		     $("#dataNascimento").focus();
		     $("#div-data").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
		      $("#dataNascimento").inputmask("99/99/9999");
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
		          <spring:bind path="dataNascimento">
		            <div class="form-group col-xs-12 col-md-6">
		              <label for="dataNascimento" class="control-label">Data de nascimento</label>
			          <div class="input-group date" id="div-data">
			          	<form:input path="dataNascimento" class="form-control" extra="placeholder=Data de nascimento" />
			            <span class="input-group-addon">
			                <span class="glyphicon glyphicon-calendar"></span>
			            </span>
			          </div>
		            </div>
		          </spring:bind>
		        </div>
		        <div class="form-group col-x-12 col-md-12" style="text-align: center; margin-top: 25px;">
		          <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> Próximo</button>
		        </div>
			</form:form>
		</div>
	</div>
</body>
</html>