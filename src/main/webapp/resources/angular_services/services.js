angular.module('hicollege_webapp').factory('albumService', function () {
	return {
		test : function(albums) {
			console.log(albums)
			var commaString = "";
		
			for(i = 0; i < albums.length; i++) {
				album = albums[i];
				commaString = commaString + album.title + ", ";
			}
			return commaString.slice(0, -2);
			}
	}
});