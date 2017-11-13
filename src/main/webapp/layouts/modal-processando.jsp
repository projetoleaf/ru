<%@taglib prefix="dandelion" uri="http://github.com/dandelion" %>
<dandelion:bundle includes="font-awesome,sweetalert2"/>

<!-- Mensagem "Processando..." -->
<script type="text/javascript">
  $("form").submit(function() {      
      swal({
		  title: 'Processando ...',
		  text: 'Aguarde, em processamento ...',
		  timer: 3000,
		  onOpen: function () {
		    swal.showLoading()
		  }
	  }).then(
	  	function () {},
	 // handling the promise rejection
		function (dismiss) {
		}
	  )
      return true;
  });
</script>