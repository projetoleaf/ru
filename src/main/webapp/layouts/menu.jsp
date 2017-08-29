<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<dandelion:bundle includes="font-awesome" />

<sec:authorize access="isAuthenticated()">
    <li><a href="<c:url value="/reservas"/>"><span class="fa fa-calendar"></span> Reservas</a></li>
    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-usd"></span> Vendas</a>
      <ul class="dropdown-menu">
        <li><a href="<c:url value="/semanaAtual"/>"><span class="fa fa-calendar"></span> Semana Atual</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/remanescentes"/>"><span class="fa fa-calendar-o"></span> Remanescentes</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/creditos"/>"><span class="fa fa-usd"></span> Créditos</a></li>
      </ul>
    </li>
    <li><a href="<c:url value="/clientes"/>"><span class="fa fa-users"></span> Clientes</a></li>
    <li class="dropdown"><a href="#" class="dropdown-togle" data-toggle="dropdown"><span class="fa fa-wrench"></span> Manutenção</a>
      <ul class="dropdown-menu">
        <li><a href="<c:url value="/cardapios"/>"><span class="fa fa-pencil-square-o"></span> Cardápios</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/categorias"/>"><span class="fa fa-pencil-square-o"></span> Categorias</a></li>
        <li class="divider"></li>
        <!--  <li><a href="<c:url value="/cursos"/>"><span class="fa fa-pencil-square-o"></span> Cursos</a></li>
        <li class="divider"></li>-->
        <li><a href="<c:url value="/feriados"/>"><span class="fa fa-pencil-square-o"></span> Feriados</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/status"/>"><span class="fa fa-pencil-square-o"></span> Status</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/tiposRefeicoes"/>"><span class="fa fa-pencil-square-o"></span> Tipos Refeições</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/tiposValores"/>"><span class="fa fa-pencil-square-o"></span> Tipos Valores</a></li>
      </ul>
    </li>
    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-list-alt"></span> Relatórios</a>
      <ul class="dropdown-menu">
        <li><a href="<c:url value="/planilhas"/>"><span class="fa fa-table"></span> Planilhas</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/graficos"/>"><span class="fa fa-pie-chart"></span> Gráficos</a></li>
      </ul>
    </li>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_FC.UNESP.RU_CLIENTE')">
    <p>Sou cliente</p>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_FC.UNESP.RU_STF')">
    <p>Sou de finanças</p>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_FC.UNESP.RU_STN')">
    <p>Sou de nutrição</p>
</sec:authorize>