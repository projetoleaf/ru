<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li><a href="<c:url value="#"/>"><span class="glyphicon glyphicon-calendar"></span> Reservas</a></li>
<li><a href="<c:url value="#"/>"><span class="glyphicon glyphicon-usd"></span> Vendas</a></li>
<li class="dropdown"><a href="#" class="dropdown-togle" data-toggle="dropdown"><span class="glyphicon glyphicon-wrench"></span> Manutenção</a>
  <ul class="dropdown-menu">
  	<li><a href="<c:url value="/cardapios"/>"><span class="glyphicon glyphicon-edit"></span> Cardápios</a></li>
  	<li class="divider"></li>
    <li><a href="<c:url value="/categorias"/>"><span class="glyphicon glyphicon-edit"></span> Categorias</a></li>
    <li class="divider"></li>
    <li><a href="<c:url value="/cursos"/>"><span class="glyphicon glyphicon-edit"></span> Cursos</a></li>
    <li class="divider"></li>
    <li><a href="<c:url value="/feriados"/>"><span class="glyphicon glyphicon-edit"></span> Feriados</a></li>
    <li class="divider"></li>
    <li><a href="<c:url value="/status"/>"><span class="glyphicon glyphicon-edit"></span> Status</a></li>
    <li class="divider"></li>
    <li><a href="<c:url value="/tipos"/>"><span class="glyphicon glyphicon-edit"></span> Tipos</a></li>
  </ul>
</li>
<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-stats"></span> Relatórios</a>
  <ul class="dropdown-menu">
	<li><a href="#"><span class="glyphicon glyphicon-list-alt"></span> Planilhas</a></li>
	<li class="divider"></li>
	<li><a href="#"><span class="glyphicon glyphicon-picture"></span> Gráficos</a></li>
  </ul>
</li>