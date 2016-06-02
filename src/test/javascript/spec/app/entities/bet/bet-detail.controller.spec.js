'use strict';

describe('Controller Tests', function() {

    describe('Bet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBet, MockUser, MockGame;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBet = jasmine.createSpy('MockBet');
            MockUser = jasmine.createSpy('MockUser');
            MockGame = jasmine.createSpy('MockGame');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Bet': MockBet,
                'User': MockUser,
                'Game': MockGame
            };
            createController = function() {
                $injector.get('$controller')("BetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:betUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
