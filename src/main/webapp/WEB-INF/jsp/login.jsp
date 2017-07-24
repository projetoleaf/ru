<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta name="header" content="Login" />
<title>Login</title>
</head>
<body>
	<div class="row">
		<div class="col-sm-4 col-sm-offset-4">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form method="post">
						<div class="form-group">
							<label for="login">Email/CPF</label> <input type="text"
								class="form-control" id="login" placeholder="Email/CPF" required>
						</div>
						<div class="form-group">
							<label for="senha">Senha</label> <input type="password"
								class="form-control" id="senha" placeholder="Senha" required>
						</div>
						<div class="form-group text-right">
							<a href="#myModal" data-toggle="modal" data-target="#myModal">Esqueceu
								sua senha?</a>
						</div>
						<div class="modal fade" id="myModal" role="dialog">
							<div class="modal-dialog modal-md">
								<div class="modal-content">
									<div class="modal-header">
										<h4 class="modal-title text-center">Redefinir Senha</h4>
									</div>
									<div class="modal-body">
										<p>Digite seu e-mail e data de nascimento para recuperar
											sua senha</p>
										<div class="row">
											<div class="col-sm-8 col-sm-offset-2">
												<div class="form-group">
													<input type="email" class="form-control" id="email"
														placeholder="Digite seu email" name="email">
												</div>
												<div class="form-group">
													<input type="date" class="form-control" id="datanasc"
														name="datanasc">
												</div>
												<div class="text-center">
													<button type="button" class="btn btn-primary"
														data-toggle="modal" data-target="#myModal2">
														<span class="glyphicon glyphicon-send"></span> Enviar
													</button>
												</div>
												<div class="modal fade" id="myModal2" role="dialog">
													<div class="modal-dialog modal-sm">
														<div class="modal-content">
															<div class="modal-header">
																<h4 class="modal-title text-center">E-mail enviado
																	com sucesso!</h4>
															</div>
															<div class="modal-body">
																<p>Verifique sua caixa de entrada ou lixo eletrônico
																	para alterar sua senha.</p>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-primary"
																	data-dismiss="modal">
																	<span class="glyphicon glyphicon-remove"></span> Fechar
																</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-primary"
											data-dismiss="modal">
											<span class="glyphicon glyphicon-remove"></span> Fechar
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="text-center">
							<button type="submit" class="btn btn-primary">Entrar</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>