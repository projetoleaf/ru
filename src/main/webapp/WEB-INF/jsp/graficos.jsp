<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="font-awesome" />

<c:set var="linkController"> <c:url value="/graficos" /> </c:set>

<html>
<head>
<meta name="header" content="Gráficos" />
<title>Gráficos</title>
</head>
<body>	
	<div class="col-xs-12 col-md-8 col-md-offset-2">

		<h4>Selecione alguns filtros e monte o seu gráfico</h4>
		
		<br />
		
		<div class="page-header" style="margin-top: 10px;">
        	<h4><i class="fa fa-pie-chart" aria-hidden="true"></i> Setor</h4>
        </div>
		<div class="row">
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Categorias </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Período </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Faculdade </div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Refeição </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Idade </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Sexo </div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Venda por categoria </div>
		</div>
		
		<br />
		
		<div class="page-header" style="margin-top: 10px;">
        	<h4><i class="fa fa-bar-chart" aria-hidden="true"></i> Coluna</h4>
        </div>
		<div class="row">
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Cursos </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Venda por mês </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Venda por categoria </div>
		</div>
		
		<br />
		
		<div class="page-header" style="margin-top: 10px;">
        	<h4><i class="fa fa-line-chart" aria-hidden="true"></i> Linha</h4>
        </div>
		<div class="row">
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Cursos </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Venda por mês </div>
			<div class="col-xs-12 col-sm-4"> <input type="checkbox"> Venda por categoria </div>
		</div>
		
		<br />
		<br />
		
		<div class="text-center">
			<a href="#" class="btn btn-primary">Gerar gráfico</a>
		</div>
		
		<br />
		<br />
		
	</div>
</body>
</html>