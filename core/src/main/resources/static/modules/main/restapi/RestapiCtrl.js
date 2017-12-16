controllers
.controller('RestapiCtrl', function($scope, RestService) {

    $scope.uploadFile = function() {
        var file = $scope.aimFile;


        console.log('file is ' );
        console.dir(file);

        var formData = new FormData();
        formData.append('file', file);
        formData.append('id', '19359124keworiewr');

        RestService.uploadAimFile(formData)
            .then(function(response) {
                console.log('przeslany plik', response);

            }).catch(function(response) {
                console.log('nieudane', response);
            });

    }

});