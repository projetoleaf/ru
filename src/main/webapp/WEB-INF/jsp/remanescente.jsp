<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome,sweetalert2" />

<html>
<head>
<meta name="header" content="Remanescentes" />
<title>Remanescentes</title>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/geral.css" type="text/css"/>
<style>
	input[type=checkbox] {
		vertical-align: middle;
		margin: 0px 4px 2.5px 0px !important;
	}
	.form-control {
		margin-bottom: 5px !important;
	}
</style>
</head>
<body>
	<script>	
		$( document ).ready(function() {	
			var count; 
			
			$("select").prop('disabled',true);		
			
			$("input:checkbox[name='data']").change(function(){
				
				var $checkboxes = $("input:checkbox[name='data']");		
				count = 0;
				
				$checkboxes.each(function(){
					
					if ($(this).is(":checked")) {		
						$("#tipo" + count).prop('disabled',false);
					} else {
						$("#tipo" + count).prop('disabled',true);	
						$("#tipo" + count).prop('selectedIndex',0);
				  	}
					
					count++;
				});
			});
			
			$("#formRemanescente").submit(function(e) {
				e.preventDefault();
				var datas = [];
				var tipos = [];
				count = 0;
				
				$("input[name='data']").each (function(){
					if($(this).is(":checked")){
						datas.push($(this).val());
						tipos.push($("#tipo" + count).val());
					}
					count++;
				});
				
				$.ajax({
					type: 'post',
				    url: "${pageContext.request.contextPath}/verificaremanescente",
				  	data: {datas: datas, tipos: tipos}
				}).done(function(response){
					response = JSON.parse(response);
					if(response.sucesso){
						var lista = "";
						for(var i in datas)
							lista += "<br>" + datas[i] + " | " + tipos[i];
						swal({
							  title: 'Confirme os Dados',
							  html: "<b>Data | Tipo de Refeição</b>" + lista,
							  type: 'question',
							  timer: 5000,
							  showCancelButton: true,
							  cancelButtonText: 'Cancelar',
							  cancelButtonColor: '#d33'
						}).then(function(){
							$.ajax({
								type: 'post',
							    url: "${pageContext.request.contextPath}/efetuarremanescente",
							  	data: {tipos: tipos, datas: datas}
							}).done(function(response){
								response = JSON.parse(response);
								if(response.sucesso){
									swal(
									    'Adquirido!',
									    'Refeição remanescente adquirida com sucesso!',
									    'success'
									).then(function(){
										window.location.replace("${pageContext.request.contextPath}/historico");
									});
								} else if (response.erro != null){
									switch(response.erro){
										case "erro":
											swal(
												'Oops...',
												'Ocorreu um erro, tente novamente!',
												'error'
								        	)
								        	break;
										default:
											break;
									}
								}
							});
						}, function(dismiss){})
					} else if (response.erro != null){
						switch(response.erro){
							case "data":
								swal(
									'Oops...',
									'Selecione pelo menos uma data!',
									'warning'
					        	)
					        	break;
							case "tipos":
								swal(
									'Oops...',
									'Especifique o tipo de refeição!',
									'warning'
					        	)
					        	break;
							case "creditos":
								swal(
									'Oops...',
									'Você não possui créditos suficientes para a quantidade de refeições selecionadas!',
									'warning'
					        	)
					        	break;					
							default:
								break;
						}
					}
				}).fail(function() {
					swal(
						'Oops...',
						'Não há refeições remanescentes!',
						'warning'
		        	)
				});		
			});
			
			$("#selecionarTudo").change(function () {
				$('input:checkbox').not(this).prop('checked', this.checked);
			     
			    var $checkboxes = $("input:checkbox[name='data']");		
		     	count = 0;
			     
			    $checkboxes.each(function(){
						
					if ($(this).is(":checked")) {	
						$(this).prop('disabled', true);
						$("#tipo" + count).prop('disabled',false);
					} else {
						$(this).prop('disabled', false);
						$("#tipo" + count).prop('disabled',true);	
						$("#tipo" + count).prop('selectedIndex',0);
				  	}
					
					count++;
				 });
			});
			
			$("#cbdatas").css("line-height", $(".form-control").outerHeight(true)+"px"); 
		});		
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Remanescentes</strong>
          </h3>
        </div>
        
		<h4>Selecione o(s) dia(s) que deseja reservar:</h4>
		
		<br/> 
		
		<form id="formRemanescente">
		
			<div class="row">
				<div class="col-xs-12 col-sm-4 text-center">
					<input type="checkbox" id="selecionarTudo" /> <b>Selecionar todas as datas</b> 
				</div>
			</div>
		
			<div class="row">		
				<div class="col-xs-12 col-sm-4 text-center" id="cbdatas">
					<c:forEach items="${datas}" var="data">
						<input type="checkbox" name="data" value="${data}">
						<b>${data}</b><br>
					</c:forEach>
				</div>					
				<div class="col-xs-12 col-sm-4 text-center">
					<c:forEach items="${datas}" varStatus="loop">					
						<select id="tipo${loop.index}" name="tipo" class="form-control">	
							<option value="" label="-- Tipo de Refeição --"> </option>	
							<c:forEach items="${tipoRefeicoes}" var="refeicoes">
								<option value="${refeicoes.descricao}" label="${refeicoes.descricao}"> </option>									
							</c:forEach>
						</select>
					</c:forEach>
				</div>	
			</div>
			
			<div class="text-center">
				<br>
				<button type="submit" class="btn btn-primary">
				 	<i class="fa fa-cutlery" aria-hidden="true"></i> Reservar
				</button>
			</div>
		</form>
	  </div>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>