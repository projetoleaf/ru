<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="jquery.validation,jquery.inputmask,sweetalert2" />

<html>
<head>
	<meta name="header" content="Transferir" />
	<title>Transferir</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/geral.css" type="text/css"/>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<script type="text/javascript">		
		$(document).ready(function(){
			$("#cpf").focus();
			$("#cpf").inputmask("999.999.999-99");
			
			$("#transferencia").submit(function(e){
				e.preventDefault();
				var cpf = $("#cpf").val();
				var datas = [];
				
				$("input[name='cbdatas']:checked").each (function(){
					datas.push($(this).val());
				});
				
				$.ajax({
					type: 'post',
				    url: "${pageContext.request.contextPath}/verificartransferencia",
				  	data: {cpf: cpf, datas: datas}
				}).done(function(response){
					response = JSON.parse(response);

					if(typeof response.nome !== 'undefined'){
						var lista = "";
						for(var i in datas)
							lista += "<br>"+datas[i];
						swal({
							  title: 'Confirme os Dados',
							  html: "<b>Nome</b><br> " + response.nome + "<br><br><b>Data(s) a Transferir</b>" + lista,
							  type: 'question',
							  timer: 5000,
							  showCancelButton: true,
							  cancelButtonText: 'Cancelar',
							  cancelButtonColor: '#d33'
						}).then(function(){
							$.ajax({
								type: 'post',
							    url: "${pageContext.request.contextPath}/efetuartransferencia",
							  	data: {cpf: cpf, datas: datas}
							}).done(function(response){
								swal(
								    'Transferido!',
								    'Transferência realizada com sucesso!',
								    'success'
								).then(function(){
									location.reload();
								});
							});
						}, function(dismiss){});
					}
					else {
						switch(response.erro){
							case "cpfigual":
								swal(
									'Oops...',
									'Não é possível transferir para si mesmo!',
									'warning'
					        	)
					        	break;
							case "data":
								swal(
									'Oops...',
									'Selecione pelo menos uma data!',
									'warning'
					        	)
					        	break;
							case "cpf":
								swal(
									'Oops...',
									'Digite um CPF válido!',
									'warning'
					        	)
					        	break;
							case "reservado":
								swal(
									'Oops...',
									'A pessoa a quem deseja transferir já possui reserva paga para um dos dias!',
									'warning'
					        	)
					        	break;
							case "categoria":
								swal(
									'Oops...',
									'A categoria da pessoa a quem deseja transferir não pode ser diferente da sua!',
									'warning'
					        	)
					        	break;
							default:
								break;
						}
					}
				});
			});
		});
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
		<div class="panel-body">
			<div class="page-header">
         		<h3><strong>Transferir</strong></h3>
        	</div>
			<h4>Selecione o(s) dia(s) que deseja transferir:</h4>
			<p id="aviso">*Somente serão mostrados os dias com reserva e pagamento efetuados.</p>
			<form id="transferencia">
				<div class="text-center">
					<c:forEach items="${datasPagas}" var="datas">
						<input type="checkbox" value="${datas}" name="cbdatas"/>
						<b>${datas}</b><br>
					</c:forEach>
					<br>
				</div>
				<h4>Escreva o CPF da pessoa a quem deseja tranferir:</h4>
				<div class="form-group col-xs-12 col-sm-4 col-sm-offset-4">
	            	<br><input id="cpf" class="form-control" placeholder="Digite o CPF da pessoa"/>
			 	</div>
				<div class="form-group col-xs-12 col-sm-4 col-sm-offset-4">
					<button type="submit" id="transferir" class="btn btn-primary center-block">
					 	<span class="fa fa-exchange" aria-hidden="true"></span> Transferir
					</button>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>