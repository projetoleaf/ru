<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome,sweetalert2" />

<html>
<head>
	<meta name="header" content="Reservar" />
	<title>Reservar</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/geral.css" type="text/css"/>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<!-- meta http-equiv="refresh" content="25"-->
</head>
<body>
	<script>
		$( document ).ready(function() {
			var count;
			$("select[name='tipos']").prop('disabled',true);		
			
			$("input[name='cbdatas']").change(function(){
				count = 0;
				$("input[name='cbdatas']").each(function(){
					if ($(this).is(":checked"))
						$("#tipo" + count).prop('disabled',false);
					else {
						$("#tipo" + count).prop('disabled',true);	
						$("#tipo" + count).prop('selectedIndex',0);
				  	}
					count++;
				});
			});
			
			$("#reserva").submit(function(e){
				e.preventDefault();
				var datas = [];
				var tipos = [];
				var pagamento = $("#pagamento").val();
				count = 0;
				
				$("input[name='cbdatas']").each (function(){
					if($(this).is(":checked")){
						datas.push($(this).val());
						tipos.push($("#tipo" + count).val());
					}
					count++;
				});
				
				$.ajax({
					type: 'post',
				    url: "${pageContext.request.contextPath}/verificarreserva",
				  	data: {tipos: tipos, datas: datas, pagamento: pagamento}
				}).done(function(response){
					response = JSON.parse(response);
					if(response.sucesso){
						var lista = "";
						var metodo = pagamento == 2 ? "Dinheiro" : "Créditos"; 
						for(var i in datas)
							lista += "<br>" + datas[i] + " | " + tipos[i];
						swal({
							  title: 'Confirme os Dados',
							  html: "<b>Data | Tipo de Refeição</b>" + lista + "<br><br><b>Forma de Pagamento</b><br>" + metodo,
							  type: 'question',
							  timer: 5000,
							  showCancelButton: true,
							  cancelButtonText: 'Cancelar',
							  cancelButtonColor: '#d33'
						}).then(function(){
							$.ajax({
								type: 'post',
							    url: "${pageContext.request.contextPath}/efetuarreserva",
							  	data: {tipos: tipos, datas: datas, pagamento: pagamento}
							}).done(function(response){
								response = JSON.parse(response);
								if(response.sucesso){
									swal(
										    'Reservado!',
										    'Reserva realizada com sucesso!',
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
						})
					} else if (response.erro != null){
						switch(response.erro){
							case "data":
							swal(
									'Oops...',
									'Selecione pelo menos uma data!',
									'warning'
					        	)
					        	break;
							case "creditos":
								swal(
									'Oops...',
									'Você não possui créditos suficientes!',
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
							default:
								break;
						}
					}
				}).fail(function() {
					swal(
							'Oops...',
							'Não há datas para reservar!',
							'warning'
			        	)
				});
			});
			
			$("#selecionarTudo").change(function () {
				$('input:checkbox').not(this).prop('checked', this.checked);	
			    $("input[name='cbdatas']").each(function(){
					if ($(this).is(":checked")){
						$(this).prop('disabled', true);
						$("select[name='tipos']").prop('disabled',false);
					} else{
						$(this).prop('disabled', false);
						$("select[name='tipos']").prop('disabled',true);	
						$("select[name='tipos']").prop('selectedIndex',0);
				  	}
				 });
			});
			
			$("#cbdatas").css("line-height", $(".form-control").outerHeight(true)+"px"); 
		});		
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
		<div class="panel-body">
        	<div class="page-header" style="margin-top: 10px;">
          		<h3>
            		<strong>Reservar</strong>
          		</h3>
        	</div>
			<h4>Selecione o(s) dia(s) que deseja reservar:</h4>
			<br/> 
			<form id="reserva">
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center">
						<input type="checkbox" id="selecionarTudo" /> <b>Selecionar todas as datas</b> 
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center" id="cbdatas" >
						<c:forEach items="${datas}" var="datas">
							<input type="checkbox" value="${datas}" name="cbdatas"/>
							<b>${datas}</b><br>
						</c:forEach>
					</div>	
					<div class="col-xs-12 col-sm-4 text-center">	
						<c:forEach items="${datas}" var="datas" varStatus="loop">
							<select id="tipo${loop.index}" name="tipos" class="form-control">
								<option disabled value="" label="-- Tipo de Refeição --" selected> </option>
								<c:forEach items="${tipoRefeicoes}" var="tipos">
									<option value="${tipos.descricao}" label="${tipos.descricao}"> </option>
								</c:forEach>
							</select>	
						</c:forEach>
					</div>		
				</div>
				<br>
				<h4>Forma de pagamento:</h4>
				<div class="row">
					<div class="col-xs-12 col-sm-offset-4 col-sm-4 text-center">
						<select id="pagamento" name="pagamento" class="form-control">
							<option value="1" label="Créditos"> </option>
							<option value="2" label="Dinheiro"> </option>
						</select>						
					</div>
				</div>
				<div class="text-center">
					<br/>
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