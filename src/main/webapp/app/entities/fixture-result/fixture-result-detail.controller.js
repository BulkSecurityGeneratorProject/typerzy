(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('FixtureResultDetailController', FixtureResultDetailController);

    FixtureResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FixtureResult', 'Game'];

    function FixtureResultDetailController($scope, $rootScope, $stateParams, entity, FixtureResult, Game) {
        var vm = this;
        vm.fixtureResult = entity;
        
        var unsubscribe = $rootScope.$on('test2App:fixtureResultUpdate', function(event, result) {
            vm.fixtureResult = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
