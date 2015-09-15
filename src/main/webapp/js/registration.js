var registerServices = angular.module('registerServices', ['ngResource']);

registerServices.factory('CheckEmail', ['$resource',
  function($resource){
    return $resource('/register/check-email/:email');
  }]);


var app = angular.module('app', ['ngRoute', 'registerServices']);

app.controller('RegisterController', [ '$scope', 'CheckEmail', function($scope, CheckEmail) {
	$scope.user = {};

	$scope.register = function() {
		console.log($scope.user);
	};

} ]);

app.directive('uniqueemail',
		['CheckEmail', '$q', function(CheckEmail, $q) {
			return {
				require : 'ngModel',
				link : function(scope, elm, attrs, ctrl) {

					ctrl.$asyncValidators.uniqueemail = function(modelValue,
							viewValue) {

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