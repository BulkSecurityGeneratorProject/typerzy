(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TeamDetailController', TeamDetailController);

    TeamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Team'];

    function TeamDetailController($scope, $rootScope, $stateParams, entity, Team) {
        var vm = this;
        vm.team = entity;
        
        var unsubscribe = $rootScope.$on('test2App:teamUpdate', function(event, result) {
            vm.team = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
