/******************************************************************/
/* GeoLocation Phonegap	  										  */ 	
/******************************************************************/

function onLocationError(e) {
     //console.log("Geolocation error: #" + e.code + "\n" + e.message);
	document.getElementById("locationInfo").innerHTML = '<b>Error</b><hr /><b>Codigo Error</b> - ' + e.code + '<br /><b>Msg Error</b> - ' + e.message + '<br />';
}

function onLocationSuccess(loc) {
     console.log("onLocationSuccess");
     var d = new Date(loc.timestamp);
     document.getElementById("locationInfo").innerHTML = '<b>Current Location</b><hr /><b>Latitude</b>: ' + loc.coords.latitude + '<br /><b>Longitude</b>: ' + loc.coords.longitude + '<br /><b>Altitude</b>: ' + loc.coords.altitude + '<br /><b>Accuracy</b>: ' + loc.coords.accuracy + '<br /><b>Altitude Accuracy</b>: ' + loc.coords.altitudeAccuracy + '<br /><b>Heading</b>: ' + loc.coords.heading + '<br /><b>Speed</b>: ' + loc.coords.speed + '<br /><b>Timestamp</b>: ' + d.toLocaleString();
     document.getElementById("delSit").value = "lat - " + loc.coords.latitude + " long - " + loc.coords.longitude;
}

function getLocation() {

  var locOptions = {
      timeout : 5000,
      enableHighAccuracy : true
  };
  
  navigator.geolocation.getCurrentPosition(onLocationSuccess, onLocationError, locOptions);  
}

