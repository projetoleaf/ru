<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Categorias</title>
		<style>		
			nav.sidebar, .main{
			    -webkit-transition: margin 200ms ease-out;
			      -moz-transition: margin 200ms ease-out;
			      -o-transition: margin 200ms ease-out;
			      transition: margin 200ms ease-out;
			  }
			
			  .main{
			    padding: 10px 10px 0 10px;
			  }
			  
			  nav.sidebar li {
			  	border-radius: 1px;
			  }
			  
			  .navbar .navbar-nav > .active > a,
				.navbar .navbar-nav > .active > a:hover,
				.navbar .navbar-nav > .active > a:focus {
				    color: white !important;
				    background-color: #337ab7 !important;
			  }
			
			 @media (min-width: 765px) {
			
			    .main{
			      position: absolute;
			      width: calc(100% - 40px); 
			      margin-left: 40px;
			      float: right;
			    }
			
			    nav.sidebar:hover + .main{
			      margin-left: 200px;
			    }
			
			    nav.sidebar.navbar.sidebar>.container .navbar-brand, .navbar>.container-fluid .navbar-brand {
			      margin-left: 0px;
			    }
			
			    nav.sidebar .navbar-brand, nav.sidebar .navbar-header{
			      text-align: center;
			      width: 100%;
			      margin-left: 0px;
			    }
			    
			    nav.sidebar a{
			      padding-right: 13px;
			    }
	
			    nav.sidebar .navbar-nav > li{
			      border-bottom: 1px #e5e5e5 solid;
			    }
			
			
			    nav.sidebar .navbar-nav .open .dropdown-menu {
			      position: static;
			      float: none;
			      width: auto;
			      margin-top: 0;
			      background-color: transparent;
			      border: 0;
			      -webkit-box-shadow: none;
			      box-shadow: none;
			    }
			
			    nav.sidebar .navbar-collapse, nav.sidebar .container-fluid{
			      padding: 0 0px 0 0px;
			    }
			
			    .navbar-inverse .navbar-nav .open .dropdown-menu>li>a {
			      color: #777;
			    }
			
			    nav.sidebar{
			      width: auto;
			      height: 100%;
			      margin-left: -160px;
			      float: left;
			      margin-bottom: 0px;
			    }
			
			    nav.sidebar li {
			      width: 100%;
			    }
			
			    nav.sidebar:hover{
			      margin-left: 0px;
			    }
			
			    .forAnimate{
			      opacity: 0;
			    }
			  }
			   
			  @media (min-width: 1330px) {
			
			    .main{
			      width: calc(100% - 200px);
			      margin-left: 200px;
			    }
			
			    nav.sidebar{
			      margin-left: 0px;
			      float: left;
			    }
			
			    nav.sidebar .forAnimate{
			      opacity: 1;
			    }
			  }
			
			  nav.sidebar .navbar-nav .open .dropdown-menu>li>a:hover, nav.sidebar .navbar-nav .open .dropdown-menu>li>a:focus {
			    color: #CCC;
			    background-color: transparent;
			  }
			
			  nav:hover .forAnimate{
			    opacity: 1;
			  }
			  section{
			    padding-left: 15px;
			  }
		  </style>
	</head>
	<body>
	<div class="row">
		<div class="col-sm-3">
			<nav class="navbar navbar-default sidebar panel panel-primary" role="navigation">
			    <div class="navbar-header">
			      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			      </button>      
			    </div>
			    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
			      <ul class="nav navbar-nav">
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-calendar"></span> Reservas</a></li>
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-usd"></span> Vendas</a></li>			          
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-user"></span> Usuários</a></li>        
			        <li class="dropdown active">
			         <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span style="font-size:16px;" class="glyphicon glyphicon-wrench"></span> Manutenção</span></a>
			         <ul class="dropdown-menu forAnimate" role="menu">
			           <li ><a href="#"><span style="font-size:16px;" class="glyphicon glyphicon-edit"></span> Categorias</a></li>
			           <li class="divider"></li>
			           <li><a href="#"><span style="font-size:16px;" class="glyphicon glyphicon-edit"></span> Cursos</a></li>
			           <li class="divider"></li>
			           <li><a href="#"><span style="font-size:16px;" class="glyphicon glyphicon-edit"></span> Feriados</a></li>
			           <li class="divider"></li>
			           <li><a href="#"><span style="font-size:16px;" class="glyphicon glyphicon-edit"></span> Tipos</a></li>
			         </ul>
			       </li>
			       <li class="dropdown">
			         <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span style="font-size:16px;" class="glyphicon glyphicon-stats"></span> Relatórios</span></a>
			         <ul class="dropdown-menu forAnimate" role="menu">
			           <li ><a href="#"><span style="font-size:16px;" class="glyphicon glyphicon-list-alt"></span> Planilhas</a></li>
			           <li class="divider"></li>
			           <li><a href="#"><span style="font-size:16px;" class="glyphicon glyphicon-picture"></span> Gráficos</a></li>
			         </ul>
			       </li>
			      </ul>
			    </div>
			</nav>
		</div>
		<div class="col-sm-9">
			<div class="panel panel-primary">			
				<div class="panel-body">
					<table class="table table-hover table-bordered table-condensed" style="font-family:verdana; font-size: 12px;">
						<thead>
							<tr>
								<th style="width: 130px;">ID</th>
								<th>Descrição</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>Visitantes</td>
							</tr>
							<tr>
								<td>2</td>
								<td>CTI</td>
							</tr>
						</tbody>
					</table>
					<form>
					<div class="row">
						<div class="col-xs-10 col-sm-6">
							<div class="form-group">
							    <label for="descricao">Descrição</label>
							    <input type="text" class="form-control" id="descricao" placeholder="Descrição">							    
							</div>
						</div>
						<div class="col-xs-2 col-sm-2" style="padding: 0">
							<div style="margin-top: 25px;"></div>
							<button type="submit" class="btn btn-primary">Salvar</button>
						</div>
					</div>					  
					  <button type="submit" class="btn btn-primary">Incluir</button>
					  <button type="submit" class="btn btn-warning">Alterar</button>
					  <button type="submit" class="btn btn-danger">Excluir</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	</body>
</html>