<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="jquery.validation,jquery.inputmask,jquery.datetimepicker,sweetalert2,font-awesome" />

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
	              descricao : { required : true, maxlength : 100 },
	              data : { required : true }
	          }
	      });
	      $("#data").focus();
	      $("#div-data").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
	      $("#data").inputmask("99/99/9999");
	      
	      $("#feriado").submit(function(event) {
	    	  event.preventDefault();	  
	    	  
				if ($("#feriado").valid()){
					$.ajax({
						type: 'post',
					    url: '${pageContext.request.contextPath}/feriados/verificar',
					    data: $("#feriado").serialize(),
					    success: function(data) {
					    	data = JSON.parse(data);
					    	
					    	if(data.sucesso){
					    		swal({
					    			title: "Sucesso",
									text: "Feriado salvo com sucesso!",
									type: "success"
								}).then(function () {
									$(location).attr('href','${pageContext.request.contextPath}/feriados');
								})
							} else if (data.erro != null) {
								switch(data.erro){
									case "data":
										swal({
											title: "Data inválida!",
											text: "Esta data já foi cadastrada!",
											type: "warning"
										}).then(function () {
											$("#data").val("");
										  	$("#data").focus();
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
								text: 'Não foi possível salvar o feriado! <br>' +
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
	<form:form modelAttribute="feriado">
	    <form:hidden path="id" />
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <h3>
	            <strong>Feriados</strong>
	          </h3>
	        </div>
	        <div class="row">
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="descricao" class="control-label">Descrição</label>
	              <form:input path="descricao" class="form-control" placeholder="Digite a descrição do feriado"/>
	              <span class="has-error"><form:errors path="descricao" class="help-block"/></span>
	            </div>
	            <div class="form-group col-xs-12 col-md-6">
	              <label for="data" class="control-label">Data</label>
		          <div class="input-group date" id="div-data">
		          	<form:input path="data" class="form-control" extra="placeholder=Data do feriado" />
		            <span class="input-group-addon">
		                <span class="fa fa-calendar"></span>
		            </span>
		          </div>
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