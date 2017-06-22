<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<title>Cardápio</title>
<style>
/* Previous button  */
.media-carousel .carousel-control.left {
	left: -12px;
	color: #428bca;
	background-image: none;
}
/* Next button  */
.media-carousel .carousel-control.right {
	right: -12px !important;
	background-image: none;
	color: #428bca;
}

.thumbnail:hover {
	border-color: #428bca;
}

.carousel-control .glyphicon-chevron-left, .carousel-control .glyphicon-chevron-right,
	.carousel-control .icon-next, .carousel-control {
	font-size: 20px !important;
}
</style>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.2.1.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jspdf.min.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/html2canvas.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/js/script.js" />"> </script>

</head>
<body>
	<p
		style="color: #023f88; font-family: verdana; font-weight: bold; font-size: 17px;">Cardápio</p>
	<div class="text-center">
		<img alt="Bandeja do RU" style="width: 450px"
			src="https://www.bauru.unesp.br/Home/restauranteuniversitario-ru/cardapio1.jpg">
		<br> <br>
		<p class="text-center" style="font-family: verdana; font-size: 12px;">A
			bandeja do RU é composta pelos seguintes elementos: um prato base, um
			prato principal, uma guarnição, uma salada, uma sobremesa e um suco.</p>
	</div>
	<div class="row">
		<div class="col-md-4 col-md-offset-4 thumbnail"
			style="height: auto; width: 270px; float: none; margin: 0 auto; display: flex; justify-content: center; flex-direction: column;">
			<div class="carousel slide media-carousel" id="media">
				<div class="carousel-inner">
					<div class="item active">
						<div class="row">
							<div class="col-md-4" style="width: 100%">
								<div class="text-center" href="#">
									<p
										style="width: 80%; margin: auto; font-size: 18px; font-family: verdana;">29/05/17
										- 02/06/17</p>
								</div>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="row">
							<div class="col-md-4" style="width: 100%">
								<div class="text-center" href="#">
									<p
										style="width: 80%; margin: auto; font-size: 18px; font-family: verdana;">29/05/17
										- 02/06/17</p>
								</div>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="row">
							<div class="col-md-4" style="width: 100%">
								<div class="text-center" href="#">
									<p
										style="width: 80%; margin: auto; font-size: 18px; font-family: verdana;">29/05/17
										- 02/06/17</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<a class="left carousel-control" href="#media" role="button"
					data-slide="prev"> <span class="glyphicon glyphicon-menu-left"
					aria-hidden="true"></span>
				</a> <a class="right carousel-control" href="#media" role="button"
					data-slide="next"> <span class="glyphicon glyphicon-menu-right"
					aria-hidden="true"></span>
				</a>
			</div>
		</div>
	</div>
	<br>
	<div id="conteudo">
		<table class="table table-hover table-bordered table-condensed"
			style="font-family: verdana; font-size: 12px;">
			<thead>
				<tr>
					<th style="width: 150px">Constituintes do cardápio</th>
					<th style="width: 180px">SEGUNDA-FEIRA</th>
					<th style="width: 180px">TERÇA-FEIRA</th>
					<th style="width: 180px">QUARTA-FEIRA</th>
					<th style="width: 180px">QUINTA-FEIRA</th>
					<th style="width: 180px">SEXTA-FEIRA</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th rowspan="2">Prato base</th>
					<td>Arroz</td>
					<td>Arroz</td>
					<td>Arroz</td>
					<td>Arroz</td>
					<td>Arroz</td>
				</tr>
				<tr>
					<td>Feijão</td>
					<td>Feijão Preto</td>
					<td>Feijão</td>
					<td>Feijão Preto</td>
					<td>Feijão</td>
				</tr>
				<tr>
					<th>Prato Principal Tradicional</th>
					<td>Carne de Panela c/ Batata e Cenoura</td>
					<td>Rocambole de Carne</td>
					<td>Sobrecoxa Assada</td>
					<td>Carne Moída c/ Batata e Cenoura</td>
					<td>Filé de Frango Empanado</td>
				</tr>
				<tr>
					<th>Prato Principal Vegetariano</th>
					<td>Proteína Texturizada de Soja c/ Batata e Cenoura</td>
					<td>Almôndegas veganas ao sugo</td>
					<td>Lentilha c/ CouveFlor, Abobrinha e Tomate</td>
					<td>Batata Recheada c/ PTS, mussarela e Tomate</td>
					<td>Hambúrguer Vegano</td>
				</tr>
				<tr>
					<th>Guarnição</th>
					<td>Abobrinha Refogada c/ Tomate</td>
					<td>Batata alho e óleo</td>
					<td>Mandioquinha, Cenoura e Brócolis</td>
					<td>Polenta ao Sugo</td>
					<td>Creme de Espinafre</td>
				</tr>
				<tr>
					<th rowspan="2">Saladas</th>
					<td>Alface</td>
					<td>Alface c/ Escarola</td>
					<td>Alface c/ Rúcula</td>
					<td>Alface c/ Almeirão</td>
					<td>Mix de Folhas</td>
				</tr>
				<tr>
					<td>Beterraba Ralada</td>
					<td>Pepino c/ Cebola</td>
					<td>Acelga c/ Soja e Abacaxi</td>
					<td>Repolho Colorido</td>
					<td>Cenoura Ralada c/ Ervilha Seca</td>
				</tr>
				<tr>
					<th>Sobremesa</th>
					<td>Tangerina</td>
					<td>Maçã</td>
					<td>Melão c/ Laranja</td>
					<td>Banana</td>
					<td>Doce de Goiaba</td>
				</tr>
				<tr>
					<th>Suco</th>
					<td>Limão</td>
					<td>Laranja</td>
					<td>Maracujá</td>
					<td>Abacaxi</td>
					<td>Uva</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="text-center">
		<a href="javascript:demoFromHTML()" class="btn btn-primary">Gerar PDF</a>
	</div>
	<br>
</body>
</html>