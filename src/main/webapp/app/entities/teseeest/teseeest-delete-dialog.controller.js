(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TeseeestDeleteController',TeseeestDeleteController);

    TeseeestDeleteController.$inject = ['$uibModalInstance', 'entity', 'Teseeest'];

    function TeseeestDeleteController($uibModalInstance, entity, Teseeest) {
        var vm = this;
        vm.teseeest = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Teseeest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
