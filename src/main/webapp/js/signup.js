var signupServices = angular.module('signupServices', ['ngResource']);

signupServices.factory('CheckEmail', [ '$resource', function($resource) {
	return $resource('/check-email/:email');
} ]);

signupServices.factory('Signup', [ '$resource', function($resource) {
	return $resource('/signup');
} ]);


var app = angular.module('app', ['ngRoute', 'signupServices']);

app.controller('SignupController', [ '$scope', '$window', 'Signup', function($scope, $window, Signup) {
	$scope.user = {};
	
	$scope.register = function() {
		if ($scope.form.$valid) {
			var signup = new Signup($scope.user);
			signup.$save(function(status){
				if (!status.error && !!status.redirectUrl) {
					$window.location.href = status.redirectUrl;
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

app.directive('uniqueemail',
		['CheckEmail', '$q', function(CheckEmail, $q) {
			return {
				require : 'ngModel',
				link : function(scope, elm, attrs, ctrl) {
					ctrl.$asyncValidators.uniqueemail = function(modelValue, viewValue) {
						if (ctrl.$isEmpty(modelValue)) {
							// consider empty model valid
							return $q.when();
						}
						var def = $q.defer();
						CheckEmail.get({email : modelValue}, function(status) {
							if (status.exists) {
								def.reject();	
							} else {
								def.resolve();
							}
						}, function(){
							def.resolve();
						});
						return def.promise;
					};
				}
			};
		}]);

