<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<dandelion:bundle includes="font-awesome" />

<c:set var="linkController">
	<c:url value="/graficos" />
</c:set>

<html>
<head>
<meta name="header" content="Gráficos Gerados" />
<title>Gráficos Gerados</title>
</head>
<body>
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.debug.js"></script>
	<script type="text/javascript">
  $( document ).ready(function() {	
	  google.charts.load("current", {packages:["corechart"], 'language': 'pt_BR'})
	  .then(function () {
	      var data = google.visualization.arrayToDataTable([ 	
	        ["${textos}", "${valores}"]
	        <c:forEach var="total" items="${dados}">
	            ,[
	                '${total.key}',
	                ${total.value}
	             ]
	            <c:set var="textos"> <c:out value="${textos}"/> ${total.key}</c:set>
	            <c:set var="valores"> <c:out value="${valores}"/> ${total.value}</c:set>
	        </c:forEach>
	      ]);
	      
	      var chart = new google.visualization.PieChart(document.getElementById("grafico"));
	      chart.draw(data)
	      
	      $("#pdf").click(function() {
	  		var doc = new jsPDF();
	  	    doc.addImage(chart.getImageURI(), 0, 0);
	  	    doc.save('grafico.pdf');
	  	    
	  	    var string = doc.output('datauristring');
		  	var iframe = "<iframe width='100%' height='100%' src='" + string + "'></iframe>";	
		  	var x = window.open();
		  	x.document.open();
		  	x.document.write(iframe);
		  	x.document.close();
	      });
	   });		  
  });
  </script>
	<div class="col-xs-12 col-md-8 col-md-offset-2">

		<h4>${titulo}</h4>

		<br />

		<div id="grafico" style="width: 900px; height: 500px;"></div>

		<br /> <br />

		<div class="text-center">
			<button type="button" id="pdf" class="btn btn-primary">
				<i class="fa fa-file-pdf-o" aria-hidden="true"></i> Gerar PDF
			</button>
		</div>

		<br /> <br />

	</div>
</body>
</html>