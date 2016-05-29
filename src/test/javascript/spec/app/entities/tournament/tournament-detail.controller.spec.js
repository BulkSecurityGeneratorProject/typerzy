'use strict';

describe('Controller Tests', function() {

    describe('Tournament Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTournament, MockUser, MockGame;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTournament = jasmine.createSpy('MockTournament');
            MockUser = jasmine.createSpy('MockUser');
            MockGame = jasmine.createSpy('MockGame');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tournament': MockTournament,
                'User': MockUser,
                'Game': MockGame
            };
            createController = function() {
                $injector.get('$controller')("TournamentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:tournamentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
