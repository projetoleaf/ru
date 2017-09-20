<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<dandelion:bundle includes="font-awesome" />

<sec:authorize access="hasRole('ROLE_FC.UNESP.RU_ADMIN') || hasRole('ROLE_FC.UNESP.RU_STF')">
    <li><a href="<c:url value="/reservas"/>"><span class="fa fa-calendar"></span> Reservas</a></li>
    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-bank"></span> Vendas</a>
      <ul class="dropdown-menu">
        <li><a href="<c:url value="/semanaAtual"/>"><span class="fa fa-calendar"></span> Semana Atual</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/remanescentes"/>"><span class="fa fa-calendar-plus-o"></span> Remanescentes</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/creditos"/>"><span class="fa fa-usd"></span> Créditos</a></li>
      </ul>
    </li>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_FC.UNESP.RU_ADMIN') || hasRole('ROLE_FC.UNESP.RU_STN')">
    <li><a href="<c:url value="/clientes"/>"><span class="fa fa-users"></span> Clientes</a></li>
    <li class="dropdown"><a href="#" class="dropdown-togle" data-toggle="dropdown"><span class="fa fa-wrench"></span> Manuten��o</a>
      <ul class="dropdown-menu">
        <li><a href="<c:url value="/cardapios"/>"><span class="fa fa-cog"></span> Cardápios</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/categorias"/>"><span class="fa fa-cog"></span> Categorias</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/cursos"/>"><span class="fa fa-cog"></span> Cursos</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/feriados"/>"><span class="fa fa-cog"></span> Feriados</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/periodosRefeicoes"/>"><span class="fa fa-cog"></span> Períodos de Refeições</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/quantidadesRefeicoes"/>"><span class="fa fa-cog"></span> Quantidades de Refeições</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/status"/>"><span class="fa fa-cog"></span> Status</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/tiposRefeicoes"/>"><span class="fa fa-cog"></span> Tipos de Refeições</a></li>
        <li class="divider"></li>
        <li><a href="<c:url value="/tiposValores"/>"><span class="fa fa-cog"></span> Tipos de Valores</a></li>
      </ul>
    </li>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_FC.UNESP.RU_CLIENTE')">
	<li><a href="<c:url value="/conta"/>"><span class="fa fa-id-card"></span> Conta</a></li>
	<li><a href="<c:url value="/historico"/>"><span class="fa fa-calendar"></span> Histórico</a></li>
	<li><a href="<c:url value="/reserva"/>"><span class="fa fa-cutlery"></span> Reservar</a></li>
	<li><a href="<c:url value="/transferencias"/>"><span class="fa fa-exchange"></span> Transferências</a></li>
	<li><a href="<c:url value="/reservas"/>"><span class="fa fa-money"></span> Comprar</a></li>
	<li><a href="<c:url value="/reservas"/>"><span class="fa fa-feed"></span> Remanescentes</a></li>
	<li><a href="<c:url value="/extrato"/>"><span class="fa fa-bank"></span> Extrato</a></li>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-info-circle"></span> Relatórios</a>
	  <ul class="dropdown-menu">
	    <li><a href="<c:url value="/planilhas"/>"><span class="fa fa-table"></span> Planilhas</a></li>
	    <li class="divider"></li>
	    <li><a href="<c:url value="/graficos"/>"><span class="fa fa-pie-chart"></span> Gráficos</a></li>
	  </ul>
	</li>
</sec:authorize>