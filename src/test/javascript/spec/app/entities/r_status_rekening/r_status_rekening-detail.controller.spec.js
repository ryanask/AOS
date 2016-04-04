'use strict';

describe('Controller Tests', function() {

    describe('R_status_rekening Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_status_rekening;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_status_rekening = jasmine.createSpy('MockR_status_rekening');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_status_rekening': MockR_status_rekening
            };
            createController = function() {
                $injector.get('$controller')("R_status_rekeningDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_status_rekeningUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
