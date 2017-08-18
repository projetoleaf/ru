<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://github.com/dandelion" prefix="dandelion"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<dandelion:bundle includes="font-awesome" />

<li><a href="<c:url value="/inicio"/>"><i class="fa fa-tachometer" aria-hidden="true"></i> Sistema</a></li>
<li><a href="<c:url value="/sobre"/>"><span class="fa fa-leaf"></span> Sobre</a></li>
<sec:authorize access="isAuthenticated()">
    <li class="dropdown">
        <a href="#" class="dropdown-togle" data-toggle="dropdown">
            <i class="fa fa-user" aria-hidden="true"></i> <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <li><a title="<sec:authentication property="details.nome" />">Usuário: <strong><sec:authentication property="principal" /></strong></a></li>
            <li role="separator" class="divider"></li>
            <li>
                <a href="<c:url value="/logout"/>" title="Desconectar">
                    <i class="fa fa-times" aria-hidden="true"></i> Desconectar
                </a>
            </li>
        </ul>
    </li>
</sec:authorize>