controllers
.controller('LoginCtrl', function($scope, $http, UserService, RestService, ngNotify) {

    $scope.username = "test@test.com";
    $scope.password = "test";
    $scope.signupMode = false;

    $scope.login = function() {
        var loginCredentials = {
            email: $scope.username,
            password: $scope.password
        };

        RestService.login(loginCredentials)
            .then(function(response) {
                if(response.status === 200) {
                    var user = {};
                    user.access_token = response.headers('authorization');

                    UserService.setCurrentUser(user);

                    location.reload();
                    $scope.goto('DASHBOARD');
                } else {
                    var type = 'info';
                    if(response.status === 401) {
                        type = 'error';
                    }
                    ngNotify.set(response.data.message, {
                        type: type
                    });
                }
            }).catch(function(response) {
                console.log('nieudane', response);
            });
    };

    $scope.register = function() {
        if(checkPasswords()) {
            var signupData = {
                email: $scope.username,
                password: $scope.password
            };

            RestService.register(signupData)
                .then(function(response) {
                    if(response.status === 200) {
                        $scope.signupMode = false;

                        ngNotify.set('You have successfully signed up', {
                            type: 'success'
                        });
                    } else {
                        ngNotify.set(response.data.message);
                    }
                }, function(response) {
                    console.log('nieudane', response);
                });
        } else {
            ngNotify.set('')
        }
    };

    function checkPasswords() {
        return $scope.repassword && $scope.password === $scope.repassword;
    }

    $scope.setSignupMode = function(state) {
        $scope.signupMode = state;
    }
});