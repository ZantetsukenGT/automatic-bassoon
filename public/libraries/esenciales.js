function HttpClient() {
	this.get = function(aUrl, async, aCallback) {
		var anHttpRequest = new XMLHttpRequest();

		anHttpRequest.open( "GET", aUrl, async );
		anHttpRequest.setRequestHeader('Content-Type', 'application/json');
    
		anHttpRequest.onreadystatechange = function() { 
	    	if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
			aCallback(anHttpRequest.responseText);
		}
     
		anHttpRequest.send();
	}
	this.post = function(aUrl, async , obj, aCallback) {
		var anHttpRequest = new XMLHttpRequest();

		anHttpRequest.open( "POST", aUrl, async );
		anHttpRequest.setRequestHeader('Content-Type', 'application/json');  

		anHttpRequest.onreadystatechange = function() { 
	    	if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
			aCallback(anHttpRequest.responseText);
		}
  		var sendObj = JSON.stringify(obj);
		anHttpRequest.send(sendObj);
	}
}

function CambiarPagina(url)
{
	window.location.href = url;
}

function AbrirPagina(url)
{
	window.open(url);
}
