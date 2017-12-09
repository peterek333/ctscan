angular.module('ctscan', ['ctscan.controllers', 'ctscan.services', 'ui.router', 'angular-storage'])
.config(function($stateProvider, $urlRouterProvider, $httpProvider) {

    $stateProvider
        .state('login', {
            url: '/login',
            templateUrl: 'modules/user/login/loginView.html',
            controller: 'LoginCtrl',
            data: {
                bodyClasses: 'login-background'
            }
        })
        .state('signup', {
            url: '/signup',
            templateUrl: 'modules/user/signup/signupView.html',
            controller: 'SignupCtrl',
            data: {
                bodyClasses: 'login-background'
            }
        })
        .state('main', {
            url: '/',
            templateUrl: 'modules/main/mainView.html',
            controller: 'MainCtrl'
        })
        .state('main.dashboard', {
            url: '/dashboard',
            templateUrl: 'modules/main/dashboard/dashboardView.html',
            controller: 'DashboardCtrl'
        })
    ;
// .state('main', {
//         url: '/',
//         views: {
//             'main': {
//                 controller: 'MainCtrl'
//             }
//         }
//     })
//         .state('main.login', {
//             url: 'login',
//             templateUrl: 'modules/user/login/loginView.html',
//             controller: "LoginCtrl",
//             data: {
//                 bodyClasses: 'login-background'
//             }
//         })
//         .state('main.signup', {
//             url: 'signup',
//             templateUrl: 'modules/user/signup/signupView.html',
//             controller: "SignupCtrl"
//         });


    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    $urlRouterProvider.otherwise('/login');

    $httpProvider.interceptors.push('APIInterceptor');
})
    .controller('AppCtrl', function($scope, $state, $rootScope, UserService) {
        var DEFAULT_BODY_CLASS = '';
        $scope.name = 'Peter';
        $scope.bodyClasses = angular.copy(DEFAULT_BODY_CLASS);

        $scope.goto = function(stateName) {
            switch(stateName) {
                case 'SIGNUP':
                    $state.go('signup');
                    break;
                case 'MAIN':
                    $state.go('main');
                    break;
                case 'DASHBOARD':
                    $state.go('main.dashboard');
                    break;
            }
        };

        $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
            if(angular.isDefined(toState.data) && angular.isDefined(toState.data.bodyClasses)) {
                $scope.bodyClasses = toState.data.bodyClasses;
            } else {
                $scope.bodyClasses = angular.copy(DEFAULT_BODY_CLASS);
            }
        });

        $rootScope.isLoggedIn = function() {
            console.log('sprawdzam');
            return UserService.getCurrentUser() !== null;
        };
    });

var controllers = angular.module('ctscan.controllers', []);
var services = angular.module('ctscan.services', []);