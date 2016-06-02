(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('FixtureResultDialogController', FixtureResultDialogController);

    FixtureResultDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FixtureResult', 'Game'];

    function FixtureResultDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FixtureResult, Game) {
        var vm = this;
        vm.fixtureResult = entity;
        vm.games = Game.games.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('test2App:fixtureResultUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.fixtureResult.id !== null) {
                FixtureResult.update(vm.fixtureResult, onSaveSuccess, onSaveError);
            } else {
                FixtureResult.save(vm.fixtureResult, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
