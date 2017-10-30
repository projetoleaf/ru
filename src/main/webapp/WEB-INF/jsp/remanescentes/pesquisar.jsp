<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,font-awesome,sweetalert2" />

<c:set var="linkController"> <c:url value="/remanescentes" /> </c:set>

<html>
<head>
<meta name="header" content="Remanescentes" />
<title>Remanescentes</title>
</head>
<body>
	<script type="text/javascript">			
		$(document).ready(function() {				
			var feriado = ${holiday};		
			
			if(feriado == 0) {				
				document.getElementById('reservaDisponivel').style.display = "block";
			} else {
				document.getElementById('reservaIndisponivel').style.display = "block";
			}
			
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
			    
			    if(count.seg != "Não reservado" && count.ter != "Não reservado" && count.qua != "Não reservado" && count.qui != "Não reservado" && count.sex != "Não reservado"){
			    	$("a[href='/ru/remanescentes/pagamento/" + count.nome + "']").addClass('disabled');
			    } else {
			    	$("a[href='/ru/remanescentes/pagamento/" + count.nome + "']").removeClass('disabled')
			    }
			}			
		});
	</script>
	
	<div id="reservaDisponivel" style="display: none;">
	
		<datatables:table data="${listagemRemanescentes}" row="remanescentes" id="GridDatatable">
			<datatables:column title="Nome" property="nome" />
			<datatables:column title="Créditos" property="creditos" /> 
			<datatables:column title="${segunda}" property="segundaStatus" />
			<datatables:column title="${terca}" property="tercaStatus" />
			<datatables:column title="${quarta}" property="quartaStatus"/>
			<datatables:column title="${quinta}" property="quintaStatus" />
			<datatables:column title="${sexta}" property="sextaStatus"/>
			<datatables:column title="Operações" filterable="falseStatus" searchable="false" cssCellClass="text-center" >
				<a href="${linkController}/pagamento/${remanescentes.nome}"
					class="btn btn-success btn-xs" data-toggle="tooltip"
					title="Efetivar pagamento"> <span class='fa fa-usd'></span>
				</a>
			</datatables:column>
			<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
		</datatables:table>
	
	</div>
	
	<div id="reservaIndisponivel" style="display: none;">
			
		<div class="alert alert-danger text-center" role="alert">
			<p><strong>Atenção!</strong> Fora do período de reservas remanescentes!</p>
		</div>
		
	</div>
	
	<br />
	<br />
</body>
</html>