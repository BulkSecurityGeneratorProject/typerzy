(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('BetDeleteController',BetDeleteController);

    BetDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bet'];

    function BetDeleteController($uibModalInstance, entity, Bet) {
        var vm = this;

        vm.bet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Bet.crud.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
