<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>

<c:set var="linkController"><c:url value="/cardapios" /></c:set>

<dandelion:bundle includes="floating.button,font-awesome" />

<html>
<head>
<meta name="header" content="Cardápios" />
<title>Cardápios</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>

	<%@include file="/layouts/modal-mensagens.jsp"%>
	<%@include file="/layouts/modal-exclusao.jsp"%>

	<a href="${linkController}/incluir" class="float-button"><i	class="fa fa-plus"></i></a>
	
</body>
</html>