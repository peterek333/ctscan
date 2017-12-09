angular.module('ctscan', ['ctscan.controllers', 'ctscan.services', 'ui.router', 'angular-storage'])
.config(function($stateProvider, $urlRouterProvider, $httpProvider) {

    $stateProvider
        .state('main', {
            url: '/',
            views: {
                'main': {
                    controller: 'MainCtrl'
                }
            }
        })
        .state('main.login', {
            url: 'login',
            templateUrl: 'modules/user/login/loginView.html',
            controller: "LoginCtrl"
        })
        .state('main.signup', {
            url: 'signup',
            templateUrl: 'modules/user/signup/signupView.html',
            controller: "SignupCtrl"
        });


    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('APIInterceptor');
})
    .controller('MainCtrl', function($scope, $state) {
        $scope.name = 'Peter';

        $scope.goto = function(stateName) {
            switch(stateName) {
                case 'SIGNUP':
                    $state.go('main.signup');
                    break;
            }
        }
    });

var controllers = angular.module('ctscan.controllers', []);
var services = angular.module('ctscan.services', []);