/******************************************************************/
/*	Gestion de las vistas de la aplicacion						  */
/******************************************************************/

//http://stackoverflow.com/questions/5527823/simplest-of-backbone-js-examples
//http://stackoverflow.com/questions/21975045/a-simple-example-backbone-js-tutorial-collection-based-on-json-view
//http://blog.42floors.com/structure-render-views-backbone/
//http://coenraets.org/blog/2012/01/backbone-js-lessons-learned-and-improved-sample-app/


//render pedidos (en dos sitios distintos)

function ViewStructure(){
	
	

	var StateView = Backbone.View.extend({
	
    el: 'body',
    
    events: {
    },
    
    initialize: function() {
        console.log("StateView initialize");
        console.log(this.el);
    },

    render: function() {
       //renderizado de la coleccion de estados en ambos sitios:
    	
		var str = '{"user":"'+globalV.User+'"}';
		var json = JSON.parse(str);
		
		var data = remoteDT.pedidosLogin.where(json);
    	
		var viewHtml="";
		var viewHtml_1="";
		
		if (data != 0){
			
			for(var i = 0; i < data.length; ++i) {
				
				var pedido = data[i];				
				
				viewHtml+= "<li>";
				viewHtml+="<div class=\"ui-grid-b\">";
				viewHtml+="<div class=\"ui-block-a\">";
				viewHtml+="<img src=\"img/user.png\">";
				viewHtml+="</div>";	
	      		viewHtml+="<div class=\"ui-block-b\">";
	      		viewHtml+="<div class=\"ui-grid-solo\">";
	      		viewHtml+="<div class=\"ui-block-a\">";
	      		viewHtml+="<p>Code: <b>"+pedido.get("code_command")+"</b></p>";
	      		viewHtml+="</div>"
	      		viewHtml+="<div class=\"ui-block-a\">";
	      		viewHtml+="<p>Type: <b>"+pedido.get("type")+"</b></p>";	
	      		viewHtml+="</div>";
	      		viewHtml+="<div class=\"ui-block-a\">";
	      		viewHtml+="<p>State: <b>"+pedido.get("state")+"</b></p>";	
	      		viewHtml+="</div>";
	      		viewHtml+="</div>";
	      		viewHtml+="</div>";
	      		viewHtml+="<div class=\"ui-block-c\">";
	      		viewHtml+="<a href=\"#p\" onclick=\"getDataConsulta('"+pedido.get("code_command")+"','"+pedido.get("type")+"','"+pedido.get("state")+"')\"><img src=\"img/carat-r-black.png\"/></a>";
	      		viewHtml+="</div>";
	      		viewHtml+="</div>";
				viewHtml+="</li>";
					
				
				viewHtml_1+= "<li>";
				viewHtml_1+="<div class=\"ui-grid-c\">";
				viewHtml_1+="<div class=\"ui-block-a\" style=\"width:25%;\">";
				viewHtml_1+="<img src=\"img/user.png\">";
				viewHtml_1+="</div>";
				viewHtml_1+="<div class=\"ui-block-b\" style=\"width:25%;\">";
				viewHtml_1+="<input type=\"checkbox\" name=\"sel_"+pedido.get("code_command")+"\" id=\"sel_"+pedido.get("code_command")+"\" class=\"custom\">";
				viewHtml_1+="</div>";
	      		viewHtml_1+="<div class=\"ui-block-c\" style=\"width:25%;\">";
	      		viewHtml_1+="<div class=\"ui-grid-solo\">";
	      		viewHtml_1+="<div class=\"ui-block-a\">";
	      		viewHtml_1+="<p>Code: <b>"+pedido.get("code_command")+"</b></p>";
	      		viewHtml_1+="</div>"
	      			
	      		viewHtml_1+="<div class=\"ui-block-a\">";
	      		viewHtml_1+="<p>Type: <b>"+pedido.get("type")+"</b></p>";	
	      		viewHtml_1+="</div>";
	      		
	      		viewHtml_1+="<div class=\"ui-block-a\">";
	      		viewHtml_1+="<p>State: <b>"+pedido.get("state")+"</b></p>";	
	      		viewHtml_1+="</div>";
	      		
	      		viewHtml_1+="</div>";
	      		
	      		viewHtml_1+="</div>";
	      		
	      		viewHtml_1+="<div class=\"ui-block-d\" style=\"width:15%;\">";
	      		viewHtml_1+="<a href=\"#p\" onclick=\"getDataConsultaEdicion('"+pedido.get("code_command")+"','"+pedido.get("type")+"','"+pedido.get("state")+"','"+i+"')\"><img src=\"img/carat-r-black.png\"/></a>";
	      		viewHtml_1+="</div>";
	      		
	      		viewHtml_1+="</div>";
	      		
				viewHtml_1+="</li>";	
			}
			
			document.getElementById("deliveries").innerHTML = viewHtml;
			document.getElementById("deliveries_1").innerHTML = viewHtml_1;
			
			return this;
		}
    	
		
    	console.log('State rendered');
    },

	});

	this.vistaEstado = StateView;
	
	
	var StateView_1 = Backbone.View.extend({
		
	    el: 'body',
	    
	    events: {
	    },
	    
	    initialize: function() {
	        console.log("StateView_1 initialize");
	        console.log(this.el);
	    },

	    render: function(pedido) {
	       //renderizado de la coleccion de estados en ambos sitios:
	    	
			var str = '{"user":"'+globalV.User+'","code_command":"'+pedido+'"}';
			var json = JSON.parse(str);
			
			var data = remoteDT.estadosLogin.where(json);
	    	
			var viewHtml="";
			var viewHtml_1="";
			
			if (data != 0){
				
				for(var i = 0; i < data.length; ++i) {
					
					var estado = data[i];		
					
					viewHtml+= "<li>";
					
					viewHtml+="<div class=\"ui-grid-b\">";
					viewHtml+="<div class=\"ui-block-a\">";
					viewHtml+="<img src=\"img/truck.png\">";
					viewHtml+="</div>";
  		
					viewHtml+="<div class=\"ui-block-b\">";
					viewHtml+="<div class=\"ui-grid-solo\">";
  					viewHtml+="<div class=\"ui-block-a\">";
  					viewHtml+="<p>Code: <b>"+estado.get("code_state")+"</b></p>";
  					viewHtml+="</div>";
  					viewHtml+="<div class=\"ui-block-a\">";
  					viewHtml+="<p>State: <b>"+estado.get("state")+"</b></p>";	
  					viewHtml+="</div>";
  					viewHtml+="<div class=\"ui-block-a\">";
  					viewHtml+="<p>Situation: <b>"+estado.get("situation")+"</b></p>";	
  					viewHtml+="</div>";
  					viewHtml+="<div class=\"ui-block-a\">";
  					viewHtml+="<p>Time: <b>"+estado.get("timestamp")+"</b></p>";	
  					viewHtml+="</div>";
  					viewHtml+="</div>";
  					viewHtml+="</div>";
  		
  					viewHtml+="<div class=\"ui-block-c\">";
  					viewHtml+="<a href=\"#\"><img src=\"img/carat-r-black.png\"/></a>";
  					viewHtml+="</div>";
  					viewHtml+="</div>";
					viewHtml+="</li>";
				}	
			
				document.getElementById("states").innerHTML = viewHtml;				
				return this;
			}
	    	
			
	    	console.log('State_1 rendered');
	    },

		});

		this.vistaEstado_1 = StateView_1;	
}

var view = new ViewStructure();





