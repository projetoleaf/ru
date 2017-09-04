<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion"%>

<dandelion:bundle includes="datatables.extended,font-awesome,jquery.validation,jquery.inputmask,jquery.datetimepicker" />

<c:set var="linkController"><c:url value="/historico"/></c:set>

<html>
<head>
<meta name="header" content="Histórico" />
<title>Histórico</title>
</head>
<body>
	<script type="text/javascript">
      $(document).ready(function() {
          var formValidator = $("#historico").validate({
              rules : {
                  txt_data : { required : true }
              }
          });
          $("#btn_pesquisar").click(function() {
              var form = $("#historico"); 
              form.validate();
              if (form.valid()) {
                  form.submit();
              }
          });
          $("#txt_data").focus();
          $("#txt_data").inputmask("99/99/9999");
          $("#data").datetimepicker({locale: "pt-br", format: "DD/MM/YYYY"});
      });
  </script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
		<div class="panel-body">
			<div class="page-header" style="margin-top: 10px;">
				<h3>
					<strong>Histórico</strong>
				</h3>
			</div>

			<form name="historico" id="historico" action="${linkController}">
				<%@include file="/layouts/modal-processando.jsp"%>
				<div class="row">
					<div class="form-group col-xs-12 col-md-4">
						<label for="txt_data" class="control-label">Até a Data</label>
						<div class="input-group date" id="data">
							<input type="text" name="txt_data" id="txt_data" class="form-control" value="${txt_data}"/> 
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span> 
							<span class="input-group-btn">
								<button type="button" class="btn btn-primary" name="btn_pesquisar" id="btn_pesquisar">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</span>
						</div>
					</div>
				</div>
			</form>

			<br />

			<datatables:table data="${listagemHistorico}" id="GridDatatable">
				<datatables:column title="Data" property="cardapio.data" sortInitDirection="desc" format="{0,date,dd/MM/yyyy}" sortType="date-uk" cssCellClass="text-center" />
				<datatables:column title="Status" property="status.descricao" cssCellClass="text-center" />
		
				<datatables:extraJs bundles="datatables.extended.config" placeholder="before_start_document_ready" />
			</datatables:table>
			<br /> <br />
			<!-- <div class="row">
				<div class="col-sm-4 pull-right">
					<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;Pago<br>
					<span class="glyphicon glyphicon-time" aria-hidden="true"></span>&nbsp;Solicitado<br> <span class="glyphicon glyphicon-ok-sign"
						aria-hidden="true"></span>&nbsp;Consumida<br> <span
						class="glyphicon glyphicon-download" aria-hidden="true"></span>&nbsp;Transferido<br>
					<span class="glyphicon glyphicon-upload" aria-hidden="true"></span>&nbsp;Transferente
				</div>
			</div> -->
		</div>
	</div>
</body>
</html>