<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<dandelion:bundle includes="jquery.validation,jquery.inputmask,sweetalert2,font-awesome" />

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
		     
		     $("#categoria").submit(function(event) {
		    	  event.preventDefault();	  
		    	  
					if ($("#categoria").valid()){
						$.ajax({
							type: 'post',
						    url: '${pageContext.request.contextPath}/categorias/verificar',
						    data: $("#categoria").serialize(),
						    success: function(data) {
						    	data = JSON.parse(data);
						    	
						    	if(data.sucesso){
						    		swal({
						    			title: "Sucesso",
										text: "Categoria salva com sucesso!",
										type: "success"
									}).then(function () {
										$(location).attr('href','${pageContext.request.contextPath}/categorias');
									})
								} else if (data.erro != null) {
									switch(data.erro){
										case "descricao":
											swal({
												title: "Categoria inválida!",
												text: "Esta categoria já foi cadastrada!",
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
									text: 'Não foi possível salvar a categoria! <br>' +
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
  	<form:form modelAttribute="categoria">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <h3>
	            <strong>Categoria</strong>
	          </h3>
	        </div>
	        <div class="row">
	            <div class="form-group col-xs-12 col-md-12">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição da categoria"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
	            </div>
	        </div>
	        <div class="row">
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="valorSemSubsidio" class="control-label">Valor sem subsídio (R$)</label>
	              <form:input path="valorSemSubsidio" class="form-control" placeholder="Digite o valor sem subsídio"/>
	              <span class="has-error"><form:errors path="valorSemSubsidio" class="help-block"/></span>
	            </div>
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="valorComSubsidio" class="control-label">Valor com subsídio (R$)</label>
	              <form:input path="valorComSubsidio" class="form-control" placeholder="Digite o valor com subsídio"/>
	              <span class="has-error"><form:errors path="valorComSubsidio" class="help-block"/></span>
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