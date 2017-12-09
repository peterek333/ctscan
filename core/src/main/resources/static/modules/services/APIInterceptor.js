services
.service('APIInterceptor', function($rootScope, UserService) {
    var service = this;

    service.request = function(config) {
        var currentUser = UserService.getCurrentUser(),
            access_token = currentUser ? currentUser.access_token : null;
        if (access_token) {
            if(isExpired(access_token)) {
                UserService.setCurrentUser(null);
                location.reload();
            } else {
                config.headers.authorization = access_token;
            }
        }
        return config;
    };

    function isExpired(token) {
        return (new Date().getTime() / 1000) > jwt_decode(token).exp;
    }

    service.responseError = function(response) {
        if (response.status === 401) {
            $rootScope.$broadcast('unauthorized');
        }
        return response;
    };
});