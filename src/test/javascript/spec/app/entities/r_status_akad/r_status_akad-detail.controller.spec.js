'use strict';

describe('Controller Tests', function() {

    describe('R_status_akad Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_status_akad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_status_akad = jasmine.createSpy('MockR_status_akad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_status_akad': MockR_status_akad
            };
            createController = function() {
                $injector.get('$controller')("R_status_akadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_status_akadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
