var websiteAddServices = angular.module('websiteAddServices', ['ngResource']);

websiteAddServices.factory('CheckDomain', [ '$resource', function($resource) {
	return $resource('/check-domain/:domain');
} ]);

websiteAddServices.factory('Website', [ '$resource', function($resource) {
	return $resource('/add-website');
} ]);

var app = angular.module('app', ['ngRoute', 'websiteAddServices']);

app.controller('WebsiteAddController', [ '$scope', '$window', 'Website', function($scope, $window, Website) {

	$scope.website = {};
	
	$scope.addWebsite = function() {
		if ($scope.form.$valid) {
			var newWebsite = new Website($scope.website);
			newWebsite.$save(function(status){
				if (!status.error && !!status.redirectUrl) {
					$window.location.href = status.redirectUrl;
				}
			});
		}
	};

} ]);

app.directive('validdomain', function() {
	  return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ctrl) {
	      ctrl.$validators.validdomain = function(modelValue, viewValue) {
	        if (ctrl.$isEmpty(modelValue)) {
	          // consider empty models to be valid
	          return true;
	        }
	        // alphanumeric with hyphens
	        return /^[a-z0-9]+[a-z0-9-]+[a-z0-9]+$/i.test(modelValue);
	      };
	    }
	  };
	});

app.directive('uniquedomain',
		['CheckDomain', '$q', function(CheckDomain, $q) {
			return {
				require : 'ngModel',
				link : function(scope, elm, attrs, ctrl) {
					ctrl.$asyncValidators.uniquedomain = function(modelValue, viewValue) {
						if (ctrl.$isEmpty(modelValue)) {
							// consider empty model valid
							return $q.when();
						}
						var def = $q.defer();
						CheckDomain.get({domain : modelValue}, function(status) {
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

