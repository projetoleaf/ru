<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/reservas/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask" />

<html>
<head>
<meta name="header" content="Efetivação das Reservas" />
<meta name="previouspage" content="<li><a href='<c:url value="/reservas"/>'>Reservas</a></li>" />
<title>Efetivação das Reservas</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {	 
			
			var caiu = 0;
			
			$("#utilizarCreditos").prop('disabled',true);
			$("#refeicoes").prop('disabled',true);
			$("#valor").prop('disabled',true);
			$("#habilitarRecargas").prop('disabled',true);
			$("#recargas").prop('disabled',true);
			$("#troco").prop('disabled',true);		
			
			$('input:checkbox[name="datas"]').change(function(){
				
				if($(this).prop("checked")) {					
					$("#valor").prop('disabled',false);
					$("#habilitarRecargas").prop('disabled',false);
					$("#troco").prop('disabled',false);
					$('#valor').focus();
					
					if($('#creditos').val() != 0.00)
						$("#utilizarCreditos").prop('disabled',false);
					
			  	} else {
			  		$("#utilizarCreditos").prop('disabled',true);
					$("#valor").prop('disabled',true);
					$("#habilitarRecargas").prop('disabled',true);
					$("#troco").prop('disabled',true);		
			  	}
			});
			
			$('#habilitarRecargas').change(function(){
				
				if($(this).prop("checked")) {
					$("#recargas").prop('disabled',false);
			  	} else {
			  		$("#recargas").prop('disabled',true);
			  	}
			});
			
			$('#confirmarPagamento').click(function () {
				var todosOsStatus = $("input:checkbox[name='datas']");
			    
			    if(!todosOsStatus.is(":checked")) {			
			    	$("#modalErro .modal-body").append("<p>Selecione pelo menos um dia!</p>");
			    	$('#modalErro').modal('show');			        	    
				}
			    else if($('#valor').val() == "") {
			    	$("#modalErro .modal-body").append("<p>Digite o valor pago pelo o cliente!</p>");			    	
			    	$('#modalErro').modal('show');				    	
			    	caiu = 1;
			    }
			    else{
			    	$('#modalConfirmarPagamento').modal('show');	
			    }
		    });		
			
			$('.fechar').click(function () {
				$("#modalConfirmarPagamento .modal-body").empty();
				$("#modalErro .modal-body").empty();
				
				if(caiu = 1)
					$('#valor').focus();
		    });	
			
			$("#valor").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#refeicoes").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#recargas").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#troco").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });    	
		});
  	</script>
  	<form:form action="${actionSalvar}" modelAttribute="reserva">
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="reserva"/></jsp:include>
	          <h3>
	            <strong>Efetivação das Reservas</strong>
	          </h3>
	        </div>
	         <div class="row">
	            <div class="form-group col-xs-6 col-md-6">
	              <label class="control-label">Nome</label>
	              <form:input path="nome" class="form-control" value="${cliente.nome}" readonly="true"/>	              
	            </div>
	            <div class="form-group col-xs-6 col-md-6">
	              <label for="creditos" class="control-label">Créditos</label>
	              <input class="form-control" id="creditos" value="${cliente.creditos}" readonly="true"/>	
	            </div>	            
	        </div>
	        <br>
	        <div class="row">
	        	<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4 text-center">
	        		<form:checkboxes items="${datasReservas}" path="datas" delimiter="<br>" />
	        	</div>
	        </div>
	        <br>
			<div class="row">
				<div class="col-sm-12 form-group">
					<input type="checkbox" id="utilizarCreditos"> Utilizar créditos
				</div>
			</div>
			<div class="row">
				<div class="col-sm-3 form-group" style="margin-top: 2px">
					<label for="refeicoes">Refeições</label> 
					<input type="text" class="form-control" id="refeicoes">
				</div>				
			</div>
			<div class="row">
				<div class="col-sm-3 form-group">
					<label for="valor">Valor dado</label> 
					<form:input path="valor" class="form-control" />
				</div>
				<div class="col-sm-3 form-group">
					<label for="recargas"> <input type="checkbox" id="habilitarRecargas"> Recargas</label> 
					<input type="text" class="form-control" id="recargas">
				</div>
				<div class="col-sm-3 form-group" style="margin-top: 2px">
					<label for="troco">Troco</label> 
					<input type="text" class="form-control" id="troco">
				</div>
				<div class="col-sm-3 form-group" style="margin-top: 27px">
					<button type="button" id="confirmarPagamento" class="btn btn-primary">
					 	Pagar
					</button>
				</div>
			</div>
	        
	        <!-- Modal confirmar pagamento -->
			<div id="modalConfirmarPagamento" class="modal fade" role="dialog">
				<div class="modal-dialog modal-md">
			 		<div class="modal-content">
			 			<div class="modal-header">
			 				<button type="button" class="close fechar" data-dismiss="modal">&times;</button>
			 				<h3 class="modal-title">Efetivação das Reservas</h3>
			 			</div>
			 			<div class="modal-body">
			 			</div>
			 			<div class="modal-footer">
			 				<div class="text-center">
			 					<button type="button" class="btn btn-default fechar" data-dismiss="modal">Cancelar</button>
			 					<button type="submit" class="btn btn-primary">Confirmar pagamento</button>
			 				</div>
			 			</div>
			 		</div>
			 	</div>
			</div>
		    
		    <!-- Modal erro -->
			<div id="modalErro" class="modal fade" role="dialog">
				<div class="modal-dialog modal-md">
			 		<div class="modal-content">
			 			<div class="modal-header">
			 				<button type="button" class="close fechar" data-dismiss="modal">&times;</button>
			 				<h3 class="modal-title">Aviso</h3>
			 			</div>
			 			<div class="modal-body">
			 			</div>
			 			<div class="modal-footer">
			 				<div class="text-center">
			 					<button type="button" class="btn btn-default fechar" data-dismiss="modal">Ok</button>
			 				</div>
			 			</div>
			 		</div>
			 	</div>
			</div>
			
	      </div>
	    </div>    
	</form:form>
</body>
</html>