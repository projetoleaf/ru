<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="sweetalert2" />

<html>
<head>
<meta name="header" content="Comprar" />
<title>Comprar</title>
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
			$("select[name='tipoRefeicao']").prop('disabled',true);		
			$("select[name='tipoValor']").prop('disabled',true);	
			
			$("input:checkbox[name='cbdatas']").change(function(){
				
				var $checkboxes = $("input:checkbox[name='cbdatas']");		
				count = 0;				
				
				$checkboxes.each(function(){						
					if ($(this).is(":checked")) {	
						if($("select#tipoValor" + count).val() == "")
							$("select#tipoValor" + count).prop('disabled',false);						
						$("select#tipoRefeicao" + count).prop('disabled',false);	
						
					} else {
						$("select#tipoRefeicao" + count).prop('disabled',true);	
						$("select#tipoRefeicao" + count).prop('selectedIndex',0);
						
						if($("select#tipoValor" + count).val() == "") {
							$("select#tipoValor" + count).prop('disabled',true);
							$("select#tipoValor" + count).prop('selectedIndex',0);
						}						
				  	}
					
					count++;
				});
			});	
			
			$("#selecionarTudo").change(function () {
				$('input:checkbox').not(this).prop('checked', this.checked);
			     
			    var $checkboxes = $("input:checkbox[name='cbdatas']");	
			    
			    count = 0;
			     
			    $checkboxes.each(function() {
						
					if ($(this).is(":checked")) {
						
						$(this).prop('disabled', true);
						
						if($("select#tipoValor" + count).val() == "")
							$("select#tipoValor" + count).prop('disabled',false);							
						$("select#tipoRefeicao" + count).prop('disabled',false);
						
					} else {
						$(this).prop('disabled', false);
						$("select[name='tipoRefeicao']").prop('disabled',true);	
						$("select[name='tipoRefeicao']").prop('selectedIndex',0);
						
						if($("select#tipoValor" + count).val() == "") {
							$("select#tipoValor" + count).prop('disabled',true);
							$("select#tipoValor" + count).prop('selectedIndex',0);
						}						
				  	}
					
					count++;
				 });
			});
			
			$("#compra").submit(function(e) {
				e.preventDefault();
				var datas = [];
				var tiposRefeicoes = [];
				var tiposValores = [];
				count = 0;
				
				$("input[name='cbdatas']").each (function(){
					if($(this).is(":checked")){
						datas.push($(this).val());
						tiposRefeicoes.push($("#tipoRefeicao" + count).val());
						tiposValores.push($("#tipoValor" + count).val());
					}
					count++;
				});
				
				$.ajax({
					type: 'post',
				    url: "${pageContext.request.contextPath}/verificarcompra",
				  	data: {datas: datas, tiposRefeicoes: tiposRefeicoes, tiposValores: tiposValores}
				}).done(function(response) {
					response = JSON.parse(response);

					if(response.sucesso) {
						var lista = "";
						for(var i in datas)
							lista += "<br><br>" + datas[i] + " | " + tiposRefeicoes[i] + " | " + tiposValores[i];
						swal({
							  title: 'Confirme os Dados',
							  html: "<b>Data | Tipo Refeição | Tipo Valor</b>" + lista,
							  type: 'question',
							  timer: 5000,
							  showCancelButton: true,
							  cancelButtonText: 'Cancelar',
							  cancelButtonColor: '#d33'
						}).then(function() {
							$.ajax({
								type: 'post',
							    url: "${pageContext.request.contextPath}/efetuarcompra",
							  	data: {datas: datas, tiposRefeicoes: tiposRefeicoes, tiposValores: tiposValores}
							}).done(function(resposta){
								resposta = JSON.parse(resposta);
								
								if(resposta.sucesso) {
									swal(
									    'Comprado!',
									    'Compra realizada com sucesso!',
									    'success'
									).then(function(){
										$(location).attr('href','${pageContext.request.contextPath}/historico');
									});
								} else if (response.erro != null) {
									switch(response.erro){										
										case "erro":
											swal(
												'Oops...',
												'Não foi possível realizar esta operação!',
												'error'
								        	)
								        	window.reload();
								        	break;
										default:
											break;
									}
								}								
							}).fail(function() {
								swal(
									'Oops...',
									'Compra não realizada! Comunique o operador do sistema!',
									'error'
					        	)
							});		
						}, function(dismiss){});
					}
					else if (response.erro != null) {
						switch(response.erro){
							case "data":
								swal(
									'Oops...',
									'Selecione pelo menos uma data!',
									'warning'
					        	)
					        	break;
							case "tipoRefeicao":
								swal(
									'Oops...',
									'Selecione o tipo de refeição!',
									'warning'
					        	)
					        	break;
							case "tipoValor":
								swal(
									'Oops...',
									'Selecione o tipo de valor!',
									'warning'
					        	)
					        	break;
							case "saldo":
								swal(
									'Oops...',
									'Saldo insuficiente para a quantidade de refeições e os tipos valores selecionados!',
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
						'Não há datas para comprar!',
						'warning'
		        	)
				});				
			});
			
			$("#cbdatas").css("line-height", $(".form-control").outerHeight()+"px"); 
		});		
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Comprar</strong>
          </h3>
        </div>
        
			<h4>Selecione o(s) dia(s) que deseja comprar:</h4>
			
			<br/> 
			
			<form id="compra">
			
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center">
						<input type="checkbox" id="selecionarTudo" /> <b>Selecionar todas as datas</b> 
					</div>
				</div>
			
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center" id="cbdatas">
						<c:forEach items="${todasAsDatas}" var="datas">
							<input type="checkbox" name="cbdatas" value="${datas}">
							<b>${datas}</b><br>
						</c:forEach>
					</div>	
					<div class="col-xs-12 col-sm-4 text-center">
						<c:forEach items="${todasAsDatas}" varStatus="loop">					
							<select id="tipoRefeicao${loop.index}" name="tipoRefeicao" class="form-control">	
								<option value="" label="-- Tipo de Refeição --"> </option>	
								<c:forEach items="${todasAsRefeicoes}" var="refeicoes">
									<option value="${refeicoes.descricao}" label="${refeicoes.descricao}"> </option>									
								</c:forEach>
							</select>
						</c:forEach>
					</div>	
					<div class="col-xs-12 col-sm-4 text-center">	
						<c:forEach items="${todasAsDatas}" varStatus="status">	
							<select id="tipoValor${status.index}" name="tipoValor" class="form-control">
								<option value="" label="-- Tipo de Valor --"> </option>	
								<c:forEach items="${todosOsValores}" var="valores" varStatus="loop">				
									<c:set var="count" value="${loop.index}"></c:set>
									<c:choose>
										<c:when test="${contadores[loop.index] == count}">
											<option value="${valores.descricao}" selected label="${valores.descricao}"> </option>	
										</c:when>	
										<c:otherwise>
											<option value="${valores.descricao}" label="${valores.descricao}"> </option>	
										</c:otherwise>						
									</c:choose>										
								</c:forEach>
							</select>
						</c:forEach>
					</div>					
				</div>
				
				<div class="text-center">
					<br/>
					<button type="submit" class="btn btn-primary">
					 	<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> Comprar
					</button>
				</div>
			</form>
	  </div>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>