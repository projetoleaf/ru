<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<dandelion:bundle includes="jquery.validation,sweetalert2,font-awesome" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage" content="<li><a href='<c:url value="tiposValores"/>'>Tipos de Valores</a></li>" />
<title>Cadastro de Tipos de Valores</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#tipoValor").validate({
		         rules : {
		             descricao : { required : true, maxlength : 50 }
		         }
		     });
		     $("#descricao").focus();
		     
		     $("#tipoValor").submit(function(event) {
		    	  event.preventDefault();	  
		    	  
					if ($("#tipoValor").valid()){
						$.ajax({
							type: 'post',
						    url: '${pageContext.request.contextPath}/tiposValores/verificar',
						    data: $("#tipoValor").serialize(),
						    success: function(data) {
						    	data = JSON.parse(data);
						    	
						    	if(data.sucesso){
						    		swal({
						    			title: "Sucesso",
										text: "Tipo salvo com sucesso!",
										type: "success"
									}).then(function () {
										$(location).attr('href','${pageContext.request.contextPath}/tiposValores');
									})
								} else if (data.erro != null) {
									switch(data.erro){
										case "descricao":
											swal({
												title: "Tipo inválido!",
												text: "Este tipo já foi cadastrado!",
												type: "warning"
											}).then(function () {
												$("#descricao").val("");
											  	$("#descricao").focus();
											})
								        	break;										
										default:
											break;
									}
								}
						    },
						    error: function(jqXHR, textStatus, errorThrown){
					    		swal({
									title: 'Erro',
									text: 'Não foi possível salvar o tipo valor! <br>' +
										'Erro: ' + textStatus + '<br>' +
										'Descrição: ' + errorThrown,
									type: 'error'
								});
						    }
						});
					}
			  }); 
		 });
  	</script>
  	<form:form modelAttribute="tipoValor">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <h3>
	            <strong>Tipo de Valor</strong>
	          </h3>
	        </div>
	        <div class="row">
	            <div class="form-group col-xs-12 col-md-12">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição do tipo de valor"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
	            </div>
	        </div>
	      </div>
	      <div class="form-group col-x-12 col-md-12" style="text-align: center; margin-top: 15px;">
	        <button type="submit" class="btn btn-primary"><span class="fa fa-floppy-o"></span> Salvar</button>
	      </div>
	    </div>
	</form:form>
</body>
</html>