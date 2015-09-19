var passwordServices = angular.module('passwordServices', ['ngResource']);

passwordServices.factory('PasswordReset', [ '$resource', function($resource) {
	return $resource('/password-reset');
} ]);


var app = angular.module('app', ['ngRoute', 'passwordServices']);

app.controller('PasswordResetController', [ '$scope', '$window', 'PasswordReset', function($scope, $window, PasswordReset) {
	$scope.user = {};
	$scope.done = false;
	
	$scope.resetPassword = function() {
		if (!$scope.done && $scope.form.$valid) {
			var passwordReset = new PasswordReset($scope.user);
			passwordReset.$save(function(status){
				$scope.done = true;
			});
		}
	};

} ]);


