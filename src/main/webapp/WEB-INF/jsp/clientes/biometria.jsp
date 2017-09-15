<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/clientes/salvarBiometria"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask" />

<html>
<head>
<meta name="header" content="Cadastro de Biometria" />
<meta name="previouspage" content="<li><a href='<c:url value="/clientes"/>'>Clientes</a></li>" />
<title>Cadastro de Biometria</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#cliente").validate({
		         rules : {
		             biometria : { required : true }
		         }
		     });
		     $("#biometria").focus();
		 });
  	</script> 
  	<form:form action="${actionSalvar}" modelAttribute="cliente">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="cliente"/></jsp:include>
	          <h3>
	            <strong>Cadastro de Biometria</strong>
	          </h3>
	        </div>
	         <div class="row">
	          <spring:bind path="biometria">
	            <div class="form-group col-xs-12 col-md-12 ${status.error ? 'has-error' : ''}">
	              <label for="biometria" class="control-label">Biometria</label>
	              <form:input path="biometria" class="form-control" placeholder="Digite a descrição da biometria"/>
	              <span class="has-error"><form:errors path="biometria" class="help-block"/></span>
	            </div>
	          </spring:bind>
	        </div>
	        <div class="form-group col-x-12 col-md-12" style="text-align: center; margin-top: 25px;">
	          <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> Salvar</button>
	        </div>
	      </div>
	    </div>
	</form:form>
</body>
</html>