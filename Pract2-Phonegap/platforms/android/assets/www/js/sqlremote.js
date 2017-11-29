/******************************************************/
/* estructura del modelo						      */
/* en esta primera versión, descarto objetos anidados */
/******************************************************/

function RemoteDBStructure(){
	
	
	//objeto User
	var User = Backbone.Model.extend({
		defaults: {
            user: '1',
            pass: '1',
            perfil: '1'
        },
		
        initialize: function(){
            //alert("Creado objeto User!");
        }
    });
	
	this.Usuario = User;
	
	//objeto usuarios
	var Users = Backbone.Collection.extend({
		model: User
	});
	
	this.Usuarios = Users;
	
	//objeto estado de un pedido
	var State = Backbone.Model.extend({
		defaults: {
			user: '1',
			code_command: '1',
			code_state: '1',
			state: '1',
			situation: '1',
			timestamp: '1'
		},
		
		initialize: function(){
		}
	});
	
	this.Estado = State;
	
	//objeto estados
	var States = Backbone.Collection.extend({
		model: State
	});
	
	this.Estados = States;
	
	//objeto estado de un pedido
	var Command = Backbone.Model.extend({
		defaults: {
			user: '1',
			code_command: '1',
			type: '1',
			state: '1'
		},
		
		initialize: function(){
		}
	});
	
	this.Comanda = Command;
	
	//objeto estados
	var Commands = Backbone.Collection.extend({
		model: Command
	});
	
	this.Comandas = Commands;
}

var remoteDB = new RemoteDBStructure();

