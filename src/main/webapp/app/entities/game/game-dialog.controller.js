(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('GameDialogController', GameDialogController);

    GameDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Game', 'Team', 'FixtureResult', 'Tournament'];

    function GameDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Game, Team, FixtureResult, Tournament) {
        var vm = this;
        vm.game = entity;
        vm.teams = Team.query();
        vm.results = FixtureResult.query({filter: 'game-is-null'});
        $q.all([vm.game.$promise, vm.results.$promise]).then(function() {
            if (!vm.game.result || !vm.game.result.id) {
                return $q.reject();
            }
            return FixtureResult.get({id : vm.game.result.id}).$promise;
        }).then(function(result) {
            vm.results.push(result);
        });
        vm.tournaments = Tournament.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('test2App:gameUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.game.id !== null) {
                Game.update(vm.game, onSaveSuccess, onSaveError);
            } else {
                Game.save(vm.game, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.time = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
