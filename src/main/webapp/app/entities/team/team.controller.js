(function() {
    'use strict';

    angular
        .module('test2App')
        .controller('TeamController', TeamController);

    TeamController.$inject = ['$scope', '$state', 'Team', 'ParseLinks', 'AlertService'];

    function TeamController ($scope, $state, Team, ParseLinks, AlertService) {
        var vm = this;
        vm.teams =  Team.query();

    }
})();
