<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,jquery.validation,jquery.inputmask,font-awesome" />

<c:set var="actionSalvar"><c:url value="/creditos/salvar"/></c:set>

<html>
<head>
<meta name="header" content="Créditos" />
<title>Créditos</title>
<!-- link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.9.1/sweetalert2.min.css"-->
</head>
<body>
	<!-- script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.9.1/sweetalert2.min.js"></script-->
	<script type="text/javascript">
		$(document).ready(function() {
			$("#confirmarRecarga").click(function(e) {
				e.preventDefault();
				var recarga = $("#recarga").val();
				recarga = recarga.replace(/\./g, '');
				if(recarga.replace(/,/g, '.') > 0)
				{
					recarga = recarga.replace(/\B(?=(\d{3})+(?!\d))/g,'.');
					swal({
						title: "Confirme o valor! ",
						text: "Valor da recarga: R$ "+recarga,
						type: "question",
						showCancelButton: true,
						cancelButtonText: 'Cancelar',
						cancelButtonColor: '#d33'
					}).then(function() {
						$("#recarga-form").submit();
					});
				}
			});
			$("#valor").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#recarga").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#troco").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			
			$("#valor").keyup(function () {
				$("#recarga").val($(this).val());
				darTroco();
			});
			
			$("#recarga").keyup(function () {
				darTroco();
			});		
		});
		function darTroco() {
			var valor = parseFloat($("#valor").val().replace(/[.,]/g, ''));
			var recarga = parseFloat($("#recarga").val().replace(/[.,]/g, ''));
			var troco = $("#valor").val();
			
			if(valor == 0)
			{
				$("#div-valor").addClass("has-error");
				$("#confirmarRecarga").addClass("disabled");
			}
			if(valor < recarga) {
				$("#div-recarga").addClass("has-error");
				$("#confirmarRecarga").addClass("disabled");
				$("#troco").val("");
			}
			else {
				var resultado = (valor - recarga)/100;
				resultado = resultado.toFixed(2).replace(/\./g, ',');
				var resultadoFormatado = resultado.replace(/\B(?=(\d{3})+(?!\d))/g,'.');
				$("#troco").val(resultadoFormatado);
				if(troco != '' && valor > 0)
				{
					$("#div-valor").removeClass("has-error");
					$("#div-recarga").removeClass("has-error");
					$("#confirmarRecarga").removeClass("disabled");
				}	
			}
		}
	</script>
	
	<form:form action="${actionSalvar}" modelAttribute="creditos" id="recarga-form">
	    <div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
	      <div class="panel-body">
	        <div class="page-header" style="margin-top: 10px;">
	          <jsp:include page="/layouts/modal-mensagens.jsp"><jsp:param name="model" value="creditos"/></jsp:include>
	          <h3>
	            <strong>Faça a recarga de créditos desejado:</strong>
	          </h3>
	        </div>
	         <div class="row">
	            <div class="form-group col-xs-6 col-md-6">
	              <label class="control-label">Nome</label>
	              <form:input path="nome" class="form-control" value="${nome}" readonly="true"/>	              
	            </div>
	            <div class="form-group col-xs-6 col-md-6">
	              <label for="saldo" class="control-label">Saldo</label>
	              <form:input path="saldo" class="form-control" value="${saldo}" readonly="true"/>
	            </div>	            
	        </div>
	        <br>
			<div class="row">
				<div id="div-valor" class="col-sm-3 form-group">
					<label for="valor">Valor dado</label> 
					<input type="text" id="valor" class="form-control" >
				</div>
				<div id="div-recarga" class="col-sm-3 form-group">
					<label for="recarga">Recarga</label> 
					<form:input path="recarga" class="form-control" />
				</div>
				<div class="col-sm-3 form-group">
					<label for="troco">Troco</label> 
					<input type="text" class="form-control" id="troco" readonly>
				</div>
				<div class="col-sm-3 form-group">
					<label>&emsp;</label> 
					<button type="submit" id="confirmarRecarga" class="form-control btn btn-primary disabled">Confirmar recarga</button>
				</div>
			</div>			
	      </div>
	    </div>    
	</form:form>
</body>
</html>