<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion"%>

<dandelion:bundle includes="font-awesome" />

<html lang="pt-br">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Garante a renderização adequada para mobile e desabilita o zoom, ou seja, o usuário só é capaz de rolar a tela -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description"
	content="Unesp, Faculdade de Ciências, Diretoria Técnica de Informática" />
<meta name="keywords"
	content="Unesp, Faculdade de Ciências, Diretoria Técnica de Informática" />
<meta name="author"
	content="Unesp, Faculdade de Ciências, Diretoria Técnica de Informática" />
<meta http-equiv="Content-Language" content="pt-br" />
<meta http-equiv="imagetoolbar" content="no" />
<meta name="robots" content="index" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta http-equiv="cache-control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="pragma" content="no-cache" />
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/assets/imagens/favicon.ico" />
<link rel="icon"
	href="${pageContext.request.contextPath}/assets/imagens/favicon.ico" />

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<meta name="header" content="Página não encontrada" />
<title>:: UNESP:FC :: Página não encontrada</title>
<style>
.error {
	margin: 0 auto;
	text-align: center;
	position: relative;
	top: 40%;
	transform: translateY(-50%);
}

.error .error-code {
	color: #4686CC;
	font-size: 96px;
	font-weight: bold;
}

.error .error-desc {
	font-size: 12px;
	color: #647788;
}

.error .m-b-10 {
	margin-bottom: 10px !important;
}

.error .m-t-20 {
	margin-top: 20px !important;
}
</style>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"></link>
<link rel="stylesheet" type="text/css" media="screen, projection"
	href="${pageContext.request.contextPath}/layouts/unespfc-bootstrap3/css/navbar-unespfc.css" />
</head>
<body>
	<nav class="navbar navbar-unespfc navbar-static-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${pageContext.request.contextPath}">
					<img alt="Brand"
					src="${pageContext.request.contextPath}/layouts/unespfc-bootstrap3/imagens/logotipo.png"
					style="margin-top: -5px;">
				</a>
			</div>
		</div>
	</nav>
	<div class="error">
		<div class="error-code m-b-10 m-t-20">
			404 <i class="fa fa-warning"></i>
		</div>
		<h3 class="font-bold">Não conseguimos encontrar esta página...</h3>

		<div class="error-desc">
			A página que você procura não foi encontrada ou ela não existe. <br>
			Tente recarregar a página ou clique no botão abaixo para voltar na
			tela inicial.
		</div>
		<br>
		<div>
			<a class="btn btn-primary" href="${pageContext.request.contextPath}/">
				<i class="fa fa-arrow-left"></i> Voltar para tela inicial
			</a>
		</div>
	</div>
</body>
</html>