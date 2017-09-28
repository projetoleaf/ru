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
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
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
			});
			
			$("#recarga").keyup(function () {
				
				var valor = $("#valor").val().replace(/,/g, '.');
				var recarga = $(this).val().replace(/,/g, '.');
				
				if(parseFloat(valor) < parseFloat(recarga)) {
					$("#erro").addClass("has-error");
					$("#confirmarRecarga").addClass("disabled");
					$("#troco").val("");
				}
				else {
					$("#erro").removeClass("has-error");
					$("#confirmarRecarga").removeClass("disabled");
					var resultado = valor - recarga;				
					var resultadoFormatado = resultado.toFixed(2);
					$("#troco").val(resultadoFormatado.replace(/\./g, ','));
				}
			});			
		});
	</script>
	
	<form:form action="${actionSalvar}" modelAttribute="creditos">
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
				<div class="col-sm-3 form-group">
					<label for="valor">Valor dado</label> 
					<input type="text" id="valor" class="form-control" >
				</div>
				<div id="erro" class="col-sm-3 form-group">
					<label for="recarga">Recarga</label> 
					<form:input path="recarga" class="form-control" />
				</div>
				<div class="col-sm-3 form-group">
					<label for="troco">Troco</label> 
					<input type="text" class="form-control" id="troco" readonly>
				</div>
				<div class="col-sm-3 form-group">
					<label for="troco">&nbsp;</label> 
					<button type="submit" id="confirmarRecarga" class="btn btn-primary disabled">Confirmar recarga</button>
				</div>
			</div>			
	      </div>
	    </div>    
	</form:form>
</body>
</html>