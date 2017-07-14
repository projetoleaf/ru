<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Usuários" />
<title>Usuários</title>
</head>
<body>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Usuários</strong>
          </h3>
        </div>
		<div>
			<table>
				<tr>
					<td><h4>Dados do usuario:</h4></td>
				</tr>
				<tr>
					<td>
						<p>Nome:</p>
					</td>
					<td>
						<p>Matheus Guermandi Ribeiro</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>Tipo da conta:</p>
					</td>
					<td>
						<p>Discente</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>Saldo:</p>
					</td>
					<td>
						<p>4 Refeições</p>
					</td>
				</tr>
			</table>
			<h4>
				<p>Enviar penalidade ao usuaruio</p>
			</h4>
			<br>
			<form class="form-horizontal" action="/action_page.php">
				<div class="form-group">
					<label class="control-label col-sm-1" for="email">Email:</label>
					<div class="col-sm-5">
						<input type="email" class="form-control" id="email"
							placeholder="Email" name="email">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-1" for="pwd">Mensagem:</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="msg"
							placeholder="Mensagem" name="msg">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-1 col-sm-5">
						<button type="submit" class="btn btn-primary">Entrar</button>
						<button type="reset" class="btn btn-primary">Limpar</button>
					</div>
				</div>
			</form>
			<hr>
			<table>
				<tr>
					<div>
						<button type="button" class="btn btn-primary">Excluir
							Conta</button>
						<button type="button" class="btn btn-primary">Adicionar
							conta como ADM</button>
						<button type="button" class="btn btn-primary">Alterar
							saldo</button>
					</div>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>