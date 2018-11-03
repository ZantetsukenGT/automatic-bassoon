var estudiantes = [];
var cursos = [];


function estudiantesSelected()
{
	var genericOptions = document.getElementById("estudiantesOptions");
	var i = genericOptions.selectedIndex;
	if(i != -1)
	{
		document.getElementById("textBoxCarnet").value = estudiantes[i].carnet;
		document.getElementById("textBoxDPI").value = estudiantes[i].dpi;
		document.getElementById("textBoxNombres").value = estudiantes[i].nombre;
		document.getElementById("textBoxApellidos").value = estudiantes[i].apellidos;
		document.getElementById("textBoxEmail").value = estudiantes[i].correo;
		document.getElementById("textBoxCreditos").value = estudiantes[i].creditos;
		document.getElementById("bEliminarTutor").disabled = false;
		document.getElementById("bAgregarTutor").disabled = false;
	}	
}

function AgregarTutor()
{

	var i = document.getElementById("estudiantesOptions").selectedIndex;
	var j = document.getElementById("cursosOptions").selectedIndex;
	var textBoxPeriodo = document.getElementById("textBoxPeriodo").value;

	if(i != -1 && j != -1 && textBoxPeriodo != "")
	{
		var estudiantesOptionsText = estudiantes[i].carnet;
		var cursosOptionsText = cursos[j].codigo;

		var input = {estudiante:estudiantesOptionsText,curso:cursosOptionsText,periodo:textBoxPeriodo};
		var req = new HttpClient();
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/Insertar", true, input, function(response) {

			DynamicRefresh();
		});
	}
	else
	{
		alert("Debe elegir un estudiante/curso/periodo validos");
	}
}

function EliminarTutor()
{
	var i = document.getElementById("estudiantesOptions").selectedIndex;
	if(i != -1)
	{
		var estudiantesOptionsText = estudiantes[i].carnet;
	
		var input = estudiantesOptionsText;
		var req = new HttpClient();
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/Eliminar", true, input, function(response) {

			DynamicRefresh();
		});
	}
	else
	{
		alert("Debe elegir un estudiante para eliminarlo de los tutores");
	}
}

function VisualizarAVL()
{
	var req = new HttpClient();
	req.get("http://192.168.56.101:7777/proyecto2/server/Tutor/Grafica", true, function(response) {

		AbrirPagina(response);
	});
}

function DynamicRefresh()
{
	var genericOptions = document.getElementById("estudiantesOptions");
	while(genericOptions.length > 0) {
	    genericOptions.remove(0);
	}

	var genericOptions = document.getElementById("cursosOptions");
	while(genericOptions.length > 0) {
	    genericOptions.remove(0);
	}

	document.getElementById("textBoxCarnet").value = "";
	document.getElementById("textBoxDPI").value = "";
	document.getElementById("textBoxNombres").value = "";
	document.getElementById("textBoxApellidos").value = "";
	document.getElementById("textBoxEmail").value = "";
	document.getElementById("textBoxCreditos").value = "";

	document.getElementById("bEliminarTutor").disabled = true;
	document.getElementById("bAgregarTutor").disabled = true;

	var req = new HttpClient();
	req.get("http://192.168.56.101:7777/proyecto2/server/Tutor/SetupEstudiantes", false, function(response) {
	
		estudiantes = JSON.parse(response);
		console.log(estudiantes);
		if(estudiantes.length > 0)
		{
			var estudiantesOptions = document.getElementById("estudiantesOptions");
			for(var i = 0; i < estudiantes.length; i++)
			{
				var option = document.createElement("option");
				option.text = "" + estudiantes[i].carnet + " -- " + estudiantes[i].nombre + ", " + estudiantes[i].apellidos;
				estudiantesOptions.add(option);
			}
		}
	});

	req.get("http://192.168.56.101:7777/proyecto2/server/Tutor/SetupCursos", false, function(response) {
	
		cursos = JSON.parse(response);
		console.log(cursos);
		if(cursos.length > 0)
		{
			var cursosOptions = document.getElementById("cursosOptions");
			for(var i = 0; i < cursos.length; i++)
			{
				var option = document.createElement("option");
				option.text = "" + cursos[i].codigo + " -- " + cursos[i].nombre;
				cursosOptions.add(option);
			}
		}
	});

	document.getElementById("estudiantesOptions").selectedIndex = -1;
	document.getElementById("cursosOptions").selectedIndex = -1;
}

function setup() {
	noCanvas();
	DynamicRefresh();
	document.getElementById("textBoxCarnet").disabled = true;
	document.getElementById("textBoxDPI").disabled = true;
	document.getElementById("textBoxNombres").disabled = true;
	document.getElementById("textBoxApellidos").disabled = true;
	document.getElementById("textBoxEmail").disabled = true;
	document.getElementById("textBoxCreditos").disabled = true;
}

