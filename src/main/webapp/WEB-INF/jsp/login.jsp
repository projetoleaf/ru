<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>

<dandelion:bundle includes="font-awesome"/>

<html>
	<head>
	<title>Login</title>
	</head>
	
	
	<body>
	<div class="container">
	<div class="row">
			<div class="panel panel-primary">
				<div class="panel-heading">Login</div>
					<div class="panel-body">
				 	<div class="form-horizontal col-lg-12 col-lg-offset-3">
						<div class="form-group">
							<label class="control-label col-lg-2" for="Login"><i>*</i> Login:</label>
							<div class="col-lg-2">
							<input type="text" class="form-control"  id="login" placeholder="Email / RA" required>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-lg-2" for="Login"><i>*</i> Senha:</label>
							<div class="col-sm-2">
							<input type="password" class="form-control"  id="senha" placeholder="Digite sua senha" required>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12 col-lg-offset-2">
								<button type="submit" class="btn btn-primary" >Enviar</button>
								<button type="reset" class="btn btn-primary" >Limpar</button>
								<span id="helpBlock1" class="help-block">* campos obrigatórios</span>
								
							</div>
									
						</div>
						
					
						
					</div>
				</div>
			</div>
		</div>
	</div>
	</body>
	
</html>