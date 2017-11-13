<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended" />

<html>
<head>
	<meta name="header" content="Histórico do Cliente" />
	<title>Histórico do Cliente</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/geral.css" type="text/css"/>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
	<div class="col-xs-12">
		<div class="panel-body">
			<div class="page-header">
         		<h3><strong>Histórico do Cliente</strong></h3>
        	</div>
			<datatables:table data="${listagemHistorico}" id="Historico">
				<datatables:column title="Hora" property="data" format="{0,date,HH:MM:ss}" />
				<datatables:column title="Refeição" property="reservaItem.tipoRefeicao.descricao" />
		
				<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
			</datatables:table>
			<br /> <br />
		</div>
	</div>
	<jsp:include page="../verifica.jsp"/>
</body>
</html>