'use strict';

describe('Controller Tests', function() {

    describe('Result Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResult, MockGame;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResult = jasmine.createSpy('MockResult');
            MockGame = jasmine.createSpy('MockGame');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Result': MockResult,
                'Game': MockGame
            };
            createController = function() {
                $injector.get('$controller')("ResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:resultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
