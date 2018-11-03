var estudiantes = [];
var cursosAprobados = [];
var carnet_busqueda = "";
var global_token = "";

function VisualizarArbol() {
	var req = new HttpClient();

	req.get("http://192.168.56.101:7777/proyecto2/server/Estudiante/Grafica", true, function(response) {
		AbrirPagina(response);
	});
}

function CargarEstudiantes() {
	var req = new HttpClient();
	for(var i = 0; i < estudiantes.length; i++)
	{
		var text = JSON.stringify(estudiantes[i]);
		for(var j = 0; j < estudiantes[i].cursos.length; j++)
		{
			text = text.replace("CodigoCurso","codigoCurso");
		}
		var input = JSON.parse(text);
		req.post("http://192.168.56.101:7777/proyecto2/server/Estudiante/Insertar", false, input, function(response) {

		});
		
	}
	DynamicRefresh();
}

function AgregarCurso()
{
	var textBoxCodigoCurso = document.getElementById("textBoxCodigoCurso").value;
	var textBoxNombreCurso = document.getElementById("textBoxNombreCurso").value;
	var textBoxNotaCurso = document.getElementById("textBoxNotaCurso").value;
	var textBoxFechaCurso = document.getElementById("textBoxFechaCurso").value;


	if(textBoxCodigoCurso != "" && textBoxCodigoCurso >= 0)
	{
		if(textBoxNombreCurso != "" && textBoxNotaCurso != "" && textBoxNotaCurso >= 0 && textBoxNotaCurso <= 100 && textBoxFechaCurso != "")
		{	
			var input = {codigoCurso:textBoxCodigoCurso,nombre:textBoxNombreCurso,nota:textBoxNotaCurso,fecha:textBoxFechaCurso};
			cursosAprobados.push(input);
			document.getElementById("textBoxCodigoCurso").value = "";
			document.getElementById("textBoxNombreCurso").value = "";
			document.getElementById("textBoxNotaCurso").value = "";
			document.getElementById("textBoxFechaCurso").value = "";
		}
		else
		{
			alert("Ingrese informacion valida");
		}
	
	}
	else
	{
		alert("Ingrese un codigo valido");
	}
}


function AgregarEstudiante() {
	var textBoxCarnet = document.getElementById("textBoxCarnet").value;
	var textBoxDPI = document.getElementById("textBoxDPI").value;
	var textBoxNombres = document.getElementById("textBoxNombres").value;
	var textBoxApellidos = document.getElementById("textBoxApellidos").value;
	var textBoxEmail = document.getElementById("textBoxEmail").value;
	var textBoxCreditos = document.getElementById("textBoxCreditos").value;
	var textBoxContrasena = document.getElementById("textBoxContrasena").value;

	if(textBoxCarnet != "" && textBoxDPI != "" && textBoxNombres != "" && textBoxApellidos != "" && textBoxEmail != "" && textBoxCreditos != "" && textBoxContrasena != "")
	{
		var req = new HttpClient();
		var nuevo = {carnet:textBoxCarnet, dpi:textBoxDPI, nombre:textBoxNombres, apellidos:textBoxApellidos, correo:textBoxEmail, token:"", creditos:textBoxCreditos, password:textBoxContrasena, cursos:cursosAprobados};
		req.post("http://192.168.56.101:7777/proyecto2/server/Estudiante/Insertar", true, nuevo, function(response) {

			DynamicRefresh();
		});
	}
	else
	{
		alert("No deje los campos vacios");
	}
}


