var pensum = {};
var prerrequisitos = [];
var codigo_busqueda = "";

function VisualizarGrafo() {
	var req = new HttpClient();

	req.get("http://192.168.56.101:7777/proyecto2/server/Curso/Grafo", true, function(response) {
		AbrirPagina(response);
	});
}

function VisualizarMatriz() {
	var req = new HttpClient();

	req.get("http://192.168.56.101:7777/proyecto2/server/Curso/Matriz", true, function(response) {
		AbrirPagina(response);
	});
}

function CargarPensum() {
	var req = new HttpClient();
	
	for(var i = 0; i < pensum.cursos.length; i++)
	{
		var input = pensum.cursos[i];
		req.post("http://192.168.56.101:7777/proyecto2/server/Curso/Insertar", false, input, function(response) {
			
		});
	}
	DynamicRefresh();
}

function AgregarPre()
{
	var textBoxPre = document.getElementById("textBoxPre").value;

	if(textBoxPre != "" && textBoxPre >= 0)
	{
		prerrequisitos.push(textBoxPre);
		document.getElementById("textBoxPre").value = "";
	}
	else
	{
		alert("Ingrese un codigo valido");
	}
}

function AgregarCurso()
{
	var textBoxCodigo = document.getElementById("textBoxCodigo").value;
	var textBoxNombre = document.getElementById("textBoxNombre").value;
	var textBoxCreditos = document.getElementById("textBoxCreditos").value;
	var textBoxArea = document.getElementById("textBoxArea").value;

	if(textBoxCodigo != "" && textBoxCodigo >= 0)
	{
		if(textBoxNombre != "" && textBoxCreditos >= 0 && textBoxArea != "")
		{	
			var input = {nombre:textBoxNombre,codigo:textBoxCodigo,creditos:textBoxCreditos,pre:prerrequisitos,area:textBoxArea};
			var req = new HttpClient();
			req.post("http://192.168.56.101:7777/proyecto2/server/Curso/Insertar", false, input, function(response) {
				
				DynamicRefresh();
			});
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

function BuscarCurso() {
	var input = document.getElementById("textBoxModificarEliminar").value;

	if(input != "" && input >= 0)
	{
		var req = new HttpClient();
		req.post("http://192.168.56.101:7777/proyecto2/server/Curso/Buscar", true, input, function(response) {
		
			var result = JSON.parse(response);

			codigo_busqueda = result.codigo;

			document.getElementById("textBoxCodigo").value = codigo_busqueda;
			document.getElementById("textBoxNombre").value = result.nombre;
			document.getElementById("textBoxCreditos").value = result.creditos;
			document.getElementById("textBoxArea").value = result.area;
			prerrequisitos = result.pre;

			document.getElementById("textBoxCodigo").disabled = true;

			document.getElementById("bModificar").disabled = false;
			document.getElementById("bModificar").innerHTML = "Modificar Curso: " + codigo_busqueda;
			document.getElementById("bEliminar").disabled = false;
			document.getElementById("bEliminar").innerHTML = "Eliminar Curso: " + codigo_busqueda;
		});
	}
	else
	{
		alert("Ingrese un codigo valido");
	}
}

function ModificarCurso() {
	var textBoxNombre = document.getElementById("textBoxNombre").value;
	var textBoxCreditos = document.getElementById("textBoxCreditos").value;
	var textBoxArea = document.getElementById("textBoxArea").value;

	if(codigo_busqueda != "" && codigo_busqueda >= 0)
	{
		if(textBoxNombre != "" && textBoxCreditos != "" && textBoxCreditos >= 0 && textBoxArea != "")
		{	
			var req = new HttpClient();
			var input = {nombre:textBoxNombre,codigo:codigo_busqueda,creditos:textBoxCreditos,pre:prerrequisitos,area:textBoxArea};
			req.post("http://192.168.56.101:7777/proyecto2/server/Curso/Modificar", true, input, function(response) {

				document.getElementById("textBoxCodigo").disabled = false;
				DynamicRefresh();
			});
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

function EliminarCurso() {

	if(codigo_busqueda != "" && codigo_busqueda >= 0)
	{	
		var input = codigo_busqueda;
		var req = new HttpClient();
		req.post("http://192.168.56.101:7777/proyecto2/server/Curso/Eliminar", true, input, function(response) {
			document.getElementById("textBoxCodigo").disabled = false;
			DynamicRefresh();
		});
	}
	else
	{
		alert("Ingrese un codigo valido");
	}
}

function DynamicRefresh()
{
	document.getElementById("textBoxNombre").value = "";
	document.getElementById("textBoxCodigo").value = "";
	document.getElementById("textBoxCreditos").value = "";
	document.getElementById("textBoxArea").value = "";
	document.getElementById("textBoxPre").value = "";

	document.getElementById("textBoxModificarEliminar").value = "";

	document.getElementById("bModificar").disabled = true;
	document.getElementById("bModificar").innerHTML = "Modificar Curso";
	document.getElementById("bEliminar").disabled = true;
	document.getElementById("bEliminar").innerHTML = "Eliminar Curso";
	document.getElementById("bCargarPensum").disabled = true;
	codigo_busqueda = "";
	pensum = {};
	prerrequisitos = [];
}

function pensumSelected(file)
{
	if(file.type == "text")
	{
		pensum = JSON.parse(file.data);
	}
	else
	{
		var base64 = file.data.replace("data:;base64,", "");
		var text = window.atob(base64);
		pensum = JSON.parse(text);
	}
	var boton = document.getElementById("bCargarPensum");
	boton.disabled = false;
}


function setup() {
	noCanvas();

	DynamicRefresh();

	var pensumChooser = createFileInput(pensumSelected);
	pensumChooser.position(600,242);
}

