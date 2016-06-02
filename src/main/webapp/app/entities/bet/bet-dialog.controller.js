(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('BetDialogController', BetDialogController);

    BetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Bet', 'User', 'Game'];

    function BetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Bet, User, Game) {
        var vm = this;

        vm.bet = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.games = Game.games.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bet.id !== null) {
                Bet.update(vm.bet, onSaveSuccess, onSaveError);
            } else {
                Bet.save(vm.bet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('test2App:betUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
