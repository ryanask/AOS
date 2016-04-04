'use strict';

describe('Controller Tests', function() {

    describe('R_marital_status Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_marital_status;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_marital_status = jasmine.createSpy('MockR_marital_status');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_marital_status': MockR_marital_status
            };
            createController = function() {
                $injector.get('$controller')("R_marital_statusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_marital_statusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
