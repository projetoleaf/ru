<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Reservar refeições</title>
		<style>			
			.modal-header, .close {
			      background-color: #337ab7;
			      color:white !important;
			      text-align: center;
			      border-radius: 5px 5px 0px 0;
			  }
			  
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
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-user"></span> Conta</a></li>
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-calendar"></span> Histórico</a></li>			          
			        <li class="active"><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-cutlery"></span> Reserva</a></li>        
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-resize-horizontal"></span> Transferências</a></li>
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-usd"></span> Saldo</a></li>
			        <li ><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-cutlery"></span> Remanescentes</a></li>
			        <li style="border-bottom: 0"><a href="#"><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-alert"></span> Penalidades</a></li>
			      </ul>
			    </div>
			</nav>
		</div>
		<div class="col-sm-9">
			<div class="panel panel-primary">			
				<div class="panel-body">
					<h3>Selecione o(s) dia(s) que deseja reservar:</h3>
					<br>
					<br>
					<div class="text-center">
						<input type="checkbox">&nbsp;Segunda-feira - 20/05&nbsp;
						<input type="checkbox">&nbsp;Terça-feira - 21/05&nbsp;
						<input type="checkbox">&nbsp;Quarta-feira - 22/05&nbsp;
						<input type="checkbox">&nbsp;Quinta-feira - 23/05&nbsp;
						<input type="checkbox">&nbsp;Sexta-feira - 24/05
					</div>
					<div class="text-center">
					    <br>
						<br>
						<button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Reservar</button>
					</div>
				</div>
			</div>
		</div>
	</div>		
		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
		  <div class="modal-dialog">
		
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">Reservas</h4>
		      </div>
		      <div class="modal-body">
		        <p>Confirme os dias da sua reserva...</p>
		        <p>&#10004...</p>
		        <p>&#10004...</p>
		        <p>&#10004...</p>
		      </div>
		      
			  <div class="modal-footer">
		        <div class="text-center">		        
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
				<button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Confirmar reserva</button>
			  </div>
		      </div>
		    </div>		
		  </div>
		</div>
	</body>
</html>