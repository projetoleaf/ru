<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/status/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage" content="<li><a href='<c:url value="/status"/>'>Status</a></li>" />
<title>Cadastro de Status</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#status").validate({
		         rules : {
		             descricao : { required : true, maxlength : 50 }
		         }
		     });
		     $("#descricao").focus();
		 });
  	</script>
  	<form:form action="${actionSalvar}" modelAttribute="status">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="status"/></jsp:include>
	          <h3>
	            <strong>Status</strong>
	          </h3>
	        </div>
	        <div class="row">
	          <spring:bind path="descricao">
	            <div class="form-group col-xs-12 col-md-12 ${status.error ? 'has-error' : ''}">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição do status"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
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