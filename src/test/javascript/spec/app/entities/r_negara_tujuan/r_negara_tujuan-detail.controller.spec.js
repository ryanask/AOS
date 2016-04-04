'use strict';

describe('Controller Tests', function() {

    describe('R_negara_tujuan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_negara_tujuan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_negara_tujuan = jasmine.createSpy('MockR_negara_tujuan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_negara_tujuan': MockR_negara_tujuan
            };
            createController = function() {
                $injector.get('$controller')("R_negara_tujuanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_negara_tujuanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
