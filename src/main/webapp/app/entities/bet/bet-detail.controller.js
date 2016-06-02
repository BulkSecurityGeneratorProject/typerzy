(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('BetDetailController', BetDetailController);

    BetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Bet', 'User'];

    function BetDetailController($scope, $rootScope, $stateParams, entity, Bet, User) {
        var vm = this;

        vm.bet = entity;

        var unsubscribe = $rootScope.$on('test2App:betUpdate', function(event, result) {
            vm.bet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
