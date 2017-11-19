<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion"%>

<dandelion:bundle
	includes="datatables.extended,font-awesome,jquery.validation,jquery.inputmask,jquery.datetimepicker" />

<c:set var="linkController">
	<c:url value="/historico" />
</c:set>

<html>
<head>
<meta name="header" content="Histórico de Refeições" />
<title>Histórico de Refeições</title>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<script type="text/javascript">
      $(document).ready(function() {
    	  var formValidator = $("#historico").validate({
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
        	  
              var form = $("#historico"); 
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
			<div class="page-header" style="margin-top: 15px;">
				<h3>
					<strong>Histórico de Refeições</strong>
				</h3>
			</div>

			<form name="historico" id="historico" action="${linkController}">
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

			<datatables:table data="${listagemHistorico}" id="GridDatatable">
				<datatables:column title="Data da Refeição" property="cardapio.data"
					format="{0,date,dd/MM/yyyy}"
					sortType="date-uk" />
				<datatables:column title="Período da Refeição"
					property="cardapio.periodoRefeicao.descricao" />
				<datatables:column title="Tipo da Refeição"	property="descricaoTipo" />
				<datatables:column title="Valor (R$)" property="valor" />
				<datatables:column title="Status" property="descricaoStatus" />

				<datatables:extraJs bundles="datatables.extended.config"
					placeholder="before_start_document_ready" />
			</datatables:table>
			<br /> <br />
		</div>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>