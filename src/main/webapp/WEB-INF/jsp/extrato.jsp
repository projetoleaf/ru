<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion"%>

<dandelion:bundle includes="datatables.extended" />

<c:set var="linkController"><c:url value="/extrato"/></c:set>

<html>
<head>
<meta name="header" content="Extrato" />
<title>Extrato</title>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#GridDatatable tr td').each(function(){
				//var valor = $(this).text().replace(/[.,]/g, '') / 100;
				//if(valor < 0)
				if($(this).text().includes("-"))
			  		$(this).css('color','red');
			});
		});
	</script>
	<div class="col-xs-12">
		<div class="panel-body">
			<div class="page-header" style="margin-top: 10px;">
				<h3>
					<strong>Extrato</strong>
				</h3>
			</div>
			<br />
			<datatables:table data="${listagemExtrato}" id="GridDatatable">
				<datatables:column title="Data da Transação" property="dataTransacao" format="{0,date,dd/MM/yyyy HH:MM:ss}" />
				<datatables:column title="Transação(R$)" property="transacao" format="{0,number,#,##0.00}"/>
				<datatables:column title="Saldo(R$)" property="saldo" format="{0,number,#,##0.00}"/>
		
				<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
			</datatables:table>
			<br /> 
		</div>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>