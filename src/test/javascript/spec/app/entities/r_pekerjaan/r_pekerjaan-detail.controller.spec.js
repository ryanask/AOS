'use strict';

describe('Controller Tests', function() {

    describe('R_pekerjaan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_pekerjaan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_pekerjaan = jasmine.createSpy('MockR_pekerjaan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_pekerjaan': MockR_pekerjaan
            };
            createController = function() {
                $injector.get('$controller')("R_pekerjaanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_pekerjaanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
