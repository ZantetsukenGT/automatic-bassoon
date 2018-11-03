var actividades = [];

function VisualizarActividades() {
	var req = new HttpClient();

	req.get("http://192.168.56.101:7777/proyecto2/server/Tutor/VerActividades", true, function(response) {
		AbrirPagina(response);
	});
}

function CargarActividades() {
	if(actividades.length > 0)
	{
		var req = new HttpClient();
		for(var i = 0; i < actividades.length; i++)
		{
			var text = JSON.stringify(actividades[i]).replace("Nombre","nombre");
			text = text.replace("Fecha","fecha");
			text = text.replace("Ponderacion","ponderacion");
			text = text.replace("Descripcion","descripcion");
			actividades[i] = JSON.parse(text);
		}
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/InsertarActividad", true, actividades, function(response) {

			DynamicRefresh();
		});
	}
}

function AgregarActividad() {
	var textBoxTipo = document.getElementById("textBoxTipo").value;
	var textBoxNombre = document.getElementById("textBoxNombre").value;
	var textBoxFecha = document.getElementById("textBoxFecha").value;
	var textBoxPonderacion = document.getElementById("textBoxPonderacion").value;
	var textBoxDescripcion = document.getElementById("textBoxDescripcion").value;

	if(textBoxTipo != "" && textBoxNombre != "" && textBoxFecha != "" && textBoxPonderacion != "" && textBoxDescripcion != "")
	{
		var req = new HttpClient();
		var nuevo = {actividad:textBoxTipo,nombre:textBoxNombre,fecha:textBoxFecha,ponderacion:textBoxPonderacion,descripcion:textBoxDescripcion};
		actividades.push(nuevo);
		req.post("http://192.168.56.101:7777/proyecto2/server/Tutor/InsertarActividad", true, actividades, function(response) {

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
	document.getElementById("textBoxTipo").value = "";
	document.getElementById("textBoxNombre").value = "";
	document.getElementById("textBoxFecha").value = "";
	document.getElementById("textBoxPonderacion").value = "";
	document.getElementById("textBoxDescripcion").value = "";

	document.getElementById("bCargarActividades").disabled = true;
	actividades = [];
}

function actividadesSelected(file)
{
	if(file.type == "text")
	{
		actividades = JSON.parse(file.data).actividades;
	}
	else
	{
		var base64 = file.data.replace("data:;base64,", "");
		var text = window.atob(base64);
		actividades = JSON.parse(text).actividades;
	}
	var boton = document.getElementById("bCargarActividades");
	boton.disabled = false;
}


function setup() {
	noCanvas();

	DynamicRefresh();

	var actividadesChooser = createFileInput(actividadesSelected);
	actividadesChooser.position(600,242);
}

