(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TeseeestDetailController', TeseeestDetailController);

    TeseeestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Teseeest'];

    function TeseeestDetailController($scope, $rootScope, $stateParams, entity, Teseeest) {
        var vm = this;
        vm.teseeest = entity;
        
        var unsubscribe = $rootScope.$on('test2App:teseeestUpdate', function(event, result) {
            vm.teseeest = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
