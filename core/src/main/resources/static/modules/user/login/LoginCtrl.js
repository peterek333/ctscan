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
                console.log('odp', response);
                if(response.status === 200) {
                    var user = {};
                    user.access_token = response.headers('authorization');

                    UserService.setCurrentUser(user);

                    $scope.goto('DASHBOARD');
                } else {
                    ngNotify.set(response.data.message);
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
                    console.log('udane', response);
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
        }
    };

    $scope.getType = function() {
        return $scope.signupMode ? 'submit' : '';
    };

    function checkPasswords() {
        return $scope.repassword && $scope.password === $scope.repassword;
    }

    $scope.setSignupMode = function(state) {
        $scope.signupMode = state;
    }
});