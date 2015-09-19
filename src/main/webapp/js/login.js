var loginServices = angular.module('loginServices', ['ngResource']);

loginServices.factory('LoginUser', [ '$resource', function($resource) {
	return $resource('/login');
} ]);


var app = angular.module('app', ['ngRoute', 'loginServices']);

app.controller('LoginController', [ '$scope', '$window', 'LoginUser', function($scope, $window, LoginUser) {
	$scope.user = {};
	$scope.status = {};
	
	$scope.login = function() {
		if ($scope.form.$valid) {
			var loginUser = new LoginUser($scope.user);
			loginUser.$save(function(status){
				$scope.status = status;
				if (!status.error && !!status.redirectUrl) {
					$window.location.href = status.redirectUrl;
				}
			});
		}
	};

} ]);


