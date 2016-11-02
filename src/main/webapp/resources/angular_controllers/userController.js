angular.module('hicollege_webapp').controller('user_ctrl', function ($scope, $http, $log, albumService) {
	
	$scope.displaySuccess = false;
	$scope.displayFail = false;
	$scope.albumService = albumService;
	
	$scope.addUser = function () {
    	$http({
        	method: 'PUT', 
        	url: '/add/user',
            params: {
            	username: $scope.username,
            	email: $scope.email,
            	age: $scope.age,
            	albums: $scope.user_albums
            }
    	}).
        success(function (data, status, headers, config) {
        	$scope.status = data;
        	if(data.statusCode == 'OK'){
        		$scope.displaySuccess = true;
        		$scope.displayFail = false;
        	} else {
        		$scope.displaySuccess = false;
        		$scope.displayFail = true;
        	}
        	
        }).
        error(function (data, status, headers, config) {
        	$log.error(status);
        });
	};
	
	$scope.deleteUser = function (name) {
    	$http({
        	method: 'DELETE', 
        	url: '/delete/users/' + name
    	}).
        success(function (data, status, headers, config) {
        	$scope.status = data;
        	if(data.statusCode == 'OK'){
        		$scope.displaySuccess = true;
        		$scope.displayFail = false;
        	} else {
        		$scope.displaySuccess = false;
        		$scope.displayFail = true;
        	}
        }).
        error(function (data, status, headers, config) {
            $log.error(status);
        });
	};
	
	$scope.getAllUsers = function () {
    	$http({
        	method: 'GET', 
        	url: '/find/users'
    	}).
        success(function (data, status, headers, config) {
       		$scope.users = data;
        }).
        error(function (data, status, headers, config) {
            $log.error(status);
        });
	};
});