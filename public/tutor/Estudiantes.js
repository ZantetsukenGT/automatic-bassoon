var estudiantes = [];

function VisualizarTablaHash() {
	var req = new HttpClient();

	req.get("http://192.168.56.101:7777/proyecto2/server/Tutor/MisEstudiantes", true, function(response) {
		AbrirPagina(response);
	});
}

function CargarEstudiantes() {
	var req = new HttpClient();
	req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/InsertarEstudiantes", true, estudiantes, function(response) {

	});
	DynamicRefresh();
}

function AgregarEstudiante() {
	var textBoxCarnet = document.getElementById("textBoxCarnet").value;

	if(textBoxCarnet != "")
	{
		var req = new HttpClient();
		var nuevo = [];
		nuevo.push(textBoxCarnet);
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/InsertarEstudiantes", true, nuevo, function(response) {

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

	document.getElementById("bCargarEstudiantes").disabled = true;
	estudiantes = [];
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