function BuscarEstudiante() {
	var input = document.getElementById("textBoxModificarEliminar").value;

	if(input != "")
	{
		var req = new HttpClient();
		req.post("http://192.168.56.101:7777/proyecto2/server/Estudiante/Buscar", true, input, function(response) {
		
			var result = JSON.parse(response);

			carnet_busqueda = result.carnet;

			document.getElementById("textBoxCarnet").value = carnet_busqueda;
			document.getElementById("textBoxDPI").value = result.dpi;
			document.getElementById("textBoxNombres").value = result.nombre;
			document.getElementById("textBoxApellidos").value = result.apellidos;
			document.getElementById("textBoxEmail").value = result.correo;
			global_token = result.token;
			document.getElementById("textBoxCreditos").value = result.creditos;
			document.getElementById("textBoxContrasena").value = result.password;
			cursosAprobados = result.cursos;

			document.getElementById("textBoxCarnet").disabled = true;

			document.getElementById("bModificar").disabled = false;
			document.getElementById("bModificar").innerHTML = "Modificar Estudiante: " + carnet_busqueda;
			document.getElementById("bVisualizar").disabled = false;
			document.getElementById("bVisualizar").innerHTML = "Visualizar Estudiante: " + carnet_busqueda;
		});
	}
}

function VisualizarEstudiante() {
	var req = new HttpClient();
	var input = carnet_busqueda;
	req.post("http://192.168.56.101:7777/proyecto2/server/Estudiante/Visualizar", true, input, function(response) {
		AbrirPagina(response);
	});
	
}

function ModificarEstudiante() {
	var textBoxCarnet = carnet_busqueda;
	var textBoxDPI = document.getElementById("textBoxDPI").value;
	var textBoxNombres = document.getElementById("textBoxNombres").value;
	var textBoxApellidos = document.getElementById("textBoxApellidos").value;
	var textBoxEmail = document.getElementById("textBoxEmail").value;
	var textBoxCreditos = document.getElementById("textBoxCreditos").value;
	var textBoxContrasena = document.getElementById("textBoxContrasena").value;

	if(textBoxCarnet != "" && textBoxDPI != "" && textBoxNombres != "" && textBoxApellidos != "" && textBoxEmail != "" && textBoxCreditos != "" && textBoxContrasena != "")
	{
		var req = new HttpClient();
		var nuevo = {carnet:textBoxCarnet, dpi:textBoxDPI, nombre:textBoxNombres, apellidos:textBoxApellidos, correo:textBoxEmail, token:global_token, creditos:textBoxCreditos, password:textBoxContrasena, cursos:cursosAprobados};
		req.post("http://192.168.56.101:7777/proyecto2/server/Estudiante/Modificar", true, nuevo, function(response) {
			
			document.getElementById("textBoxCarnet").disabled = false;
			DynamicRefresh();
		});
	}
	else
	{
		alert("No deje los campos vacios");
	}
}

function DynamicRefresh()
{
	document.getElementById("textBoxCarnet").value = "";
	document.getElementById("textBoxDPI").value = "";
	document.getElementById("textBoxNombres").value = "";
	document.getElementById("textBoxApellidos").value = "";
	document.getElementById("textBoxEmail").value = "";
	document.getElementById("textBoxCreditos").value = "";
	document.getElementById("textBoxContrasena").value = "";

	document.getElementById("textBoxCodigoCurso").value = "";
	document.getElementById("textBoxNombreCurso").value = "";
	document.getElementById("textBoxNotaCurso").value = "";
	document.getElementById("textBoxFechaCurso").value = "";

	document.getElementById("textBoxModificarEliminar").value = "";

	document.getElementById("bModificar").disabled = true;
	document.getElementById("bModificar").innerHTML = "Modificar Estudiante";
	document.getElementById("bVisualizar").disabled = true;
	document.getElementById("bVisualizar").innerHTML = "Visualizar Estudiante";
	document.getElementById("bCargarEstudiantes").disabled = true;
	carnet_busqueda = "";
	estudiantes = [];
	cursosAprobados = [];
}

function estudiantesSelected(file)
{
	if(file.type == "text")
	{
		estudiantes = JSON.parse(file.data).estudiantes;
	}
	else
	{
		var base64 = file.data.replace("data:;base64,", "");
		var text = window.atob(base64);
		estudiantes = JSON.parse(text).estudiantes;
	}
	var boton = document.getElementById("bCargarEstudiantes");
	boton.disabled = false;
}


function setup() {
	noCanvas();

	DynamicRefresh();

	var estudiantesChooser = createFileInput(estudiantesSelected);
	estudiantesChooser.position(600,242);
}

