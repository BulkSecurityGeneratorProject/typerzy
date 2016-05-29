(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('FixtureResultDeleteController',FixtureResultDeleteController);

    FixtureResultDeleteController.$inject = ['$uibModalInstance', 'entity', 'FixtureResult'];

    function FixtureResultDeleteController($uibModalInstance, entity, FixtureResult) {
        var vm = this;
        vm.fixtureResult = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            FixtureResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
