<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle
	includes="font-awesome,jquery.validation,jquery.inputmask,jquery.datetimepicker" />

<html>
<head>
<meta name="header" content="Planilhas" />
<title>Planilhas</title>
</head>
<body>
	<script type="text/javascript">
      $(document).ready(function() {
          var formValidator = $("#planilhas").validate({
              rules : {
                  dataInicial : { required : true },
         		  dataFinal : { required : true }
              }
          });
          $("#btn_pesquisar").click(function() {
              var form = $("#planilhas"); 
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

	<div class="col-xs-12 col-md-8 col-md-offset-2">

		<h4>Selecione alguns filtros e monte a sua planilha</h4>

		<br />

		<form name="planilhas" id="planilhas">
			<%@include file="/layouts/modal-processando.jsp"%>
			<div class="row">
				<div class="form-group col-xs-12 col-md-3">
					<label for="dataInicial" class="control-label">Data Inicial</label>
					<div class="input-group date" id="dataI">
						<input type="text" name="dataInicial" id="dataInicial"
							class="form-control" /> <span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>

				<div class="form-group col-xs-12 col-md-3">
					<label for="dataFinal" class="control-label">Data Final</label>
					<div class="input-group date" id="dataF">
						<input type="text" name="dataFinal" id="dataFinal"
							class="form-control" /> <span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>

				<div class="form-group col-xs-12 col-md-3" style="margin-top: 25px">
					<button type="button" class="btn btn-primary" name="btn_pesquisar"
						id="btn_pesquisar">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</div>
			</div>
		</form>

		<br />
		<form:form modelAttribute="tiposPlanilhas">

			<div class="page-header" style="margin-top: 10px;">
				<h4>
					<i class="fa fa-table" aria-hidden="true"></i> Planilhas
				</h4>
			</div>
			<div class="row">
				<form:checkboxes items="${planilhas}" path="planilha"
					delimiter="<br>" />
			</div>

			<br />
			<br />

			<div class="text-center">
				<a href="${pageContext.request.contextPath}/planilhas/gerarXSLX"
					class="btn btn-primary">Gerar planilha</a> <a
					href="${pageContext.request.contextPath}/planilhas/gerarPDF"
					target="_blank" class="btn btn-primary">Gerar PDF</a>
			</div>

			<br />
			<br />

		</form:form>

	</div>
</body>
</html>