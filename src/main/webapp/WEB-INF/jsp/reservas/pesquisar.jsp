<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,font-awesome,sweetalert2" />

<c:set var="linkController"> <c:url value="/reservas" /> </c:set>

<html>
<head>
<meta name="header" content="Reservas" />
<title>Reservas</title>
</head>
<body>
	<script type="text/javascript">			
		$(document).ready(function() {	
			
			var linhasDaTabela = $('#GridDatatable tr:has(td)').map(function(i, v) {
			    var $td =  $('td', this);
			        return {
			                 id: ++i,
			                 nome: $td.eq(0).text(),
			                 creditos: $td.eq(1).text(),
			                 seg: $td.eq(2).text(),
			                 ter: $td.eq(3).text(),
			                 qua: $td.eq(4).text(),
			                 qui: $td.eq(5).text(),
			                 sex: $td.eq(6).text()
			               }
			}).get();
			
			for (let count of linhasDaTabela) {
			    
			    if(count.seg != "Solicitada" && count.ter != "Solicitada" && count.qua != "Solicitada" && count.qui != "Solicitada" && count.sex != "Solicitada"){
			    	$("a[href='${pageContext.request.contextPath}/reservas/pagamento/" + count.nome + "']").addClass('disabled');
			    } else {
			    	$("a[href='${pageContext.request.contextPath}/reservas/pagamento/" + count.nome + "']").removeClass('disabled')
			    }
			}			
		});
	</script>
	
	<datatables:table data="${listagemReservas}" row="reserva" id="GridDatatable">
		<datatables:column title="Nome" property="nome" />
		<datatables:column title="Créditos" property="creditos" /> 
		<datatables:column title="${segunda}" property="segundaStatus" />
		<datatables:column title="${terca}" property="tercaStatus" />
		<datatables:column title="${quarta}" property="quartaStatus"/>
		<datatables:column title="${quinta}" property="quintaStatus" />
		<datatables:column title="${sexta}" property="sextaStatus"/>
		<datatables:column title="Operações" filterable="falseStatus" searchable="false" cssCellClass="text-center" >
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