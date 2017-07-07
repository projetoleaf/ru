<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<li><a href="<c:url value="#"/>"><span class="glyphicon glyphicon-calendar"></span> Reservas</a></li>
<li><a href="<c:url value="#"/>"><span class="glyphicon glyphicon-usd"></span> Vendas</a></li>
<li class="dropdown"><a href="#" class="dropdown-togle" data-toggle="dropdown"><span class="glyphicon glyphicon-wrench"></span> Manutenção <b class="caret"></b></a>
  <ul class="dropdown-menu">
    <li><a href="<c:url value="/manutencao/categorias/pesquisar"/>"><span class="glyphicon glyphicon-edit"></span> Categorias</a></li>
    <li><a href="<c:url value="/manutencao/cursos/pesquisar"/>"><span class="glyphicon glyphicon-edit"></span> Cursos</a></li>
    <li><a href="<c:url value="/manutencao/feriados/pesquisar"/>"><span class="glyphicon glyphicon-edit"></span> Feriados</a></li>
    <li><a href="<c:url value="/manutencao/tipos/pesquisar"/>"><span class="glyphicon glyphicon-edit"></span> Tipos</a></li>
  </ul>
</li>

<li><a href="<c:url value="/desktop/inicio"/>">Sistema</a></li>
<li><a href="<c:url value="/desktop/sobre"/>">Sobre</a></li>
<li><a href="http://www.bauru.unesp.br/#!/restaurante-universitario---ru/" target="_blank">RU</a></li>