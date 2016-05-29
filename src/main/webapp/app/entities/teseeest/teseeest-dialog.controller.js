(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TeseeestDialogController', TeseeestDialogController);

    TeseeestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Teseeest'];

    function TeseeestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Teseeest) {
        var vm = this;
        vm.teseeest = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('test2App:teseeestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.teseeest.id !== null) {
                Teseeest.update(vm.teseeest, onSaveSuccess, onSaveError);
            } else {
                Teseeest.save(vm.teseeest, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
