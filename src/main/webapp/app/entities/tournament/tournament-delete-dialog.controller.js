(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TournamentDeleteController',TournamentDeleteController);

    TournamentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tournament'];

    function TournamentDeleteController($uibModalInstance, entity, Tournament) {
        var vm = this;
        vm.tournament = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tournament.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
