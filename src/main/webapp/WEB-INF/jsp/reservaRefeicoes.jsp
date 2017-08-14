<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="actionSalvar"> <c:url value="/reservaRefeicoes/salvar"/> </c:set>

<html>
<head>
<meta name="header" content="Reserva" />
<title>Reserva</title>
</head>
<body>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
      <div class="panel-body">
        <div class="page-header" style="margin-top: 10px;">
          <h3>
            <strong>Reserva</strong>
          </h3>
        </div>
        
		<h4>Selecione o(s) dia(s) que deseja reservar:</h4>
		
		<br/> 
		
		<form:form method="POST" action="${actionSalvar}" modelAttribute="datas">
			<div class="row">				
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <form:checkboxes items="${todasAsDatas}" path="data" itemLabel="data" itemValue="id" delimiter="<br/>"/> </div>
			</div>
			
			<div class="text-center">
				<br/> <br/>
				<button type="submit" class="btn btn-primary">
					<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span>
					Reservar
				</button>
			</div>
		</form:form>
	</div>
</div>
</body>
</html>