<%@ taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="sweetalert2" />

<script type="text/javascript">
	$(function () {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});
	
	$(document).ready(function(){	
		$("#reservar").click(function(e){
			e.preventDefault();
			$.ajax({
				type: 'post',
			    url: "${pageContext.request.contextPath}/verificarreservar"
			}).done(function(response){
				response = JSON.parse(response);
				if(response.sucesso)
					window.location.href = "${pageContext.request.contextPath}/reservar";
				else if (response.erro != null){
					switch(response.erro){
						case "discente":
						swal(
								'Oops...',
								'� necess�rio ser discente para reservar!',
								'warning'
				        	)
				        	break;
						case "data":
							swal(
								'Oops...',
								'Fora do per�odo para reservar!',
								'warning'
				        	)
				        	break;
						case "contagem":
							swal(
								'Oops...',
								'Reservas esgotadas!',
								'warning'
				        	)
				        	break;
						default:
							break;
					}
				}
				else
					swal(
						'Oops...',
						'Ocorreu um erro!',
						'error'
		        	)
			}).fail(function() {
				swal(
						'Oops...',
						'N�o h� datas para reserva!',
						'error'
		        	)
			});
		});
		
		$("#transferir").click(function(e){
			e.preventDefault();
			$.ajax({
				type: 'post',
			    url: "${pageContext.request.contextPath}/verificartransferir"
			}).done(function(response){
				if(response)
					window.location.href = "${pageContext.request.contextPath}/transferir";
				else
					swal(
						'Oops...',
						'Voc� n�o tem reservas a serem transferidas!',
						'warning'
		        	)
			});
		});	
		
		$("#comprar").click(function(e){
			e.preventDefault();
			$.ajax({
				type: 'post',
			    url: "${pageContext.request.contextPath}/verificarcomprar"
			}).done(function(response){
				response = JSON.parse(response);
				if(response.sucesso)
					window.location.href = "${pageContext.request.contextPath}/comprar";
				else if (response.erro != null){
					switch(response.erro){
						case "saldo":
							swal(
								'Oops...',
								'Voc� n�o possui saldo suficiente para comprar uma refei��o!',
								'warning'
				        	)
				        	break;
						case "data":
							swal(
								'Oops...',
								'Fora do per�odo de compras!',
								'warning'
				        	)
				        	break;
						case "contagem":
							swal(
								'Oops...',
								'Compras esgotadas!',
								'warning'
				        	)
				        	break;
						default:
							break;
					}
				}
				else
					swal(
						'Oops...',
						'Ocorreu um erro no sistema!',
						'error'
		        	)
			}).fail(function() {
				swal(
					'Oops...',
					'N�o h� refei��es para comprar!',
					'error'
	        	)
			});
		});
		
		$("#remanescente").click(function(e){
			e.preventDefault();
			$.ajax({
				type: 'post',
			    url: "${pageContext.request.contextPath}/verificarremanescente"
			}).done(function(response){
				response = JSON.parse(response);
				if(response.sucesso)
					window.location.href = "${pageContext.request.contextPath}/remanescente";
				else if (response.erro != null){
					switch(response.erro){
						case "discente":
							swal(
								'Oops...',
								'� necess�rio ser discente para reservar!',
								'warning'
				        	)
				        	break;
						case "saldo":
							swal(
								'Oops...',
								'Voc� n�o possui saldo suficiente!',
								'warning'
				        	)
				        	break;
						case "data":
							swal(
								'Oops...',
								'Fora do per�odo de remanescentes!',
								'warning'
				        	)
				        	break;
						case "contagem":
							swal(
								'Oops...',
								'Refei��es remanescentes esgotadas!',
								'warning'
				        	)
				        	break;
						default:
							break;
					}
				}
				else
					swal(
						'Oops...',
						'Ocorreu um erro!',
						'error'
		        	)
			}).fail(function() {
				swal(
					'Oops...',
					'N�o h� refei��es remanescentes!',
					'error'
	        	)
			});
		});
	});
</script>