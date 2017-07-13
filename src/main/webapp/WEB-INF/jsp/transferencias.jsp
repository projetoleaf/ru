<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Transferências" />
<title>Transferências</title>
</head>
<body>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Transferências</strong>
          </h3>
        </div>
		<h3>Selecione o(s) dia(s) que deseja transferir:</h3>
		<p>*Somente serão mostrados os dias com reserva e pagamento
			efetuados.</p>
		<br> <br>
		<form action="#" class="form-horizontal">
			<div class="text-center">
				<input type="checkbox" id="segunda" value="segunda">&nbsp;Segunda-feira
				- 22/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Terça-feira
				- 23/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Quarta-feira
				- 24/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Quinta-feira
				- 25/05 &emsp;<input type="checkbox" id="segunda" value="segunda">&nbsp;Sexta-feira
				- 26/05
			</div>
			<br> <br>
			<h4>Escreva os dados da pessoa a quem deseja tranferir:</h4>
			<br> <br>
			<div class="form-group">
				<div class="control-label col-sm-5" for="cpf">CPF:</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" id="cpf">
				</div>
			</div>
			<div class="form-group">
				<div class="control-label col-sm-5" for="cpf">Data de
					Nascimento:</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" id="data_nascimento">
				</div>
			</div>
			<br>
			<button type="submit" class="btn btn-primary center-block"><span class="glyphicon glyphicon-send" aria-hidden="true"></span> Transferir</button>
		</form>
	</div>
</div>
</body>
</html>