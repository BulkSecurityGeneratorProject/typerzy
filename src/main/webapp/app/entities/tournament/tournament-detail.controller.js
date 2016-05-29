(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TournamentDetailController', TournamentDetailController);

    TournamentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tournament', 'User', 'Game'];

    function TournamentDetailController($scope, $rootScope, $stateParams, entity, Tournament, User, Game) {
        var vm = this;
        vm.tournament = entity;
        vm.games = Game.all.query();
        
        var unsubscribe = $rootScope.$on('test2App:tournamentUpdate', function(event, result) {
            vm.tournament = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
