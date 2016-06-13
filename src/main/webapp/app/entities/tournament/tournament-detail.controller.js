(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TournamentDetailController', TournamentDetailController);

    TournamentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tournament', 'User', 'Game', 'Bet', 'Standing'];

    function TournamentDetailController($scope, $rootScope, $stateParams, entity, Tournament, User, Game, Bet, Standing) {
        var vm = this;
        vm.tournament = entity;
        vm.fixtures = Game.fixtures.query({id:$stateParams.id});
        vm.standings = Standing.query({id:$stateParams.id});
        var unsubscribe = $rootScope.$on('test2App:tournamentUpdate', function(event, result) {
            vm.tournament = result;
        });
        
        function getBets (game) {
        	
        	return Bet.games.get(game);
        }
        
        
        
        function isNotStarted(game)
        {
        	return new Date(game.time) > new Date();
        }
        
        $scope.isNotStarted = isNotStarted;
        $scope.getBets = getBets;
        
        
        $scope.$on('$destroy', unsubscribe);

    }
})();
