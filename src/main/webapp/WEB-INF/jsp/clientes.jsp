<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle
	includes="datatables.extended,jquery.validation,jquery.inputmask,font-awesome" />

<c:set var="linkController">
	<c:url value="/categorias" />
</c:set>

<html>
<head>
<meta name="header" content="Clientes" />
<title>Clientes</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			var formValidator = $("#cliente").validate({
				rules : {
					cpf : {
						required : true
					}
				}
			});
			$("#cpf").focus();
			$("#cpf").inputmask("999.999.999-99");
		});
	</script>

	<form name="cliente" id="cliente">
		<%@include file="/layouts/modal-processando.jsp"%>
		<div class="row">
			<div class="form-group col-xs-12 col-md-2">
				<label for="cpf" class="control-label">CPF</label>
				<div class="input-group">
					<input type="text" name="cpf" id="cpf" class="form-control" /> <span
						class="input-group-btn">
						<button type="button" class="btn btn-primary" name="btn_pesquisar"
							id="btn_pesquisar">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>
		</div>
	</form>

	<br />

	<datatables:table data="${listagemPendentes}" row="receber"
		id="GridDatatable">
		<datatables:column title="CPF" property="descricao" />
		<datatables:column title="Nome" property="descricao" />
		<datatables:column title="Categoria" property="descricao" />
		<datatables:column title="Status" property="descricao" />
		<datatables:column title="Biometria" property="descricao" />
		<datatables:column title="Operações" filterable="false"
			searchable="false" cssCellClass="text-center">
			<a href="${linkController}/editar/${categoria.id}"
				class="btn btn-default btn-xs" data-toggle="tooltip"
				title="Ver detalhes"> <span class='glyphicon glyphicon-plus'></span>
			</a>
			<a href="#" data-href="${linkController}/excluir/${categoria.id}"
				class="btn btn-danger btn-xs"> <span
				class='fa fa-hand-pointer-o' data-toggle="tooltip"
				title="Definir Biometria"></span>
			</a>
		</datatables:column>
		<datatables:callback function="fnFooterCallback" type="footer" />
		<datatables:extraJs bundles="datatables.extended.config"
			placeholder="before_start_document_ready" />
	</datatables:table>
</body>
</html>