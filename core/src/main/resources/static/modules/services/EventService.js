services
.service ('EventService', function($rootScope) {

    this.emitGoto = function(state) {
        $rootScope.$broadcast(events.goto.name, {
            state: state
        });
    }
});