<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Redefinir Senha" />
<title>Redefinir Senha</title>
<link href="<c:url value="/resources/css/ru.css"/>" rel="stylesheet" />
</head>
<body>
	<div class="row">
		<div class="col-sm-4 col-sm-offset-4">
			<div class="panel panel-primary">
				<div class="panel-heading text-center subtitulo">Redefinir
					Senha</div>
				<div class="panel-body">
					<form action="/action_page.php" id="passwordTest" name="formulario">
						<div class="form-group">
							<label for="novaSenha">Nova senha</label> <input type="password"
								class="form-control" id="password1"
								placeholder="Digite sua nova senha" name="novaSenha" required>
						</div>
						<div class="form-group">
							<label for="confSenha">Confirmar senha</label> <input
								type="password" class="form-control" id="password2"
								name="confSenha" placeholder="Confirme sua senha" required>
						</div>
						<div class="text-center">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#myModal" type="submit">
								<span class="glyphicon glyphicon-ok"></span> Redefinir
							</button>
						</div>
						<div class="modal fade" id="myModal" role="dialog">
							<div class="modal-dialog modal-sm">
								<div class="modal-content">
									<div class="modal-header">
										<h4 class="modal-title text-center">Alteração de Senha</h4>
									</div>
									<div class="modal-body">
										<div class="text-center">Senha alterada com sucesso!</div>
									</div>
									<div class="modal-footer">
										<div class="text-center">
											<button type="button" class="btn btn-primary"
												data-dismiss="modal">
												<span class="glyphicon glyphicon-remove"></span> Fechar
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-3.2.1.min.js" />"></script>
	<script type="text/javascript"
		src="/resources/js/jquery.complexify.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/redefinir_senha.js" />"></script>
</body>
</html>