<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="jquery.validation,jquery.inputmask" />

<html>
<head>
<meta name="header" content="Transferências" />
<title>Transferências</title>
</head>
<body>
	<script type="text/javascript">
	  $(document).ready(function() {
	      var formValidator = $("#transferencia").validate({
	          rules : {
	        	  cpf : { required : true }
	          }
	      });
	      $("#cpf").focus();
	      $("#cpf").inputmask("999.999.999-99");
	  });
	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Transferências</strong>
          </h3>
        </div>
		<h4>Selecione o(s) dia(s) que deseja transferir:</h4>
		<p>*Somente serão mostrados os dias com reserva e pagamento
			efetuados.</p>
		<br> 
		<form action="#" class="form-horizontal" id="transferencia">
			<div class="row">
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <input type="checkbox">&nbsp;16/10/17 </div>
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <input type="checkbox">&nbsp;17/10/17 </div>
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <input	type="checkbox">&nbsp;18/10/17 </div>
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <input type="checkbox">&nbsp;19/10/17 </div>
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <input type="checkbox">&nbsp;20/10/17 </div>
			</div>
			<br>
			<h4>Escreva os dados da pessoa a quem deseja tranferir:</h4>
			<br>
			<div class="form-group">
				<div class="col-sm-5 control-label"><label for="cpf">CPF</label></div>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="cpf">
				</div>
			</div>
			<br>
			<button type="submit" class="btn btn-primary center-block"><span class="glyphicon glyphicon-send" aria-hidden="true"></span> Transferir</button>
		</form>
	</div>
</div>
</body>
</html>