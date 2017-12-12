components
.component ('headerComponent', {
    templateUrl: 'modules/components/header/header.tpl.html',
    controller: function($scope, UserService) {
        $scope.isLogged = UserService.isLoggedIn();
    }
});