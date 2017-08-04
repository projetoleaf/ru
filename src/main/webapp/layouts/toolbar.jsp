<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<li><a href="<c:url value="/inicio"/>"><span class="glyphicon glyphicon-dashboard"></span> Sistema</a></li>
<li><a href="<c:url value="/sobre"/>"><span class="fa fa-leaf"></span> Sobre</a></li>
<li><a href="http://www.bauru.unesp.br/#!/restaurante-universitario---ru/" target="_blank"><span class="fa fa-external-link"></span> RU</a></li>
<sec:authorize access="isAuthenticated()">
    <li class="dropdown">
        <a href="#" class="dropdown-togle" data-toggle="dropdown">
            <span class="glyphicon glyphicon-user" aria-hidden="true"><b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <li><a title="<sec:authentication property="details.nome" />">Usuário: <strong><sec:authentication property="principal" /></strong></a></li>
            <li role="separator" class="divider"></li>
            <li>
                <a href="<c:url value="/logout"/>" title="Desconectar">
                    <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span>Desconectar
                </a>
            </li>
        </ul>
    </li>
</sec:authorize>