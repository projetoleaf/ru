<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion"%>

<dandelion:bundle includes="datatables.extended,font-awesome,jquery.validation,jquery.inputmask,jquery.datetimepicker" />

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
				if($(this).text().includes("-"))
			  		$(this).css('color','red');
			});
			
			var formValidator = $("#extrato").validate({
	              rules : {
	                  dataInicial : { required : true },
	         		  dataFinal : { required : true }
	              }
	        });
			
			$("#btn_pesquisar").click(function() { 
				var dtInicial = $("#dataInicial").val();        	  
				var dtFinal = $("#dataFinal").val();
				    	  
				var objInicial = new Date();
				objInicial.setYear(dtInicial.split("/")[2]);
				objInicial.setMonth(dtInicial.split("/")[1] - 1);
				objInicial.setDate(dtInicial.split("/")[0]);
				
				var objFinal = new Date();
				objFinal.setYear(dtFinal.split("/")[2]);
				objFinal.setMonth(dtFinal.split("/")[1] - 1);
				objFinal.setDate(dtFinal.split("/")[0]);
				 
				if (objInicial.getTime() > objFinal.getTime()) {
					 	swal({
						title: "Data inválida!",
						text: "Data inicial não pode ser posterior a data final",
						type: "warning"
					}).then(function () {
						$("#dataInicial").val("");
						$("#dataInicial").focus();
					})
					
					return false;
				}
				    	  
	            var form = $("#extrato"); 
	            form.validate();
	            if (form.valid()) {
	            	form.submit();
	          	}
	        });
			
			$("#dataInicial").focus();
	        $("#dataInicial").inputmask("99/99/9999");
	        $("#dataFinal").inputmask("99/99/9999");
	        $("#dataI").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
	        $("#dataF").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
		});
	</script>
	<div class="col-xs-12">
		<div class="panel-body">
			<div class="page-header" style="margin-top: 10px;">
				<h3>
					<strong>Extrato</strong>
				</h3>
			</div>
			
			<form name="extrato" id="extrato" action="${linkController}">
				<%@include file="/layouts/modal-processando.jsp"%>
				<div class="row">
					<div class="form-group col-xs-12 col-md-3">
						<label for="dataInicial" class="control-label">Data
							Inicial</label>
						<div class="input-group date" id="dataI">
							<input type="text" name="dataInicial" id="dataInicial"
								class="form-control" /> <span class="input-group-addon">
								<span class="fa fa-calendar"></span>
							</span>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-3">
						<label for="dataFinal" class="control-label">Data Final</label>
						<div class="input-group date" id="dataF">
							<input type="text" name="dataFinal" id="dataFinal"
								class="form-control" /> <span class="input-group-addon">
								<span class="fa fa-calendar"></span>
							</span>
						</div>
					</div>

					<div class="form-group col-xs-12 col-md-3" style="margin-top: 25px">
						<button type="button" class="btn btn-primary" name="btn_pesquisar"
							id="btn_pesquisar">
							<span class="fa fa-search"></span>
						</button>
					</div>
				</div>
			</form>
			
			<br />
			<datatables:table data="${listagemExtrato}" id="GridDatatable">
				<datatables:column title="Data da Transação" property="dataTransacao" format="{0,date,dd/MM/yyyy HH:mm:ss}" />
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