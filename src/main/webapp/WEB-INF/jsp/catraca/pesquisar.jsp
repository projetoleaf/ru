<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle includes="datatables.extended,font-awesome,sweetalert2" />

<c:set var="linkController"> <c:url value="/catraca" /> </c:set>

<html>
<head>
	<meta name="header" content="Catraca" />
	<title>Catraca</title>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/geral.css" type="text/css"/>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$('[data-toggle="tooltip"]').tooltip();
			
			$("#girarCatraca").click(function() {
				
				var tr = $(this).closest('tr');
			    var dadosTabela = tr.children("td").map(function() {
			        return $(this).text();
			    }).get();
			    
			    var nome = $.trim(dadosTabela[0]);
			    
			    $.ajax({
					type: 'post',
				    url: "${pageContext.request.contextPath}/catraca/salvar",
				  	data: {nome: nome}
				}).done(function(){
					swal(
					    'Acesso Liberado!',
					    'Pode liberar a pessoa!',
					    'success'
					).then(function(){
						location.reload();
					});															
				}).fail(function(){
					swal(
						'Oops...',
						'Não foi possível liberar o acesso!',
						'warning'
		        	)	
				});
			});
		});
	</script>
	<div class="col-xs-12">
		<div class="panel-body">
			<div class="page-header">
         		<h3><strong>Catraca</strong></h3>
        	</div>
        	
        	<h4><strong>Não consumiram</strong></h4>
			<datatables:table data="${listagemCatraca}" row="catraca" id="Catraca">
				<datatables:column title="Nome" property="nome" />
				<datatables:column title="${dataAtual}" property="mensagem" />
				<datatables:column title="Operações" filterable="false"	searchable="false" cssCellClass="text-center">
					<a id="girarCatraca" 
						class="btn btn-default btn-xs" data-toggle="tooltip" title="Girar a Roleta">
						<i class='fa fa-spinner'></i>
					</a>
					<a href="${linkController}/historico/${catraca.nome}"
						class="btn btn-default btn-xs" data-toggle="tooltip" title="Histórico do Cliente">
						<i class='fa fa-calendar'></i>
					</a>
				</datatables:column >		
				<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
			</datatables:table>
			
			<br /> <br />
			
			<h4><strong>Consumiram</strong></h4>
			<datatables:table data="${listagemHistorico}" id="Historico">
				<datatables:column title="Nome" property="cliente.nome" />
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