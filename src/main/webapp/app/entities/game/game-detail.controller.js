(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('GameDetailController', GameDetailController);

    GameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Game', 'Team', 'FixtureResult', 'Tournament'];

    function GameDetailController($scope, $rootScope, $stateParams, entity, Game, Team, FixtureResult, Tournament) {
        var vm = this;
        vm.game = entity;
        
        var unsubscribe = $rootScope.$on('test2App:gameUpdate', function(event, result) {
            vm.game = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
