'use strict';

describe('Controller Tests', function() {

    describe('FixtureResult Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFixtureResult, MockGame;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFixtureResult = jasmine.createSpy('MockFixtureResult');
            MockGame = jasmine.createSpy('MockGame');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FixtureResult': MockFixtureResult,
                'Game': MockGame
            };
            createController = function() {
                $injector.get('$controller')("FixtureResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:fixtureResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
