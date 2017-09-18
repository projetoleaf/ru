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
		var cardapioObject = ${objectJSON};
	
		$( document ).ready(function() {	
			
			$('#reservaIndisponivel').hide();	
			$('#reservaDisponivel').hide();		    
			
			if(cardapioObject.length != 0)
			{				
				$('#reservaDisponivel').show();			
			}
			else
			{
				$('#reservaIndisponivel').show();
			}			
			
			$('input:checkbox[name="data"]').css("marginTop","5%");
			
			$('select').css("margin","0 0 7% 0");
			
			var todasAsDatas = $("input[type='checkbox']");		
			
			var str;
			
			var trad = "${todosOsTipos[0].descricao}";
			var veg = "${todosOsTipos[1].descricao}";
			
			var lista = [];
			lista.push(trad);
			lista.push(veg);	
			
			var val = 1;
			
			for (var x = 1; x <= todasAsDatas.length; x++) {	 
				
				str += '<select id="tipoRefeicao' + x + '" name="tipoRefeicao" class="form-control">';	 
				str += '<option value="" label="- Selecione uma refeição -"> </option>';				
				
				for (var z = 0; z < lista.length; z++) {										
					str += '<option value="' + val + '" label="' + lista[z] + '"> </option>';					
					val+=1;
				}
				
				str += '</select>';
	    	}
			
			$("#reservaDisponivel .selectList").append(str); 
		});	
		
		function reservar() {
		    var todasAsDatas = $("input[type='checkbox']");
		    var txt = "";
		    var base;
		    var minhaDataFormatada;
		    
		    if(todasAsDatas.is(":checked")) {
		    
			    $("#modalConfirmarReserva .modal-body").append("<p>Confirme os dias da sua reserva...</p>");
				
			    for (var i = 0; i < todasAsDatas.length; i++) {
			    	
			       if (todasAsDatas[i].checked) {
			    	   
			    	   for (var x = 0; x < cardapioObject.length; x++) {	    		   
			    		   
			    		   if(String(cardapioObject[x].id) === todasAsDatas[i].value) {
			    			   
			    			   base = cardapioObject[x].data;		    		   
			    			   minhaDataFormatada = new Date(base);
			    			   txt = txt + "<p>&#10004 " + minhaDataFormatada.toLocaleDateString('en-GB')  + "</p>";		    		   
			    		   }
			    	   }
			       }
			    }
			    
			    $("#modalConfirmarReserva .modal-body").append(txt);				    
			    $('#modalConfirmarReserva').modal('show');		    
			}
		    else{
		    	$('#modalNaoSelecionado').modal('show');
		    }
			
		}
		
		function cancelar() {
			$("#modalConfirmarReserva .modal-body").empty();
		}
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
					<br/> <br/>
					<button type="button" onclick="reservar()" class="btn btn-primary">
					 	<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> Reservar
					</button>
				</div>
				
				<!-- Modal confirmar refeição -->
				<div id="modalConfirmarReserva" class="modal fade" role="dialog">
					<div class="modal-dialog modal-md">
				 		<div class="modal-content">
				 			<div class="modal-header">
				 				<button type="button" onclick="cancelar()" class="close" data-dismiss="modal">&times;</button>
				 				<h3 class="modal-title">Reserva</h3>
				 			</div>
				 			<div class="modal-body">
				 			</div>
				 			<div class="modal-footer">
				 				<div class="text-center">
				 					<button type="button" onclick="cancelar()" class="btn btn-default" data-dismiss="modal">Cancelar</button>
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
				 				<button type="button" class="close" data-dismiss="modal">&times;</button>
				 				<h3 class="modal-title">Aviso</h3>
				 			</div>
				 			<div class="modal-body">
				 				<p>Selecione pelo menos um dia!</p>
				 			</div>
				 			<div class="modal-footer">
				 				<div class="text-center">
				 					<button type="button" class="btn btn-default" data-dismiss="modal">Ok</button>
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