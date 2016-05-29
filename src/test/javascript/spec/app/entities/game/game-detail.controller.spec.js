'use strict';

describe('Controller Tests', function() {

    describe('Game Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGame, MockTeam, MockFixtureResult, MockTournament;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGame = jasmine.createSpy('MockGame');
            MockTeam = jasmine.createSpy('MockTeam');
            MockFixtureResult = jasmine.createSpy('MockFixtureResult');
            MockTournament = jasmine.createSpy('MockTournament');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Game': MockGame,
                'Team': MockTeam,
                'FixtureResult': MockFixtureResult,
                'Tournament': MockTournament
            };
            createController = function() {
                $injector.get('$controller')("GameDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:gameUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
