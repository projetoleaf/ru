<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<dandelion:bundle
	includes="jquery.validation,jquery.inputmask,jquery.datetimepicker,sweetalert2,font-awesome" />

<html>
<head>
<meta name="header" content="Cadastro" />
<meta name="previouspage"
	content="<li><a href='<c:url value="/cardapios"/>'>Cardápios</a></li>" />
<title>Cadastro de cardápios</title>
</head>
<body>
	<script type="text/javascript">
		 $(document).ready(function() {
			 var count = ${count};
			 $("#count").val(count);
			 
			 function desabilitaCampos(status) {
				 $("#data").prop('readonly',status);				    	 
				 $('select option:not(:selected)').attr('disabled',status);
			 }
			 
			 if(count == 1) {		    	  
		    	  desabilitaCampos(true);	 		    	  
		     } else {
		    	  $("#data").blur(function(){
			    	  var data = $(this).val();

					  var objDate = new Date();
					  objDate.setYear(data.split("/")[2]);
					  objDate.setMonth(data.split("/")[1] - 1);//- 1 pq em js é de 0 a 11 os meses
					  objDate.setDate(data.split("/")[0]);

					  if (objDate.getTime() <= new Date().getTime()) {
					  	swal({
							title: "Data inválida!",
							text: "Data a ser cadastrada não pode ser anterior ou igual a data atual.",
							type: "warning"
						}).then(function () {
							$("#data").val("");
						  	$("#data").focus();
						})
						
						return false;
					  }
			      });
		      }      
			 
		     var formValidator = $("#cardapio").validate({
		         rules : {
		        	 data : { required : true },
		             periodoRefeicao : { required : true },
		             pratoBase : { required : true, maxlength : 50, minlength : 3 },
		             pratoTradicional : { required : true, maxlength : 100, minlength : 3 },
		             pratoVegetariano : { required : true, maxlength : 100, minlength : 3 },
		             guarnicao : { required : true, maxlength : 100, minlength : 3 },
		             salada : { required : true, maxlength : 50, minlength : 3 },
		             sobremesa : { required : true, maxlength : 50, minlength : 3 },
		             suco : { required : true, maxlength : 50, minlength : 3 }
		         }
		     });
		     $("#data").focus();
		     $("#div-data").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
		     $("#data").inputmask("99/99/9999");     
		      
		      $("#cardapio").submit(function(event) {
		    	  event.preventDefault();	  
		    	  
					if ($("#cardapio").valid()){
						$.ajax({
							type: 'post',
						    url: '${pageContext.request.contextPath}/cardapios/verificar',
						    data: $("#cardapio").serialize(),
						    success: function(data) {
						    	data = JSON.parse(data);
						    	
						    	if(data.sucesso){
						    		swal({
						    			title: "Sucesso",
										text: "Cardápio salvo com sucesso!",
										type: "success"
									}).then(function () {
										$(location).attr('href','${pageContext.request.contextPath}/cardapios');
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
										case "feriado":
											swal({
								    			title: "Data inválida!",
												text: "Esta data é um feriado!",
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
									text: 'Não foi possível salvar o cardápio! <br>' +
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
	<form:form modelAttribute="cardapio">
		<form:hidden path="id" />
		<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
			<div class="panel-body">
				<div class="page-header" style="margin-top: 10px;">
					<h3>
						<strong>Cardápio</strong>
					</h3>
				</div>
				<div class="row">
					<div class="form-group col-xs-12 col-md-6">
						<label for="data" class="control-label">Data</label>
						<div class="input-group date" id="div-data">
							<form:input path="data" class="form-control"
								extra="placeholder=Data da refeição" />
							<span class="input-group-addon"> <span
								class="fa fa-calendar"></span>
							</span>
						</div>
					</div>
					<div class="form-group col-xs-12 col-md-6">
						<label for="periodoRefeicao" class="control-label">Período</label>
						<form:select path="periodoRefeicao" class="form-control">
							<form:option value="" label="----- Selecione um período -----" />
							<form:options items="${periodoRefeicao}" itemLabel="descricao"
								itemValue="id" />
						</form:select>
					</div>
				</div>
				<div class="row">
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="pratoBase" class="control-label">Prato Base</label>
						<form:input path="pratoBase" class="form-control"
							placeholder="Digite o prato base" />
						<span class="has-error"><form:errors path="pratoBase"
								class="help-block" /></span>
					</div>
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="pratoTradicional" class="control-label">Prato
							Tradicional</label>
						<form:input path="pratoTradicional" class="form-control"
							placeholder="Digite o prato tradicional" />
						<span class="has-error"><form:errors
								path="pratoTradicional" class="help-block" /></span>
					</div>
				</div>
				<div class="row">
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="pratoVegetariano" class="control-label">Prato
							Vegetariano</label>
						<form:input path="pratoVegetariano" class="form-control"
							placeholder="Digite o prato vegetariano" />
						<span class="has-error"><form:errors
								path="pratoVegetariano" class="help-block" /></span>
					</div>
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="guarnicao" class="control-label">Guarnição</label>
						<form:input path="guarnicao" class="form-control"
							placeholder="Digite a guarnição" />
						<span class="has-error"><form:errors path="guarnicao"
								class="help-block" /></span>
					</div>
				</div>
				<div class="row">
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="salada" class="control-label">Salada</label>
						<form:input path="salada" class="form-control"
							placeholder="Digite a salada" />
						<span class="has-error"><form:errors path="salada"
								class="help-block" /></span>
					</div>
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="sobremesa" class="control-label">Sobremesa</label>
						<form:input path="sobremesa" class="form-control"
							placeholder="Digite a sobremesa" />
						<span class="has-error"><form:errors path="sobremesa"
								class="help-block" /></span>
					</div>
				</div>
				<div class="row">
					<div
						class="form-group col-xs-12 col-md-6">
						<label for="suco" class="control-label">Suco</label>
						<form:input path="suco" class="form-control"
							placeholder="Digite o suco" />
						<span class="has-error"><form:errors path="suco"
								class="help-block" /></span>
					</div>
				</div>
			</div>
			<input type="hidden" id="count" name="count" value="" />
			<div class="form-group col-x-12 col-md-12"
				style="text-align: center; margin-top: 15px;">
				<button type="submit" class="btn btn-primary">
					<i class="fa fa-floppy-o" aria-hidden="true"></i> Salvar
				</button>
			</div>
		</div>
	</form:form>
</body>
</html>