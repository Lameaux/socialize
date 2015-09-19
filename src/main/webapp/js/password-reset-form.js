var passwordResetFormServices = angular.module('passwordResetFormServices', ['ngResource']);

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

passwordResetFormServices.factory('PasswordReset', [ '$resource', function($resource) {
	return $resource('/password-reset/:uuid', {uuid : '@uuid'});
} ]);


var app = angular.module('app', ['ngRoute', 'passwordResetFormServices']);

app.controller('PasswordResetFormController', [ '$scope', '$window', 'PasswordReset', function($scope, $window, PasswordReset) {
	$scope.user = {};
	$scope.done = false;
	
	$scope.resetPassword = function() {
		if (!$scope.done && $scope.form.$valid) {
			var passwordReset = new PasswordReset($scope.user);
			passwordReset.$save(function(status){
				if (!status.error) {
					$scope.done = true;
				}
			});
		}
	};

} ]);

app.directive('repeatpassword', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ctrl) {
	      ctrl.$validators.repeatpassword = function(modelValue, viewValue) {
	        if (ctrl.$isEmpty(modelValue)) {
	          // consider empty models to be valid
	          return true;
	        }
	        // password should be the same
	        return scope.user.password == modelValue;
	      };
	    }
	  };
	});

