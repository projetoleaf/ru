<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="actionSalvar"><c:url value="/feriados/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask,jquery.datetimepicker" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage" content="<li><a href='<c:url value="/feriados"/>'>Feriados</a></li>" />
<title>Cadastro de Feriados</title>
</head>
<body>
	<script type="text/javascript">
	  $(document).ready(function() {
	      var formValidator = $("#feriado").validate({
	          rules : {
	              descricao : { required : true },
	              data : { required : true }
	          }
	      });
	      $("#data").focus();
	      $("#div-data").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
	      $("#data").inputmask("99/99/9999");
	  });
	</script>
	<form:form action="${actionSalvar}" modelAttribute="feriado">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="feriado"/></jsp:include>
	          <h3>
	            <strong>Feriados</strong>
	          </h3>
	        </div>
	        <div class="row">
	          <spring:bind path="descricao">
	            <div class="form-group col-xs-12 col-md-6 ${status.error ? 'has-error' : ''}">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição do feriado"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
	            </div>
	          </spring:bind>
	          <spring:bind path="data">
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="data" class="control-label">Data</label>
		          <div class="input-group date" id="div-data">
		          	<form:input path="data" class="form-control" extra="placeholder=Data do feriado" />
		            <span class="input-group-addon">
		                <span class="glyphicon glyphicon-calendar"></span>
		            </span>
		          </div>
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