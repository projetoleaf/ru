<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="datatables" uri="http://github.com/dandelion/datatables"%>

<dandelion:bundle	includes="datatables.extended,jquery.validation,jquery.inputmask,font-awesome" />

<c:set var="linkController">
	<c:url value="/semanaAtual" />
</c:set>

<html>
<head>
<meta name="header" content="Semana Atual" />
<title>Semana Atual</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			var formValidator = $("#cliente").validate({
				rules : {
					cpf : {
						required : true
					}
				}
			});
			$("#cpf").focus();
			$("#cpf").inputmask("999.999.999-99");
			$("#valor").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#refeicoes").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#recargas").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
			$("#troco").maskMoney({
		          //prefix: "R$ ",
		          decimal: ",",
		          thousands: "."
		     });
		});
	</script>

	<form name="cliente" id="cliente">
		<%@include file="/layouts/modal-processando.jsp"%>
		<div class="row">
			<div class="form-group col-xs-12 col-md-2">
				<label for="cpf" class="control-label">CPF</label>
				<div class="input-group">
					<input type="text" name="cpf" id="cpf" class="form-control" /> <span
						class="input-group-btn">
						<button type="button" class="btn btn-primary" name="btn_pesquisar"
							id="btn_pesquisar">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</div>
		</div>
	</form>

	<br />

	<datatables:table data="${listagemSemanaAtual}" row="atual" id="GridDatatable">
		<datatables:column title="Nome" property="nome" />
		<datatables:column title="Créditos" property="creditos" />
		<datatables:column title="S (21/12/2018)" property="descricao" />
		<datatables:column title="T (21/12/2018)" property="descricao" />
		<datatables:column title="Q (21/12/2018)" property="descricao" />
		<datatables:column title="Q (21/12/2018)" property="descricao" />
		<datatables:column title="S (21/12/2018)" property="descricao" />
		<datatables:callback function="fnFooterCallback" type="footer" />
		<datatables:extraJs bundles="datatables.extended.config"
			placeholder="before_start_document_ready" />
	</datatables:table>
	
	<br />

	<form>
		<div class="row">
			<div class="col-sm-12 form-group">
				<input type="checkbox" id="credito"> Utilizar créditos
			</div>
		</div>
		
		<h4>Selecione o(s) dia(s) que deseja vender</h4>
		
		<div class="row">
			<div class="col-sm-12 form-group text-center"> 
				<div class="radio">
				  <input type="radio" id="com_subsidio"> Com subsídio
				</div>
				<div class="radio">
					<input type="radio" id="sem_subsidio"> Sem subsídio
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 text-center"> <input type="checkbox">&nbsp;Segunda-feira - 20/05 </div>
			<div class="col-xs-12 text-center"> <input type="checkbox">&nbsp;Terça-feira - 21/05 </div>
			<div class="col-xs-12 text-center"> <input	type="checkbox">&nbsp;Quarta-feira - 22/05 </div>
			<div class="col-xs-12 text-center"> <input type="checkbox">&nbsp;Quinta-feira - 23/05 </div>
			<div class="col-xs-12 text-center"> <input type="checkbox">&nbsp;Sexta-feira - 24/05 </div>
		</div>
		<div class="row">
			<div class="col-sm-2 form-group">
				<label for="valor">Valor dado</label> 
				<input type="text" class="form-control" id="valor">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-2 form-group" style="margin-top: 2px">
				<label for="refeicoes">Refeições</label> 
				<input type="text" class="form-control" id="refeicoes">
			</div>
			<div class="col-sm-2 form-group">
				<label for="recargas"> <input type="checkbox"> Recargas</label> 
				<input type="text" class="form-control" id="recargas">
			</div>
			<div class="col-sm-2 form-group" style="margin-top: 2px">
				<label for="troco">Troco</label> 
				<input type="text" class="form-control" id="troco">
			</div>
			<div class="col-sm-2 form-group" style="margin-top: 27px">
				<a href="#" class="btn btn-primary">Efetuar venda</a>
			</div>
		</div>
	</form>
	<br />
	<br />
</body>
</html>