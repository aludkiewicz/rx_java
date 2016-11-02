angular.module('hicollege_webapp').controller('album_ctrl', function ($scope, $http, $log, albumService) {
	
	$scope.displaySuccess = false;
	$scope.displayFail = false;
	
	$scope.addAlbum = function () {
    	$http({
        	method: 'PUT', 
        	url: '/add/album',
            params: {
            	title: $scope.albumTitle,
            	songs: $scope.albumSongs,
            	artists: $scope.albumArtists
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
	
	
	$scope.getAllAlbums = function () {
    	$http({
        	method: 'GET', 
        	url: '/find/albums'
    	}).
        success(function (data, status, headers, config) {
        	$scope.albums = data;
        }).
        error(function (data, status, headers, config) {
            $log.error(status);
        });
	};
	
	$scope.deleteAlbum = function (title) {
    	$http({
        	method: 'DELETE', 
        	url: '/delete/albums/' + title
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
});