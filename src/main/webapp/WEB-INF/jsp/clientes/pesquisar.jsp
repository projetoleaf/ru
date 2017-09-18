<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,font-awesome" />

<c:set var="linkController"> <c:url value="/clientes" /> </c:set>

<html>
<head>
<meta name="header" content="Clientes" />
<title>Clientes</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
	<datatables:table data="${listagemClientes}" row="clienteCategoria" id="GridDatatable">
		<datatables:column title="CPF" property="cliente.cpf" />
		<datatables:column title="Nome" property="cliente.nome" />
		<datatables:column title="Categoria" property="categoria.descricao" />
		<datatables:column title="Biometria" property="cliente.biometria" />
		<datatables:column title="Operações" filterable="false"	searchable="false" cssCellClass="text-center" >
			<a href="${linkController}/editarCategoria/${clienteCategoria.id}"
				class="btn btn-default btn-xs" data-toggle="tooltip"
				title="Editar Categoria"> <span class='glyphicon glyphicon-pencil'> </span>
			</a>
			<a href="${linkController}/definirBiometria/${clienteCategoria.id}"
				class="btn btn-danger btn-xs" data-toggle="tooltip"
				title="Definir Biometria"> <span class='fa fa-hand-pointer-o'> </span>
			</a>
		</datatables:column>
		<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
	</datatables:table>
</body>
</html>