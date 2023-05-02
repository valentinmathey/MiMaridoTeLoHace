		function validarFormulario() {
	
			const email = document.form["form"]["inputEmail"].value;
			const password = document.form["form"]["inputPassword"].value;

			if (email === 'Email' && password === 'Password') {
				alert('Inicio de sesión exitoso');
				return true;
			} else {			
				alert('Email o contraseña incorrecta');
				return false;
			}
		}
