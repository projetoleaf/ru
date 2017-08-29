<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/categorias/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage" content="<li><a href='<c:url value="/categorias"/>'>Categorias</a></li>" />
<title>Cadastro de Categorias</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#categoria").validate({
		         rules : {
		             descricao : { required : true, maxlength: 100 },
		             valorSemSubsidio : { required : true, maxlength : 17 },
		             valorComSubsidio : { required : true, maxlength : 17 }
		         }
		     });
		     $("#descricao").focus();
		     $("#valorSemSubsidio").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
		     $("#valorComSubsidio").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
		 });
  	</script>
  	<form:form action="${actionSalvar}" modelAttribute="categoria">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="categoria"/></jsp:include>
	          <h3>
	            <strong>Categoria</strong>
	          </h3>
	        </div>
	        <div class="row">
	          <spring:bind path="descricao">
	            <div class="form-group col-xs-12 col-md-12 ${status.error ? 'has-error' : ''}">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição da categoria"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
	            </div>
	          </spring:bind>
	        </div>
	        <div class="row">
	          <spring:bind path="valorSemSubsidio">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="valorSemSubsidio" class="control-label">Valor sem subsídio (R$)</label>
	              <form:input path="valorSemSubsidio" class="form-control" placeholder="Digite o valor sem subsídio"/>
	              <span class="has-error"><form:errors path="valorSemSubsidio" class="help-block"/></span>
	            </div>
	          </spring:bind>
	          <spring:bind path="valorComSubsidio">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="valorComSubsidio" class="control-label">Valor com subsídio (R$)</label>
	              <form:input path="valorComSubsidio" class="form-control" placeholder="Digite o valor com subsídio"/>
	              <span class="has-error"><form:errors path="valorComSubsidio" class="help-block"/></span>
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