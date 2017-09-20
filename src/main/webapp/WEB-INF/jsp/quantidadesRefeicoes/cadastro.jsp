<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/quantidadesRefeicoes/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage" content="<li><a href='<c:url value="quantidadesRefeicoes"/>'>Quantidades das Refeições</a></li>" />
<title>Cadastro de Quantidades das Refeições</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#quantidadeRefeicao").validate({
		         rules : {
		             subsidiada : { required : true, digits: true },
		             custo : { required : true, digits: true }
		         }
		     });
		     $("#subsidiada").focus();
		 });
  	</script>
  	<form:form action="${actionSalvar}" modelAttribute="quantidadeRefeicao">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="quantidadeRefeicao"/></jsp:include>
	          <h3>
	            <strong>Quantidade da Refeição</strong>
	          </h3>
	        </div>
	        <div class="row">
	          <spring:bind path="subsidiada">
	            <div class="form-group col-xs-12 col-md-12 ${status.error ? 'has-error' : ''}">
	              <label for="subsidiada" class="control-label">Subsidiada</label>
	              <form:input path="subsidiada" class="form-control" placeholder="Digite a quantidade de subsidiada"/>
	              <span class="has-error"><form:errors path="subsidiada" class="help-block"/></span>
	            </div>
	          </spring:bind>
	        </div>
	        <div class="row">
	          <spring:bind path="custo">
	            <div class="form-group col-xs-12 col-md-12 ${status.error ? 'has-error' : ''}">
	              <label for="custo" class="control-label">Custo</label>
	              <form:input path="custo" class="form-control" placeholder="Digite a quantidade de custo"/>
	              <span class="has-error"><form:errors path="custo" class="help-block"/></span>
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