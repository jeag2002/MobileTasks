/********************************************************************************/
/* TIMER DE LA APLICACION										    			*/
/********************************************************************************/

//en el ejemplo solo servirá para dar a conocer el tiempo de uso de la aplicacion
//puede servir para mas cosas (actualizar el GPS, lanzar los server push al resto
//de la aplicación, etc ...

//arranca así: Timer1.Timer.toggle();
//se para así: Timer1.resetStopwatch();

function TimerStructure(){
	var Timer1 = new (function() {
		
		
	    //tiempo en milisegundos
	    var incrementTime = 1000;
	    
	    //tiempo actual.
	    var currentTime = 0;
	    
	    //arranca la aplicación
	    $(function() {
	        Timer1.Timer = $.timer(updateTimer, incrementTime, true);  
	    });
	    
	    //formato del tiempo en 00:00:00
	    function formatTime(time) {
	        time = time / 10;
	        var hour = parseInt(time / 360000),
	        	min = parseInt(time / 6000),
	            sec = parseInt(time / 100) - (min * 60),
	            hundredths = pad(time - (sec * 100) - (min * 6000), 2);
	        return (hour > 0 ? pad(hour, 2) : "00") + ":" + (min > 0 ? pad(min, 2) : "00") + ":" + pad(sec, 2); 
	    }
	    
	
	    //tiempo e incremento
	    function updateTimer() {
	        var timeString = formatTime(currentTime);
	        document.getElementById("stopwatch").innerHTML=timeString;
	        currentTime += incrementTime;
	        
	    }
	
	    //resetea el timer.
	    this.resetStopwatch = function() {
	        currentTime = 0;
	        Timer1.Timer.stop().once();
	    };
	
	});
	
	this.Clock = Timer1;
}

var timer = new TimerStructure();