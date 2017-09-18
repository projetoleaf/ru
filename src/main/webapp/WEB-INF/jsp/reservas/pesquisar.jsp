<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,font-awesome" />

<c:set var="linkController"> <c:url value="/reservas" /> </c:set>

<html>
<head>
<meta name="header" content="Reservas" />
<title>Reservas</title>
</head>
<body>
	<datatables:table data="${listagemReservas}" row="reserva" id="GridDatatable">
		<datatables:column title="Nome" property="nome" />
		<!-- <datatables:column title="Créditos" property="creditos" /> -->
		<datatables:column title="${segunda}" property="segundaStatus" />
		<datatables:column title="${terca}" property="tercaStatus" />
		<datatables:column title="${quarta}" property="quartaStatus"/>
		<datatables:column title="${quinta}" property="quintaStatus" />
		<datatables:column title="${sexta}" property="sextaStatus"/>
		<datatables:column title="Operações" filterable="falseStatus"	searchable="false" cssCellClass="text-center" >
			<a href="${linkController}/pagamento/${reserva.nome}"
				class="btn btn-success btn-xs" data-toggle="tooltip"
				title="Efetivar pagamento"> <span class='fa fa-usd'></span>
			</a>
		</datatables:column>
		<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
	</datatables:table>
	
	<br />
	<br />
</body>
</html>