services
.service ('RestService', function($http) {
    var base = '';

    /**** USER ****/
    var user = '/user';

    this.login = function(loginCredentials) {
        return $http.post(base + 'login', loginCredentials);
    };

    this.register = function(signupData) {
        return $http.post(base + user + '/signup', signupData);
    };


    /**** UPLOAD ****/
    var upload = '/upload';

    this.uploadAimFile = function(file) {
        return $http.post(base + upload + '/aim', file, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    }

});