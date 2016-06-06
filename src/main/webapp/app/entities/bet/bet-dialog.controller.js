(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('BetDialogController', BetDialogController);

    BetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Bet', 'User', 'Game', 'FixtureResult'];

    function BetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Bet, User, Game, FixtureResult) {
        var vm = this;

        vm.bet = entity;
        vm.clear = clear;
        vm.save = save;
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
                Bet.crud.update(vm.bet, onSaveSuccess, onSaveError);
            } else {
                Bet.crud.save(vm.bet, onSaveSuccess, onSaveError);
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

    }
})();
