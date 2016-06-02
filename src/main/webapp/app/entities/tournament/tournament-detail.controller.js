(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TournamentDetailController', TournamentDetailController);

    TournamentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tournament', 'User', 'Game'];

    function TournamentDetailController($scope, $rootScope, $stateParams, entity, Tournament, User, Game) {
        var vm = this;
        vm.tournament = entity;
        vm.fixtures = Game.fixtures.query({id:$stateParams.id});
        var unsubscribe = $rootScope.$on('test2App:tournamentUpdate', function(event, result) {
            vm.tournament = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
