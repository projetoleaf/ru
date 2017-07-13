<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Reserva" />
<title>Reserva</title>
</head>
<body>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Reserva</strong>
          </h3>
        </div>
		<h3>Selecione o(s) dia(s) que deseja reservar:</h3>
		<br> <br>
		<div class="text-center">
			<input type="checkbox">&nbsp;Segunda-feira - 20/05&nbsp; <input
				type="checkbox">&nbsp;Terça-feira - 21/05&nbsp; <input
				type="checkbox">&nbsp;Quarta-feira - 22/05&nbsp; <input
				type="checkbox">&nbsp;Quinta-feira - 23/05&nbsp; <input
				type="checkbox">&nbsp;Sexta-feira - 24/05
		</div>
		<div class="text-center">
			<br> <br>
			<button type="submit" class="btn btn-primary" data-toggle="modal"
				data-target="#myModal">
				<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span>
				Reservar
			</button>
		</div>
	</div>
</div>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Reservas</h4>
			</div>
			<div class="modal-body">
				<p>Confirme os dias da sua reserva...</p>
				<p>&#10004...</p>
				<p>&#10004...</p>
				<p>&#10004...</p>
			</div>
			<div class="modal-footer">
				<div class="text-center">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-primary" data-toggle="modal"
						data-target="#myModal">Confirmar reserva</button>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>