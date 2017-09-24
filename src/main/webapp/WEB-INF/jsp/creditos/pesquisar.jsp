<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,font-awesome" />

<c:set var="linkController"> <c:url value="/creditos" /> </c:set>

<html>
<head>
<meta name="header" content="Créditos" />
<title>Créditos</title>
</head>
<body>
	<datatables:table data="${listagemCreditos}" row="credito" id="GridDatatable">
		<datatables:column title="Nome" property="nome" />
		<datatables:column title="Créditos" property="saldo" />
		<datatables:column title="Operações" filterable="falseStatus" searchable="false" cssCellClass="text-center" >
			<a href="${linkController}/recarga/${credito.nome}"
				class="btn btn-success btn-xs" data-toggle="tooltip"
				title="Colocar créditos"> <span class='fa fa-usd'></span>
			</a>
		</datatables:column>
		<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
	</datatables:table>
	
	<br />
	<br />
</body>
</html>