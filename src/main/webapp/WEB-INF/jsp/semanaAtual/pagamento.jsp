<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"><c:url value="/semanaAtual/salvar"/></c:set>

<dandelion:bundle includes="jquery.validation,jquery.inputmask" />

<html>
<head>
<meta name="header" content="Efetivação da Semana" />
<meta name="previouspage" content="<li><a href='<c:url value="/semanaAtual"/>'>Semana Atual</a></li>" />
<title>Efetivação da Semana</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {	 
			
			var datasDoCardapio = ${objectJSON};	
			var todasAsDatas = $("input:checkbox[name='data']");	
			var contadores = ${contadores};	
			var saldo = "${saldo}";	
			var valorRefeicaoS = ${valorRefeicaoS};
			var valorRefeicaoC = ${valorRefeicaoC};
			var caiu = 0;
			var zero = 0;
			var str = "";
			var str2 = "";
			
			$("#utilizarCreditos1").prop('disabled',true);
			$("#refeicoes").prop('disabled',true);
			$("#valor").prop('disabled',true);
			$("#habilitarRecargas").prop('disabled',true);
			$("#recargas").prop('disabled',true);
			$("#troco").prop('disabled',true);		
			
			$("#valor").val(zero.toFixed(2).replace(/\./g, ','));					
			$("#troco").val(zero.toFixed(2).replace(/\./g, ','));
			$("#recargas").val(zero.toFixed(2).replace(/\./g, ','));
			$("#refeicoes").val(zero.toFixed(2).replace(/\./g, ','));
			
			$("input:checkbox[name='data']").css("marginTop","7%");	
			
			var lista = [];
			lista.push("Tradicional");
			lista.push("Vegetariano");
			
			for (var x = 1; x <= todasAsDatas.length; x++) {	
				
				str += '<select id="tipoRefeicao' + x + '" name="tipoRefeicao" class="form-control">';	 
				str += '<option value="0" label="- Selecione uma refeição -"> </option>';				
				
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
			
			$("#semanaAtual .selectRefeicao").append(str);		
			$("#semanaAtual .selectValor").append(str2);	
			
			$("select").prop('disabled',true);					
			$("select").css("marginBottom","5%");
			
			$('input:checkbox[name="data"]').change(function(){			
				
				var qtdeChecks = $("input:checkbox[name='data']:checked").length;
				
				if($(this).prop("checked")) {					
					$("#valor").prop('disabled',false);
					$('#valor').focus();
					
					if($('#creditos').val() != 0.00)
						$("#utilizarCreditos1").prop('disabled',false);
					
					var valor = parseFloat(valorRefeicaoC) * qtdeChecks;
					var valorFormatado = valor.toFixed(2);
					
					$("#refeicoes").val(valorFormatado.replace(/\./g, ','));	
					
			  	} else if (qtdeChecks == 0){			  		
					$("#valor").prop('disabled',true);					
					$("#utilizarCreditos1").prop('disabled',true);				
					$("#utilizarCreditos1").prop("checked", false);
					
					$("#valor").val(qtdeChecks.toFixed(2).replace(/\./g, ','));					
					$("#troco").val(qtdeChecks.toFixed(2).replace(/\./g, ','));
					$("#recargas").val(qtdeChecks.toFixed(2).replace(/\./g, ','));
			  	}
				
				var $checkboxes = $("input:checkbox[name='data']");		
				var count = 1;
				
				$checkboxes.each(function(){
					
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
					
					count++;
				});
			});
			
			$('#habilitarRecargas').change(function(){
				
				if($(this).prop("checked")) {
					$("#recargas").prop('disabled',false);
			  	} else {
			  		$("#recargas").prop('disabled',true);
			  		$("#recargas").val(zero.toFixed(2).replace(/\./g, ','));
			  	}
			});
			
			$('#utilizarCreditos1').change(function(){
				
				if($(this).prop("checked")) {					
					
					var base = $('#creditos').val().replace(/,/g, '.');
					var creditos = base.replace(/[^\d.-]/g, '');
					var refeicoes = $("#refeicoes").val().replace(/,/g, '.');
					
					if(creditos >=  refeicoes) {
						
						$("#valor").val(zero.toFixed(2).replace(/\./g, ','));
						$("#recargas").val(zero.toFixed(2).replace(/\./g, ','));
						$("#troco").val(zero.toFixed(2).replace(/\./g, ','));	
						
						$("#valor").prop('disabled',true);					
					} else {
						$("#valor").prop('disabled',false);
						$('#valor').focus();
					}					
			  	} else {
			  		$("#valor").prop('disabled',false);
			  		$("#valor").val(zero.toFixed(2).replace(/\./g, ','));
			  		$('#valor').focus();
			  		$("#habilitarRecargas").prop('checked',false);
			  		$("#habilitarRecargas").prop('disabled',true);
			  		$("#recargas").prop('disabled',true);
			  		$("#recargas").val(zero.toFixed(2).replace(/\./g, ','));
			  		$("#troco").val(zero.toFixed(2).replace(/\./g, ','));
			  	}
			});
			
			$("#valor").keyup(function () {				
				
				var valor = $(this).val().replace(/,/g, '.');
				var refeicoes = $("#refeicoes").val().replace(/,/g, '.');
				var base = $('#creditos').val().replace(/,/g, '.');
				var creditos = base.replace(/[^\d.-]/g, '');
				
				if($("#utilizarCreditos1").prop("checked")) {		
					$("#habilitarRecargas").prop("checked", true);
					
					var resultado = parseFloat(creditos) - parseFloat(refeicoes);
					resultado += parseFloat(valor);
					
					var recarga = 0;
					
					if(resultado == 0) {
						recarga = valor;
					}					
					
					var resultadoFormatado = resultado.toFixed(2);	
					
					$("#troco").val(resultadoFormatado.replace(/\./g, ','));
					
					if(recarga != 0){
						$("#recargas").prop('disabled',false);
						$("#recargas").val(parseFloat(recarga).toFixed(2).replace(/\./g, ','));
					}			
					
					if(resultadoFormatado < 0) {					
						$("#valor").addClass("has-error");
						$("#confirmarPagamento").addClass("disabled");
						
						$("#habilitarRecargas").prop('disabled',true);		
						$("#recargas").prop('disabled',true);		
						$("#recargas").val(zero.toFixed(2).replace(/\./g, ','));
						$("#habilitarRecargas").prop("checked", false);
					} else {				
						$("#valor").removeClass("has-error");
						$("#confirmarPagamento").removeClass("disabled");
						
						$("#habilitarRecargas").prop('disabled',false);
						$("#recargas").prop('disabled',false);		
					}
				} else {
					var resultado = valor - refeicoes;				
					var resultadoFormatado = resultado.toFixed(2);		
					
					$("#troco").val(resultadoFormatado.replace(/\./g, ','));				
					
					if(resultadoFormatado < 0) {					
						$("#valor").addClass("has-error");
						$("#confirmarPagamento").addClass("disabled");
						
						$("#habilitarRecargas").prop('disabled',true);
					} else {				
						$("#valor").removeClass("has-error");
						$("#confirmarPagamento").removeClass("disabled");
						
						$("#habilitarRecargas").prop('disabled',false);
					}
				}
			});
			
			$("#recargas").keyup(function () {
				
				var valor = $("#valor").val().replace(/,/g, '.');
				var recarga = $(this).val().replace(/,/g, '.');
				var refeicoes = $("#refeicoes").val().replace(/,/g, '.');
				var base = $('#creditos').val().replace(/,/g, '.');
				var creditos = base.replace(/[^\d.-]/g, '');
				
				if($("#utilizarCreditos1").prop("checked")) {						
					var resultado = parseFloat(creditos) - parseFloat(refeicoes) + parseFloat(valor);	
					
					var recargaACompletar = parseFloat(valor) - resultado;
					
					resultado -= parseFloat(recarga);
					resultado += recargaACompletar;
					
					var resultadoFormatado = resultado.toFixed(2);	
					
					if(parseFloat(valor) < parseFloat(recarga) || resultadoFormatado < 0) {
						$("#recargas").addClass("has-error");
						$("#confirmarPagamento").addClass("disabled");
						$("#troco").val(zero.toFixed(2).replace(/\./g, ','));
					}
					else if(parseFloat(valor) == parseFloat(recarga)) {
						$("#troco").val(zero.toFixed(2).replace(/\./g, ','));
						$("#recargas").removeClass("has-error");
						$("#confirmarPagamento").removeClass("disabled");		
					}
					else if(parseFloat(recarga) == 0) {
						$("#recargas").val(recargaACompletar.toFixed(2).replace(/\./g, ","));
					}
					else {
						$("#recargas").removeClass("has-error");
						$("#confirmarPagamento").removeClass("disabled");			
						
						$("#troco").val(resultadoFormatado.replace(/\./g, ','));
					}		
				} else {
					var resultado = (valor - refeicoes) - recarga;				
					var resultadoFormatado = resultado.toFixed(2);
					
					if(parseFloat(valor) < parseFloat(recarga) || resultadoFormatado < 0) {
						$("#recargas").addClass("has-error");
						$("#confirmarPagamento").addClass("disabled");
						$("#troco").val(zero.toFixed(2).replace(/\./g, ','));
					}
					else {
						$("#recargas").removeClass("has-error");
						$("#confirmarPagamento").removeClass("disabled");			
						
						$("#troco").val(resultadoFormatado.replace(/\./g, ','));
					}		
				}
			});		
			
			$('#confirmarPagamento').click(function () {
				var todosOsStatus = $("input:checkbox[name='data']");
			    
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
  	<form:form action="${actionSalvar}" modelAttribute="semanaAtual">
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="semanaAtual"/></jsp:include>
	          <h3>
	            <strong>Efetivação da Semana</strong>
	          </h3>
	        </div>
	         <div class="row">
	            <div class="form-group col-xs-6 col-md-6">
	              <label class="control-label">Nome</label>
	              <form:input path="nome" class="form-control" value="${nome}" readonly="true"/>	              
	            </div>
	            <div class="form-group col-xs-6 col-md-6">
	              <label for="creditos" class="control-label">Créditos</label>
	              <input id="creditos" class="form-control" value="${saldo}" readonly/>
	            </div>	            
	        </div>
	        <br>
	        <div class="row">
				<div class="col-xs-12 col-sm-4 text-center">
					<input type="checkbox" id="selecionarTudo" /> <b>Selecionar todas as datas</b> 
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-12 col-sm-4 text-center">
					<form:checkboxes items="${todasAsDatas}" path="data" itemLabel="data" itemValue="id" delimiter="<br>" />
				</div>	
				<div class="col-xs-12 col-sm-4 text-center selectRefeicao">	
				</div>		
				<div class="col-xs-12 col-sm-4 text-center selectValor">	
				</div>						
			</div>
	        <br>
			<div class="row">
				<div class="col-sm-12 form-group">
					<form:checkbox path="utilizarCreditos"/> Utilizar créditos
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
					<form:input path="recargas" class="form-control"/>
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
			 				<h3 class="modal-title">Efetivação da Semana</h3>
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