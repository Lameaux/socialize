var registerServices = angular.module('registerServices', ['ngResource']);

registerServices.factory('CheckEmail', [ '$resource', function($resource) {
	return $resource('/register/check-email/:email');
} ]);

registerServices.factory('CheckLogin', [ '$resource', function($resource) {
	return $resource('/register/check-login/:login');
} ]);

registerServices.factory('RegisterUser', [ '$resource', function($resource) {
	return $resource('/register/user');
} ]);


var app = angular.module('app', ['ngRoute', 'registerServices']);

app.controller('RegisterController', [ '$scope', 'RegisterUser', function($scope, RegisterUser) {
	$scope.user = {};

	$scope.register = function() {
		
		if ($scope.form.$valid) {
			var registerUser = new RegisterUser($scope.user);
			registerUser.$save();
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
						CheckEmail.get({email : modelValue}, function(data) {
							if (data.exists) {
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

app.directive('uniquelogin',
		['CheckLogin', '$q', function(CheckLogin, $q) {
			return {
				require : 'ngModel',
				link : function(scope, elm, attrs, ctrl) {
					ctrl.$asyncValidators.uniquelogin = function(modelValue, viewValue) {
						if (ctrl.$isEmpty(modelValue)) {
							// consider empty model valid
							return $q.when();
						}
						var def = $q.defer();
						CheckLogin.get({login : modelValue}, function(data) {
							if (data.exists) {
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