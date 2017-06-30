$(document).ready(function() {
	var password1 = $('#password1');
	var password2 = $('#password2');
	var passwordsInfo = $('#pass-info');

	passwordStrengthCheck(password1, password2, passwordsInfo);

});

function passwordStrengthCheck(password1, password2, passwordsInfo) {
	var WeakPass = /(?=.{5,}).*/;
	var MediumPass = /^(?=\S*?[a-z])(?=\S*?[0-9])\S{5,}$/;
	var StrongPass = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])\S{5,}$/;
	var VryStrongPass = /^(?=\S*?[A-Z])(?=\S*?[a-z])(?=\S*?[0-9])(?=\S*?[^\w\*])\S{5,}$/;

	$(password1).on(
			'keyup',
			function(e) {
				if (VryStrongPass.test(password1.val())) {
					passwordsInfo.removeClass().addClass('vrystrongpass').html(
							"Excelente").css("color", "#238E23");
				} else if (StrongPass.test(password1.val())) {
					passwordsInfo.removeClass().addClass('strongpass').html(
							"Forte").css("color", "#0000FF");
				} else if (MediumPass.test(password1.val())) {
					passwordsInfo.removeClass().addClass('goodpass').html(
							"Regular").css("color", "#DAA520");
				} else if (WeakPass.test(password1.val())) {
					passwordsInfo.removeClass().addClass('stillweakpass').html(
							"Fraca").css("color", "#FF7F00");
				} else {
					passwordsInfo.removeClass().addClass('weakpass').html(
							"Muito fraca").css("color", "#f00");
				}
			});
}