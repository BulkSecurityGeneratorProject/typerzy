'use strict';

describe('Controller Tests', function() {

    describe('GameResult Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGameResult;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGameResult = jasmine.createSpy('MockGameResult');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'GameResult': MockGameResult
            };
            createController = function() {
                $injector.get('$controller')("GameResultDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:gameResultUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
