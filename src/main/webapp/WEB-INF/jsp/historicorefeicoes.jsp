<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome"/>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<title>Historico de refeições</title>
	</head>
	
	
	<body>
	<div class="container">
		<div class="row">
			<div class="panel panel-primary">
				<div class="panel-heading">Historico de refeições</div>
				<div class="panel-body">  
					<table class="table table-responsive table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Nº.</th>
								<th>Data</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
								<tr>
									<td>1</td>
									<td>16/05/2017</td>
									<td><span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span></td>
								</tr>
								<tr>
									<td>2</td>
									<td>17/05/2017</td>
									<td><span class="glyphicon glyphicon-time" aria-hidden="true"></span></td>
								</tr>
								<tr>
									<td>3</td>
									<td>18/05/2017</td>
									<td><span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span></td>
								</tr>	
						</tbody>
					</table>
					<div class="pull-right">
						<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>&nbsp;Pago<br>
						<span class="glyphicon glyphicon-time" aria-hidden="true"></span>&nbsp;Aguardando Pagamento<br>
						<span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>&nbsp;Não Pago<br>
						<span class="glyphicon glyphicon-download" aria-hidden="true"></span>&nbsp;Transferido<br>
						<span class="glyphicon glyphicon-upload" aria-hidden="true"></span>&nbsp;Transferente
					</div>
				</div>
			</div>
		</div>
	</div>
	
	</body>
	
</html>