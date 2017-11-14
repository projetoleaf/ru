<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://github.com/dandelion" prefix="dandelion" %>

<dandelion:bundle includes="jquery.validation,sweetalert2" />

<html>
<head>
	<meta name="header" content="Primeiro Acesso"/>
	<title>Primeiro Acesso</title>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#categoria").change(function(){
				var pattern = new RegExp("Discente");
				if(pattern.test($(this).val()))
					$("#cursos").removeClass("hidden");
				else
					$("#cursos").addClass("hidden");
			});
			
			$("#primeiroAcesso").submit(function(e){
				e.preventDefault();
				var categoria = $("#categoria").val();
				var curso = $("#cursos").val();
				var raMatricula = $("#raMatricula").val();
				
				$.ajax({
					type: 'post',
				    url: "${pageContext.request.contextPath}/primeiroacesso/salvar",
				  	data: {categoria: categoria, curso: curso, raMatricula: raMatricula}
				}).done(function(){
					swal(
					    'Sucesso!',
					    'Bem-vindo ao novo sistema do RU!',
					    'success'
					).then(function(){
						location.reload();
					});
				}).fail(function(){
					swal(
					    'Erro!',
					    'Não foi possível salvar seus dados!',
					    'error'
					).then(function(){
						location.reload();
					});
				});
			});
			
			var formValidator = $("#primeiroAcesso").validate({
		    	rules : {
	      			categoria : { required : true },
		           	raMatricula : { required : true }
		    	}
		   	});
		   	$("#categoria").focus();
		 });
  	</script>
	<div class="panel panel-primary col-xs-12 col-md-8 col-md-offset-2">
		<div class="panel-body">
			<div class="page-header" style="margin-top: 10px;">
				<h3>
					<strong>Dados do Primeiro Acesso</strong>
				</h3>
			</div>

			<form id="primeiroAcesso">			  	
		        <h4>Oops! Vemos que é a primeira vez que acessa ao nosso sistema. Vamos preencher alguns dados?</h4>
		        <br>		        
		        <div class="row">
		            <div class="form-group col-xs-12 col-offset-xs-0 col-sm-6 col-sm-offset-3">
       					<label class="control-label">Categoria</label>
   						<select id="categoria" name="categoria" class="form-control">
							<option disabled value="" label="-- Selecione uma Categoria --" selected/>
	             				<c:forEach items="${categorias}" var="categoria">
								<option value="${categoria.descricao}" label="${categoria.descricao}"> </option>
							</c:forEach>
						</select>
					</div>
		        </div>
				<div id="cursos" class="row hidden">
					<div class="form-group col-xs-12 col-offset-xs-0 col-sm-6 col-sm-offset-3">
       					<label class="control-label">Curso</label>
   						<select id="curso" name="curso" class="form-control">
							<option disabled value="" label="-- Selecione um Curso --" selected/>
	             				<c:forEach items="${cursos}" var="curso">
								<option value="${curso.descricao}" label="${curso.descricao}"> </option>
							</c:forEach>
						</select>
					</div>
    			</div>
		        <div class="row">
					<div class="form-group col-xs-12 col-offset-xs-0 col-sm-6 col-sm-offset-3">
		            	<label class="control-label">RA / Matricula</label>
		            	<input id="raMatricula" class="form-control" placeholder="Digite seu número de RA / Matricula"/>
				 	</div>
		        </div>
		        <div class="form-group col-x-12 col-md-12" style="text-align: center; margin-top: 25px;">
		          <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> Salvar</button>
		        </div>
			</form>
		</div>
	</div>
	<jsp:include page="verifica.jsp"/>
</body>
</html>