var prefix = '/admin';
var states = {
    login : { state: 'login', url: prefix + '/login' },
    signup : { state: 'signup', url: prefix + '/signup' },
    dashboard : { state: 'main.dashboard', url: prefix + '/dashboard' },
    restapi : { state: 'main.restapi', url: prefix + '/restapi' }
};

angular.module('ctscan', ['ctscan.controllers', 'ctscan.services', 'ctscan.components',
    'ui.router', 'angular-storage', 'ngAnimate', 'ngNotify'])
.config(function($stateProvider, $urlRouterProvider, $httpProvider) {

    $stateProvider
        .state('login', {
            url: prefix + '/login',
            templateUrl: 'modules/user/login/loginView.html',
            controller: 'LoginCtrl',
            data: {
                bodyClasses: 'login-background'
            }
        })
        .state('signup', {
            url: prefix + '/signup',
            templateUrl: 'modules/user/signup/signupView.html',
            controller: 'SignupCtrl',
            data: {
                bodyClasses: 'login-background'
            }
        })
        .state('main', {
            templateUrl: 'modules/main/mainView.html',
            controller: 'MainCtrl'
        })
        .state('main.dashboard', {
            url: prefix + '/dashboard',
            templateUrl: 'modules/main/dashboard/dashboardView.html',
            controller: 'DashboardCtrl'
        })
        .state(states.restapi.state, {
            url: states.restapi.url,
            templateUrl: 'modules/main/restapi/restapiView.html',
            controller: 'RestapiCtrl'
        })
    ;

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    $urlRouterProvider.otherwise(prefix + '/login');
})
.factory('authHttpResponseInterceptor', function ($q, $location, $injector, $timeout) {
    return {
        response: function (response) {
            if (response.status === 401) {
                console.log("Response 401");
            }
            if (response.status === 403) {
                console.log("Response 403", response);

                $injector.get('$state').transitionTo('login');
            }
            return response || $q.when(response);
        },
        responseError: function (rejection) {
            if (rejection.status === 401) {
                console.log("Response Error 401", rejection);

                $injector.get('$state').transitionTo('login');
            }
            if (rejection.status === 403) {
                console.log("Response Error 403", rejection);

                $injector.get('$state').transitionTo('login');
            }
            return $q.reject(rejection);
        }
    }
})
.config(function ($httpProvider) {
    $httpProvider.interceptors.push('authHttpResponseInterceptor');

    $httpProvider.interceptors.push('APIInterceptor');
})
.run(function(ngNotify) {
    ngNotify.config({
        position: 'top'
    })
})
.controller('AppCtrl', function($scope, $state, $rootScope, UserService) {
    var DEFAULT_BODY_CLASS = 'default-body-background';
    $scope.name = 'Peter';
    $scope.bodyClasses = angular.copy(DEFAULT_BODY_CLASS);

    $scope.$on(events.goto.name, function(event, args) {
        $scope.goto(args.state);
    });

    $scope.goto = function(state) {
        console.log('go to', state);
        $state.go(state);
    };

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
        console.log('statechangesuccess');
        if(angular.isDefined(toState.data) && angular.isDefined(toState.data.bodyClasses)) {
            $scope.bodyClasses = toState.data.bodyClasses;
        } else {
            $scope.bodyClasses = angular.copy(DEFAULT_BODY_CLASS);
        }
    });

    $rootScope.isLoggedIn = function() {
        return UserService.isLoggedIn();
    };
});

var controllers = angular.module('ctscan.controllers', []);
var services = angular.module('ctscan.services', []);
var components = angular.module('ctscan.components', []);