<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<dandelion:bundle includes="jquery.validation,sweetalert2,font-awesome" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage" content="<li><a href='<c:url value="/cursos"/>'>Cursos</a></li>" />
<title>Cadastro de Cursos</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
		     var formValidator = $("#curso").validate({
		         rules : {
		             descricao : { required : true, maxlength : 100 },
		             periodo : { required : true, maxlength : 50 }
		         }
		     });
		     $("#descricao").focus();
		     
		     $("#curso").submit(function(event) {
		    	  event.preventDefault();	  
		    	  
					if ($("#curso").valid()){
						$.ajax({
							type: 'post',
						    url: '${pageContext.request.contextPath}/cursos/verificar',
						    data: $("#curso").serialize(),
						    success: function(data) {
						    	data = JSON.parse(data);
						    	
						    	if(data.sucesso){
						    		swal({
						    			title: "Sucesso",
										text: "Curso salvo com sucesso!",
										type: "success"
									}).then(function () {
										$(location).attr('href','${pageContext.request.contextPath}/cursos');
									})
								} else if (data.erro != null) {
									switch(data.erro){
										case "descricao":
											swal({
												title: "Curso inválido!",
												text: "Este curso já foi cadastrado!",
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
									text: 'Não foi possível salvar o curso! <br>' +
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
  	<form:form modelAttribute="curso">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <h3>
	            <strong>Cursos</strong>
	          </h3>
	        </div>
	        <div class="row">
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição do curso"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
	            </div>
	            <div class="form-group col-xs-12 col-md-4">
	              <label for="periodo" class="control-label">Período</label>
	              <form:select path="periodo" class="btn btn-default">
					<option value="">----- Selecione um período -----</option>
					<option value="Diurno">Diurno</option>
					<option value="Integral">Integral</option>
					<option value="Matutino">Matutino</option>
					<option value="Vespertino">Vespertino</option>
				  	<option value="Noturno">Noturno</option>
				  </form:select>
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