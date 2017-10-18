<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"> <c:url value="/comprar/salvar"/> </c:set>

<html>
<head>
<meta name="header" content="Comprar" />
<title>Comprar</title>
</head>
<body>
	<script>	
		$( document ).ready(function() {	
			
			var datasDoCardapio = ${objectJSON};			
			var todasAsDatas = $("input:checkbox[name='data']");		
			var contadores = ${contadores};	
			var saldo = ${saldo};	
			var valorRefeicaoS = ${valorRefeicaoS};
			var valorRefeicaoC = ${valorRefeicaoC};
			var str = "";
			var str2 = "";
			var val = 1;			
			
			console.log(contadores);
			
			$("input:checkbox[name='data']").css("marginTop","7%");	
			
			if(datasDoCardapio.length != 0) {				
				document.getElementById('reservaDisponivel').style.display = "block";
			} else {
				document.getElementById('reservaIndisponivel').style.display = "block";
			}
			
			var lista = [];
			lista.push("Tradicional");
			lista.push("Vegetariano");	
			
			
			for (var x = 1; x <= todasAsDatas.length; x++) {	
				
				str += '<select id="tipoRefeicao' + x + '" name="tipoRefeicao" class="form-control">';	 		
				
				for (var z = 0; z < lista.length; z++) {										
					str += '<option value="' + (z+1) + '" label="' + lista[z] + '"> </option>';		
				}
				
				str += '</select>';
	    	}
			
			var lista2 = [];
			lista2.push("Custo");
			lista2.push("Subsidiada");	
			
			for (var x = 1; x <= todasAsDatas.length; x++) {	
				
				str2 += '<select id="tipoValor' + x + '" name="tipoValor" class="form-control">';			
				
				for (var z = 0; z < lista2.length; z++) {										
					str2 += '<option value="' + (z+1) + '" label="' + lista2[z] + '"> </option>';		
				}
				
				str2 += '</select>';
	    	}
			
			$("#reservaDisponivel .selectRefeicao").append(str);	
			$("#reservaDisponivel .selectValor").append(str2);	
			
			$("select").prop('disabled',true);					
			$("select").css("marginBottom","5%");
			
			$("input:checkbox[name='data']").change(function(){
				
				var $checkboxes = $("input:checkbox[name='data']");		
				var count = 1;
				
				$checkboxes.each(function(){
					
					for(var x = 0; x < contadores.length; x++) {
						
						if ($(this).is(":checked")) {	
							
							if(contadores[x] == 3) {
								$("select#tipoValor" + count).prop('disabled',false);
								$("select#tipoValor" + count).prop('selectedIndex',0);
							} else if(contadores[x] == 2) {
								$("select#tipoValor" + count).prop('disabled',false);
								$("select#tipoValor" + count).prop('selectedIndex',1);
							} else if(contadores[x] == 1) {
								$("select#tipoValor" + count).prop('disabled',false);
								$("select#tipoValor" + count).prop('selectedIndex',2);
							}	
							
							$("select#tipoRefeicao" + count).prop('disabled',false);
							
						} else {
							$("select#tipoRefeicao" + count).prop('disabled',true);	
							$("select#tipoRefeicao" + count).prop('selectedIndex',0);
							$("select#tipoValor" + count).prop('disabled',true);
							$("select#tipoValor" + count).prop('selectedIndex',0);
					  	}
					}	
					
					count++;
				});
			});
			
			$("#reservar").click(function() {
				var $checkboxes = $(this).closest('form').find(':checkbox');
			    var txt = "";
			    var txt2 = "";
			    var txt3 = "";
			    var minhaDataFormatada;
			    var selects = $("select").length;
			    var count = 0;
			    
			    if($checkboxes.is(":checked")) {
			    	
			    	for(var a = 1; a <= selects; a++) {
			    		
			    		if($('#data' + a).is(":checked")) {
			    			
			    			for(var b = 0; b < datasDoCardapio.length; b++) {	
			    				
			    				if(String(datasDoCardapio[b].id) === $('#data' + a).val()) {
			    						
		    						txt2 = ($("select#tipoRefeicao" + a).val() % 2 ? "Tradicional" : "Vegetariano");
		    						txt3 = ($("select#tipoValor" + a).val() % 2 ? "Custo" : "Subsidiada");
					    			minhaDataFormatada = new Date(datasDoCardapio[b].data);
					    			txt = txt + "<p>&#10004 " + minhaDataFormatada.toLocaleDateString('en-GB')  + " - " + txt2 + " - " + txt3 + "</p>";	
					    			
					    			count++;
				    	    	}					    			
				    		}  			
			    		} 	    		    		
			    	}
			    		
		    		if(count * valorRefeicaoS < saldo || count * valorRefeicaoC < saldo) {		
	    				
		    			$("#modalConfirmarReserva .modal-body").append("<p>Confirme os dias da sua compra, o tipo refeicao e o tipo reserva...</p>");			    		
			    		$("#modalConfirmarReserva .modal-body").append(txt);	
						$('#modalConfirmarReserva').modal('show');
		    		} else {
		    						    			
						$("#modalNaoSelecionado .modal-body").append("<p>Créditos insuficientes para a quantidade de refeições selecionadas!</p>");		
		    			$("#modalNaoSelecionado .modal-body").append("<p>Diminua a quantidade e tente novamente!</p>");		
   				    	$('#modalNaoSelecionado').modal('show');
		    		}			 			   
				} else {
			    	$("#modalNaoSelecionado .modal-body").append("<p>Selecione pelo menos um dia!</p>");		
			    	$('#modalNaoSelecionado').modal('show');
			    }				
			});
			
			$(".cancelar").click(function() {
				$("#modalConfirmarReserva .modal-body").empty();
				$("#modalNaoSelecionado .modal-body").empty();
			});
			
			$("#selecionarTudo").change(function () {
				$('input:checkbox').not(this).prop('checked', this.checked);
			     
			    var $checkboxes = $("input:checkbox[name='data']");		
		     	var count = 1;
			     
			    $checkboxes.each(function(){
						
					for(var x = 0; x < contadores.length; x++) {
						
						if ($(this).is(":checked")) {
							
							$(this).prop('disabled', true);
							
							if(contadores[x] == 3) {
								$("select#tipoValor" + count).prop('disabled',false);
								$("select#tipoValor" + count).prop('selectedIndex',0);
							} else if(contadores[x] == 2) {
								$("select#tipoValor" + count).prop('disabled',false);
								$("select#tipoValor" + count).prop('selectedIndex',1);
							} else if(contadores[x] == 1) {
								$("select#tipoValor" + count).prop('disabled',false);
								$("select#tipoValor" + count).prop('selectedIndex',2);
							}	
							
							$("select#tipoRefeicao" + count).prop('disabled',false);
							
						} else {
							$(this).prop('disabled', false);
							
							$("select#tipoRefeicao" + count).prop('disabled',true);	
							$("select#tipoRefeicao" + count).prop('selectedIndex',0);
							$("select#tipoValor" + count).prop('disabled',true);
							$("select#tipoValor" + count).prop('selectedIndex',0);
					  	}
					}
					
					count++;
				 });
			});
		});		
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Comprar</strong>
          </h3>
        </div>
        
        <div id="reservaDisponivel" style="display: none;">
        
			<h4>Selecione o(s) dia(s) que deseja comprar:</h4>
			
			<br/> 
			
			<form:form method="POST" action="${actionSalvar}" modelAttribute="comprar">
			
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center">
						<input type="checkbox" id="selecionarTudo" /> <b>Selecionar todas as datas</b> 
					</div>
				</div>
			
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center">
						<form:checkboxes items="${todasAsDatas}" path="data" itemLabel="data" itemValue="id" delimiter="<br>"/>
					</div>	
					<div class="col-xs-12 col-sm-4 text-center selectRefeicao">	
					</div>		
					<div class="col-xs-12 col-sm-4 text-center selectValor">	
					</div>					
				</div>
				
				<div class="text-center">
					<br/>
					<button type="button" id="reservar" class="btn btn-primary">
					 	<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> Comprar
					</button>
				</div>
				
				<!-- Modal confirmar refeição -->
				<div id="modalConfirmarReserva" class="modal fade" role="dialog">
					<div class="modal-dialog modal-md">
				 		<div class="modal-content">
				 			<div class="modal-header">
				 				<button type="button" class="close cancelar" data-dismiss="modal">&times;</button>
				 				<h3 class="modal-title">Comprar</h3>
				 			</div>
				 			<div class="modal-body">
				 			</div>
				 			<div class="modal-footer">
				 				<div class="text-center">
				 					<button type="button" class="btn btn-default cancelar" data-dismiss="modal">Cancelar</button>
				 					<button type="submit" class="btn btn-primary">Confirmar compra</button>
				 				</div>
				 			</div>
				 		</div>
				 	</div>
				</div>
				
				<!-- Modal não selecionado -->
				<div id="modalNaoSelecionado" class="modal fade" role="dialog">
					<div class="modal-dialog modal-md">
				 		<div class="modal-content">
				 			<div class="modal-header">
				 				<button type="button" class="close cancelar" data-dismiss="modal">&times;</button>
				 				<h3 class="modal-title">Aviso</h3>
				 			</div>
				 			<div class="modal-body">
				 			</div>
				 			<div class="modal-footer">
				 				<div class="text-center">
				 					<button type="button" class="btn btn-default cancelar" data-dismiss="modal">Ok</button>
				 				</div>
				 			</div>
				 		</div>
				 	</div>
				</div>
			</form:form>
		
		</div>
		
		<div id="reservaIndisponivel" style="display: none;">
			
			<div class="alert alert-danger text-center" role="alert">
				<p><strong>Atenção!</strong> Fora do período de compras!</p>
			</div>
			
		</div>
		
	  </div>
	</div>
</body>
</html>