<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"> <c:url value="/reserva/salvar"/> </c:set>

<html>
<head>
<meta name="header" content="Reserva" />
<title>Reserva</title>
</head>
<body>
	<script>	
		$( document ).ready(function() {	
			
			var situacao = ${situacao};
			var datasDoCardapio = ${objectJSON};			
			var diasDaSemanaReservados = "${diasDaSemanaReservados}";
			var todasAsDatas = $("input[type='checkbox']");							
			var trad = "${todosOsTipos[0].descricao}";
			var veg = "${todosOsTipos[1].descricao}";			
			var str = "";
			var val = 1;	
			
			$("input[type='checkbox']").css("marginTop","7%");	
			
			if(datasDoCardapio.length != 0) {				
				$('#reservaDisponivel').show();		
				$('#reservaIndisponivel').hide();	
			} else {
				$('#reservaIndisponivel').show();
				$('#reservaDisponivel').hide();	
			}			
			
			var lista = [];
			lista.push(trad);
			lista.push(veg);				
			
			for (var x = 1; x <= todasAsDatas.length; x++) {	
				
				str += '<select id="tipoRefeicao' + x + '" name="tipoRefeicao" class="form-control">';	 
				str += '<option value="0" label="- Selecione uma refeição -"> </option>';				
				
				for (var z = 0; z < lista.length; z++) {										
					str += '<option value="' + (z+1) + '" label="' + lista[z] + '"> </option>';		
				}
				
				str += '</select>';
	    	}
			
			$("#reservaDisponivel .selectList").append(str);			
			$("select").prop('disabled',true);		
			
			$("select").css("marginBottom","5%");
			
			$("input[type='checkbox']").change(function(){
				
				var $checkboxes = $(this).closest('form').find(':checkbox');				
				var count = 1;
				
				$checkboxes.each(function(){
					
					if ($(this).is(":checked")) {		
						$("select#tipoRefeicao" + count).prop('disabled',false);
					} else {
						$("select#tipoRefeicao" + count).prop('disabled',true);	
						$("select#tipoRefeicao" + count).prop('selectedIndex',0);
				  	}
					
					count++;
				});
			});
			
			$("#reservar").click(function() {
				var $checkboxes = $(this).closest('form').find(':checkbox');
			    var txt = "";
			    var txt2 = "";
			    var minhaDataFormatada;
			    var selects = $("select").length;
			    var count = 0;
			    var count2 = 0;
			    
			    if($checkboxes.is(":checked")) {
			    	
			    	for(var a = 1; a <= selects; a++) {
			    		
			    		if($('#data' + a).is(":checked")) {
			    			
			    			for(var b = 0; b < datasDoCardapio.length; b++) {	
			    				
			    				if(String(datasDoCardapio[b].id) === $('#data' + a).val()) {
			    					
			    					if ($("select#tipoRefeicao" + a).val() != 0) {
			    						
			    						txt2 = ($("select#tipoRefeicao" + a).val() % 2 ? "Tradicional" : "Vegetariano");	 		   
						    			minhaDataFormatada = new Date(datasDoCardapio[b].data);
						    			txt = txt + "<p>&#10004 " + minhaDataFormatada.toLocaleDateString('en-GB')  + " - " + txt2 + "</p>";	
						    			
						    			count++;
			    					} else {
			    						count2++;
			    					}				    	    		
				    	    	}					    			
				    		}  			
			    		} 	    		    		
			    	}
			    	
			    	if(count != 0 && count2 == 0) {			    		
			    		$("#modalConfirmarReserva .modal-body").append("<p>Confirme os dias da sua reserva e o seu tipo de refeição...</p>");			    		
			    		$("#modalConfirmarReserva .modal-body").append(txt);				    
						$('#modalConfirmarReserva').modal('show');	
			    	} else {			    		
			    		$("#modalNaoSelecionado .modal-body").append("<p>Selecione uma refeição!</p>");		
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
		});		
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Reserva</strong>
          </h3>
        </div>
        
        <div id="reservaDisponivel">
        
			<h4>Selecione o(s) dia(s) que deseja reservar:</h4>
			
			<br/> 
			
			<form:form method="POST" action="${actionSalvar}" modelAttribute="datas">
				<div class="row">
					<div class="col-xs-12 col-sm-4 text-center">
						<form:checkboxes items="${todasAsDatas}" path="data" itemLabel="data" itemValue="id" delimiter="<br>"/>
					</div>	
					<div class="col-xs-12 col-sm-4 text-center selectList">	
					</div>					
				</div>
				
				<div class="text-center">
					<br/>
					<button type="button" id="reservar" class="btn btn-primary">
					 	<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> Reservar
					</button>
				</div>
				
				<!-- Modal confirmar refeição -->
				<div id="modalConfirmarReserva" class="modal fade" role="dialog">
					<div class="modal-dialog modal-md">
				 		<div class="modal-content">
				 			<div class="modal-header">
				 				<button type="button" class="close cancelar" data-dismiss="modal">&times;</button>
				 				<h3 class="modal-title">Reserva</h3>
				 			</div>
				 			<div class="modal-body">
				 			</div>
				 			<div class="modal-footer">
				 				<div class="text-center">
				 					<button type="button" class="btn btn-default cancelar" data-dismiss="modal">Cancelar</button>
				 					<button type="submit" class="btn btn-primary">Confirmar reserva</button>
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
		
		<div id="reservaIndisponivel">
			
			<div class="alert alert-danger text-center" role="alert">
				<p><strong>Atenção!</strong> Fora do período de reservas!</p>
			</div>
			
		</div>
		
	  </div>
	</div>
</body>
</html>