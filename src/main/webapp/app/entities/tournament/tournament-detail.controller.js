(function() {
    'use strict';

    var app =  angular.module('test2App');
    
    app.controller('TournamentDetailController', TournamentDetailController);
    app.directive('games', function(){
    	      return {
    	        restrict: 'E',
    	        scope:{
    	            games: "="
    	        },
    	        templateUrl: 'components/tournament/fixtures.html'
    	      }
    	    });
    
    app.filter('pastFixtures', function() {
        return function(fixtures) {
        	var now = new Date();
        	now.setHours(0,0,0,0);
        	var filtered = [];
            for (var i = 0; i < fixtures.length; i++) {
              var fixture = fixtures[i];
              if (new Date(fixture.time) < now) {
                filtered.push(fixture);
              }
            }
            return filtered;
        };
    });
    
    app.filter('todaysFixtures', function() {
    	return function(fixtures) {
    		var now = new Date();
    		now.setHours(0,0,0,0);
    		var filtered = [];
    		for (var i = 0; i < fixtures.length; i++) {
    			var fixture = fixtures[i];
    			var fixtureDate = new Date(fixture.time);
    			fixtureDate.setHours(0,0,0,0);
    			if (fixtureDate.getTime() ==  now.getTime()) {
    				filtered.push(fixture);
    			}
    		}
    		return filtered;
    	};
    });
    app.filter('futureFixtures', function() {
    	return function(fixtures) {
    		var now = new Date();
    		now.setHours(0,0,0,0);
    		var filtered = [];
    		for (var i = 0; i < fixtures.length; i++) {
    			var fixture = fixtures[i];
    			var fixtureDate = new Date(fixture.time);
    			fixtureDate.setHours(0,0,0,0);
    			if (fixtureDate >  now) {
    				filtered.push(fixture);
    			}
    		}
    		return filtered;
    	};
    });
    
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
