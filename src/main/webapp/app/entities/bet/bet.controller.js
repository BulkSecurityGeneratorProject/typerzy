(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('BetController', BetController);

    BetController.$inject = ['$scope', '$state', 'Bet'];

    function BetController ($scope, $state, Bet) {
        var vm = this;
        
        vm.bets = [];

        loadAll();

        function loadAll() {
            Bet.query(function(result) {
                vm.bets = result;
            });
        }
    }
})();
