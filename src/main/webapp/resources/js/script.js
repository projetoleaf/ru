function demoFromHTML() {
	html2canvas(document.getElementById("conteudo"), {
        onrendered: function(canvas) { 
        	
            var img = canvas.toDataURL(
                'image/png');              
            var doc = new jsPDF('landscape', 'pt');
            doc.addImage(img,'PNG', 10, 165, 822,0);
            doc.save('Cardapio.pdf');
        }
    });
}