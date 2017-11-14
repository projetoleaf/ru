<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="font-awesome" />

<c:set var="linkController">
	<c:url value="/graficogerado" />
</c:set>

<html>
<head>
<meta name="header" content="Gráficos" />
<title>Gráficos</title>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<div class="col-xs-12 col-md-8 col-md-offset-2">

		<h4>Selecione alguns filtros e monte o seu gráfico</h4>

		<br />

		<form:form action="${linkController}" modelAttribute="tiposGraficos">

			<div class="row">
				<div class="col-xs-12 col-sm-4">
					<div class="page-header" style="margin-top: 10px;">
						<h4>
							<i class="fa fa-pie-chart" aria-hidden="true"></i> Setor
						</h4>
					</div>
					<form:radiobuttons items="${setores}" path="setor" delimiter="<br>" />
				</div>

				<%-- <div class="col-xs-12 col-sm-4">
					<div class="page-header" style="margin-top: 10px;">
						<h4>
							<i class="fa fa-bar-chart" aria-hidden="true"></i> Coluna
						</h4>
					</div>
					<form:checkboxes items="${colunas}" path="coluna" delimiter="<br>" />
				</div>
				<div class="col-xs-12 col-sm-4">
					<div class="page-header" style="margin-top: 10px;">
						<h4>
							<i class="fa fa-line-chart" aria-hidden="true"></i> Linha
						</h4>
					</div>
					<form:checkboxes items="${linhas}" path="linha" delimiter="<br>" />
				</div> --%>
			</div>

			<br />
			<br />

			<div class="text-center">
				<button type="submit" class="btn btn-primary">Gerar gráfico</button>
			</div>

			<br />
			<br />

		</form:form>

	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>