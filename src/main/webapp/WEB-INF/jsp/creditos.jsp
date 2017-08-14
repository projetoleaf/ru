<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,jquery.validation,jquery.inputmask,font-awesome" />

<c:set var="linkController"> <c:url value="/creditos" /> </c:set>

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

	<datatables:table data="${listagemCreditos}" row="creditos" id="GridDatatable">
		<datatables:column title="Nome" property="descricao" />
		<datatables:column title="Créditos" property="descricao" />
		<datatables:callback function="fnFooterCallback" type="footer" />
		<datatables:extraJs bundles="datatables.extended.config"
			placeholder="before_start_document_ready" />
	</datatables:table>
	
	<br />

	<form>
		<h4>Faça a recarga de créditos desejado:</h4>
		<div class="row">
			<div class="col-sm-2 form-group">
				<label for="valor">Valor dado</label> 
				<input type="text" class="form-control" id="valor">
			</div>
			<div class="col-sm-2 form-group">
				<label for="recarga"> Recarga</label> 
				<input type="text" class="form-control" id="recargas">
			</div>
			<div class="col-sm-2 form-group">
				<label for="troco">Troco</label> 
				<input type="text" class="form-control" id="troco">
			</div>
			<div class="col-sm-2 form-group" style="margin-top: 25px">
				<a href="#" class="btn btn-primary">Confirmar recarga</a>
			</div>
		</div>
	</form>
	<br />
	<br />
</body>
</html>