controllers
.controller('LoginCtrl', function($scope, $http, UserService) {

    $scope.username = "test@test.com";
    $scope.password = "test";
    $scope.signupMode = false;

    $scope.login = function() {
        var json = {
            email: $scope.username,
            password: $scope.password
        };

        $http.post('/login', json)
            .then(function(response) {
                var user = {};
                user.access_token = response.headers('authorization');

                UserService.setCurrentUser(user);
                location.reload();
            }).catch(function(response) {
                console.log('nieudane', response);
            });
    };

    $scope.setSignupMode = function(state) {
        $scope.signupMode = state;
    }
});