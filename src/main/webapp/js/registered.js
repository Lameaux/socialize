var registeredServices = angular.module('registeredServices', ['ngResource']);

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

registeredServices.factory('CheckUser', [ '$resource', function($resource) {
	return $resource('/check-user/:id');
} ]);


var app = angular.module('app', ['ngRoute', 'registeredServices']);

app.controller('RegisteredController', [ '$scope', '$window', '$interval', 'CheckUser', function($scope, $window, $interval, CheckUser) {
	
	var checkUserInterval;
	var userId = getParameterByName('user');
	if (!!userId) {
		checkUserInterval = $interval(function(){
			CheckUser.get({id : userId}, function(user) {
				if (user.active) {
					$interval.cancel(checkUserInterval);
					$window.location.reload();
				} 
			});
		}, 10000, 6 * 15);
	}
	
} ]);


