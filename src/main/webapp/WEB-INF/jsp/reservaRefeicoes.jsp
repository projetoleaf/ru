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
	<script>
	
	function reservar() {
	    var todasAsDatas = document.forms[0];
	    var txt = "";
	    var i;	    
	    var x;
	    var cardapioObject = ${objectJSON};
	    
	    $(".modal-body").append("<p>Confirme os dias da sua reserva...</p>");
		
	    for (i = 0; i < todasAsDatas.length; i++) {
	    	
	       if (todasAsDatas[i].checked) {
	    	   
	    	   for (x = 0; x < cardapioObject.length; x++) {
	    		
	    		   if(String(cardapioObject[x].id) === todasAsDatas[i].value) {
	    			   txt = txt + "<p>&#10004 " + cardapioObject[x].data  + "</p>";
	    		   
	    		   }
	    	   }
	       }
	    }
	    
	    $(".modal-body").append(txt);		
		
	}
	
	function cancelar() {
		$(".modal-body").empty();
	}
	</script>
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
				<div class="col-xs-12 col-offset-xs-0 col-sm-4 col-sm-offset-4"> <form:checkboxes items="${todasAsDatas}" path="data" itemLabel="data" itemValue="id" delimiter="<br/>" /> </div>
			</div>
			
			<div class="text-center">
				<br/> <br/>
				<button type="button" onclick="reservar()" class="btn btn-primary"  data-toggle="modal" data-target="#myModal">
				 	<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span> Reservar
				</button>
			</div>
			
			<!-- Modal -->
			<div id="myModal" class="modal fade" role="dialog">
				<div class="modal-dialog modal-md">
			
					<!-- Modal content-->
			 		<div class="modal-content">
			 			<div class="modal-header">
			 				<button type="button" class="close" data-dismiss="modal">&times;</button>
			 				<h3 class="modal-title">Reserva</h3>
			 			</div>
			 			<div class="modal-body">
			 				
			 			</div>
			 			<div class="modal-footer">
			 				<div class="text-center">
			 					<button type="button" onclick="cancelar()" class="btn btn-default" data-dismiss="modal">Cancelar</button>
			 					<button type="submit" class="btn btn-primary">Confirmar reserva</button>
			 				</div>
			 			</div>
			 		</div>
			 	</div>
			</div>
		</form:form>
	  </div>
	</div>
</body>
</html>