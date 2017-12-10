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

});