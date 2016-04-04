'use strict';

describe('Controller Tests', function() {

    describe('R_kode_kolektibilitas Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_kode_kolektibilitas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_kode_kolektibilitas = jasmine.createSpy('MockR_kode_kolektibilitas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_kode_kolektibilitas': MockR_kode_kolektibilitas
            };
            createController = function() {
                $injector.get('$controller')("R_kode_kolektibilitasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_kode_kolektibilitasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
