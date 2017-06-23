<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome"/>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
	<meta charset="utf8">
	<title>Cadastro de feriados</title>
	</head>
	
	
	<body>
	<div class="container">
		<div class="panel panel-primary">
		<div class="panel-heading">Cadastro de Feriados</div>
		<div class="panel-body">  
			<form class="form-inline">
						<label for="Data">* Data</label>
						<input type="date" class="form-control"><span id="helpBlock1" class="help-block">Data do feriado</span>
						<label for="Descricao">* Descrição</label>
						<input type="text" class="form-control" placeholder="Descrição do feriado"><br><br>
						<input type="submit" class="btn btn-primary" value="Salvar">
						<input type="submit" class="btn btn-primary" value="Alterar">						
						<input type="submit" class="btn btn-danger" value="Excluir">
			</form>
			<br><br>
			<table class="table table-responsive table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>Data do feriado</th>
						<th>Descrição feriado</th>
					</tr>
				</thead>
				<tbody>
						<tr>
							<td>16/05/2017</td>
							<td>Corpus Christi</td>
						</tr>
				</tbody>
			</table>
		</div>
		</div>
	</div>
	</body>
	
</html>