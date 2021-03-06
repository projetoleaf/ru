<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>

<c:set var="linkController"><c:url value="/status" /></c:set>

<dandelion:bundle includes="datatables.extended,floating.button,font-awesome,sweetalert2" />

<html>
<head>
<meta name="header" content="Status" />
<title>Status</title>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<script type="text/javascript">
		$(function () {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
		});
	
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
			
			$('.exclusao').on('click', function(e) {
				e.preventDefault();	
			   
				var tr = $(this).closest('tr');
			    var dadosTabela = tr.children("td").map(function() {
			        return $(this).text();
			    }).get();
			   
			   	var subcaminho = window.location.pathname.split("${pageContext.request.contextPath}/").pop();	   
			   
			   	var descricao = $.trim(dadosTabela[0]);	 	    
			   
			   	swal({
					  title: 'Você tem certeza?',
					  text: "Deseja realmente excluir " + descricao + "?",
					  type: 'warning',
					  showCancelButton: true,
					  confirmButtonText: "Excluir",
					  cancelButtonText: 'Não',
					  cancelButtonColor: '#d33'
				}).then( function () {					  
					  $.ajax({
							type: 'post',
						    url: "${pageContext.request.contextPath}/" + subcaminho + "/excluir/",
						  	data: {descricao: descricao}
						}).done(function(response){
							response = JSON.parse(response);
							
							if(response.sucesso){
								swal(
									'Excluído!',
								    'Este registro foi excluído!',
								    'success'
								).then(function(){
									location.reload();
								});	
							}
							else if (response.erro != null){
								switch(response.erro){
									case "exclusao":
										swal(
											'Oops...',
											'Esse registro possui relações com outra(s) tabela(s)!',
											'warning'
							        	)
							        	break;
									default:
										break;
								}
							}																			
						}).fail(function(jqXHR, textStatus, errorThrown){
							swal({
								title: 'Oops...',
								html: 'Não foi possível excluir este registro! <br>' +
								'Erro: ' + textStatus + '<br>' +
								'Descrição: ' + errorThrown,
								type: 'warning'
							})	
						});
				 }, function (dismiss) {})
			});
		});
	</script>

	<a href="${linkController}/incluir" class="float-button"><i	class="fa fa-plus"></i></a>

	<datatables:table data="${listagemStatus}" row="status" id="GridDatatable">
		<datatables:column title="Descrição" property="descricao" />
		<datatables:column title="Operações" filterable="false"	searchable="false" cssCellClass="text-center">
			<a href="${linkController}/editar/${status.id}"
				class="btn btn-default btn-xs" data-toggle="tooltip" title="Alterar">
				<span class='fa fa-pencil'></span>
			</a>
			<a class="btn btn-danger btn-xs exclusao"> <span
				class='fa fa-trash' data-toggle="tooltip"
				title="Excluir"></span>
			</a>
		</datatables:column>
		<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
	</datatables:table>
	<br />
	<br />
</body>
</html>