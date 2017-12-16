var events = {
    goto : { name: 'goto-event' }
};

components
.component ('menuComponent', {
    templateUrl: 'modules/components/menu/menu.tpl.html',
    controller: function($scope, DataService, EventService) {
        $scope.menu = DataService.menu;

        $scope.gotoEvent = function(state) {
            EventService.emitGoto(state);
        }
    }
});