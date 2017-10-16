<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<c:set var="linkController"><c:url value="/cardapios" /></c:set>

<dandelion:bundle includes="datatables.extended,floating.button,font-awesome,sweetalert2" />

<html>
<head>
<meta name="header" content="Cardápios" />
<title>Cardápios</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>

	<%@include file="/layouts/modal-mensagens.jsp"%>
	<%@include file="/layouts/modal-exclusao.jsp"%>

	<a href="${linkController}/incluir" class="float-button"><i	class="fa fa-plus"></i></a>
	
	<datatables:table data="${listagemCardapios}" row="cardapio" id="GridDatatable">
		<datatables:column title="Data" property="data"	format="{0,date,dd/MM/yyyy}" sortType="date-uk" />
		<datatables:column title="Período" property="periodoRefeicao.descricao" />
		<datatables:column title="Prato Base" property="pratoBase" />
		<datatables:column title="Prato Tradicional" property="pratoTradicional" />
		<datatables:column title="Prato Vegetariano" property="pratoVegetariano" />
		<datatables:column title="Guarnição" property="guarnicao" />
		<datatables:column title="Salada" property="salada" />
		<datatables:column title="Sobremesa" property="sobremesa" />
		<datatables:column title="Suco" property="suco" />		
		<datatables:column title="Operações" filterable="false"	searchable="false" cssCellClass="text-center">
			<a href="${linkController}/editar/${cardapio.id}"
				class="btn btn-default btn-xs" data-toggle="tooltip" title="Alterar">
				<span class='glyphicon glyphicon-pencil'></span>
			</a>
			<a href="#" data-href="${linkController}/excluir/${cardapio.id}"
				data-mensagem-exclusao="Deseja realmente excluir ${cardapio.data}?"
				data-toggle="modal" data-target="#janela-exclusao-modal"
				class="btn btn-danger btn-xs"> <span
				class='glyphicon glyphicon-trash' data-toggle="tooltip"
				title="Excluir"></span>
			</a>
		</datatables:column>
		<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
	</datatables:table>
	<br />
	<br />
</body>
</html>