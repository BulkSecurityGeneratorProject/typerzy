'use strict';

describe('Controller Tests', function() {

    describe('Teseeest Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTeseeest;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTeseeest = jasmine.createSpy('MockTeseeest');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Teseeest': MockTeseeest
            };
            createController = function() {
                $injector.get('$controller')("TeseeestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'test2App:teseeestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
