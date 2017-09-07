<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,jquery.validation,jquery.inputmask,font-awesome" />

<c:set var="linkController"> <c:url value="/reservas" /> </c:set>

<html>
<head>
<meta name="header" content="Reservas" />
<title>Reservas</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
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
			
			$('#valor').hide();
	    	$('#refeicoes').hide();
	    	$('#recargas').hide();
	    	$('#troco').hide();
	    	$('#btnPagamento').hide();
	    	$('label[for="valor"]').hide();
	    	$('label[for="refeicoes"]').hide();
	    	$('label[for="recargas"]').hide();
	    	$('label[for="troco"]').hide();
	    	
	    	$("#recargas").prop('disabled',true);
	    	
			$('#habilitarRecargas').change(function(){
				
				if($(this).prop("checked")) {
					$("#recargas").prop('disabled',false);
			  	} else {
			  		$("#recargas").prop('disabled',true);
			  	}
			});
			
			$('#utilizarCreditos').change(function(){
				
				if($(this).prop("checked")) {
			    	$('#valor').show();
			    	$('#refeicoes').show();
			    	$('#recargas').show();
			    	$('#troco').show();
			    	$('#btnPagamento').show();
			    	$('label[for="valor"]').show();
			    	$('label[for="refeicoes"]').show();
			    	$('label[for="recargas"]').show();
			    	$('label[for="troco"]').show();
			  	} else {
			    	$('#valor').hide();
			    	$('#refeicoes').hide();
			    	$('#recargas').hide();
			    	$('#troco').hide();
			    	$('#btnPagamento').hide();
			    	$('label[for="valor"]').hide();
			    	$('label[for="refeicoes"]').hide();
			    	$('label[for="recargas"]').hide();
			    	$('label[for="troco"]').hide();
			  	}
			});
		});
	</script>

	<datatables:table data="${listagemReservas}" row="reserva" id="GridDatatable">
		<datatables:column title="Nome" property="nome" />
		<datatables:column title="Créditos" property="creditos" />
		<datatables:column title="S (21/12/2018)" property="descricao" />
		<datatables:column title="T (21/12/2018)" property="descricao" />
		<datatables:column title="Q (21/12/2018)" property="descricao" />
		<datatables:column title="Q (21/12/2018)" property="descricao" />
		<datatables:column title="S (21/12/2018)" property="descricao" />
		<datatables:callback function="fnFooterCallback" type="footer" />
		<datatables:extraJs bundles="datatables.extended.config"
			placeholder="before_start_document_ready" />
	</datatables:table>
	
	<br />

	<form>
		<div class="row">
			<div class="col-sm-12 form-group">
				<input type="checkbox" id="utilizarCreditos"> Utilizar créditos
			</div>
		</div>
		<div class="row">
			<div class="col-sm-2 form-group">
				<label for="valor">Valor dado</label> 
				<input type="text" class="form-control" id="valor">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-2 form-group" style="margin-top: 2px">
				<label for="refeicoes">Refeições</label> 
				<input type="text" class="form-control" id="refeicoes">
			</div>
			<div class="col-sm-2 form-group">
				<label for="recargas"> <input type="checkbox" id="habilitarRecargas"> Recargas</label> 
				<input type="text" class="form-control" id="recargas">
			</div>
			<div class="col-sm-2 form-group" style="margin-top: 2px">
				<label for="troco">Troco</label> 
				<input type="text" class="form-control" id="troco">
			</div>
			<div class="col-sm-2 form-group" style="margin-top: 27px">
				<a href="#" id="btnPagamento" class="btn btn-primary">Confirmar pagamento</a>
			</div>
		</div>
	</form>
	<br />
	<br />
</body>
</html>