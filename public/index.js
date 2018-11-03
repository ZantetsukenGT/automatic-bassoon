function setup() {
	noCanvas();
}

function IniciarSesion() {
	var usuarioText = document.getElementById("textBox1").value;
	var passwordText = document.getElementById("textBox2").value;
	
	if(usuarioText != "" && passwordText != "")
	{
		if(usuarioText == "ADMIN")
		{
			if(passwordText == "140918")
			{
				alert("Se loggeara como el administrador del sitio");
				CambiarPagina('http://192.168.56.1:8080/administracion/AdminCP.html');
			}
			else
			{
				alert("Loggeo de administrador incorrecto");
			}
		}
		else 
		{
			var req = new HttpClient();
			var input = {usuario: usuarioText, password: passwordText};
			req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/Sesion", true, input, function(response) {
			
				alert("Se loggeara como usuario: " + usuarioText);
				CambiarPagina('http://192.168.56.1:8080/tutor/UCP.html');
			});
		
		}
	}
	else
	{
		alert("No deje los campos vacios");
	}
}
