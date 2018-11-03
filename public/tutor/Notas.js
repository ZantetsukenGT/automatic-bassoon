var notas = [];

function VisualizarNotas() {
	var req = new HttpClient();

	req.get("http://192.168.56.101:7777/proyecto2/server/Tutor/ControlNotas", true, function(response) {
		AbrirPagina(response);
	});
}

function CargarNotas() {
	var textBoxActividad = document.getElementById("textBoxActividad").value;
	if(textBoxActividad != "")
	{
		var req = new HttpClient();
		for(var i = 0; i < notas.length; i++)
		{
			notas[i].actividad = textBoxActividad;
		}
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/InsertarNotas", true, notas, function(response) {

			DynamicRefresh();
		});
	}
	else
	{	
		alert("Debe elegir un nombre de actividad");
	}
}

function AgregarNota() {
	var textBoxActividad = document.getElementById("textBoxActividad").value;
	var textBoxCarnet = document.getElementById("textBoxCarnet").value;
	var textBoxNota = document.getElementById("textBoxNota").value;

	if(textBoxActividad != "" && textBoxCarnet != "" && textBoxNota != "")
	{
		var req = new HttpClient();
		var nuevo = [];
		var obj = {actividad:textBoxActividad,carnet:textBoxCarnet,nota:textBoxNota};
		nuevo.push(obj);
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/InsertarNotas", true, nuevo, function(response) {

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
	document.getElementById("textBoxActividad").value = "";
	document.getElementById("textBoxCarnet").value = "";
	document.getElementById("textBoxNota").value = "";

	document.getElementById("bCargarNotas").disabled = true;
	notas = [];
}

function notasSelected(file)
{
	var texto = "";
	if(file.type == "text")
	{
		texto = file.data;
	}
	else
	{
		var base64 = file.data.replace("data:;base64,", "");
		var text = window.atob(base64);
		texto = text;
	}
	
	var array = texto.split("\n");
	for(var i = 0; i < array.length; i++)
	{
		if(array[i] != "")
		{
			var subArray = array[i].split(",");
			var obj = {actividad:"",carnet:subArray[0],nota:subArray[1]}
			notas.push(obj);
		}
	}
	var boton = document.getElementById("bCargarNotas");
	boton.disabled = false;
}


function setup() {
	noCanvas();

	DynamicRefresh();

	var notasChooser = createFileInput(notasSelected);
	notasChooser.position(600,242);
}

