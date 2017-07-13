<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Consulta" />
<title>Consulta</title>
</head>
<body>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Consulta</strong>
          </h3>
        </div>
		<form method="post">
			<div class="row">
				<div class="col-xs-10 col-sm-6">
					<div class="form-group">
						<label for="nome">Nome</label> <input type="text"
							class="form-control" id="nome" placeholder="Nome" required>
					</div>
				</div>
				<div class="col-xs-2 col-sm-2 pd">
					<button type="submit" class="btn btn-primary mg-tp">Pesquisar</button>
				</div>
			</div>
		</form>
		<table class="table table-condensed table-bordered">
			<thead>
				<tr>
					<th>#</th>
					<th>Nome</th>
					<th>22/05</th>
					<th>23/05</th>
					<th>24/05</th>
					<th>25/05</th>
					<th>26/05</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>2517</td>
					<td>Thiago Teixeira de Castro Piovan</td>
					<td><span class="glyphicon glyphicon-time"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-time"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-time"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-time"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-time"
						aria-hidden="true"></span></td>
				</tr>
				<tr>
					<td>1527</td>
					<td>Giovana Carolini</td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
				</tr>
				<tr>
					<td>7521</td>
					<td>Victor Ribeiro</td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
				</tr>
				<tr>
					<td>5217</td>
					<td>Matheus Guermandi</td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-download"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-download"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-remove-circle"
						aria-hidden="true"></span></td>
				</tr>
				<tr>
					<td>1257</td>
					<td>Gabriel Orbeli</td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-upload"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-upload"
						aria-hidden="true"></span></td>
					<td><span class="glyphicon glyphicon-ok-circle"
						aria-hidden="true"></span></td>
				</tr>
			</tbody>
		</table>
		<div class="text-center">
			<ul class="pagination">
				<li class="active"><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">></a></li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>