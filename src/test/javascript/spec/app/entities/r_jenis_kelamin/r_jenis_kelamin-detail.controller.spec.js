'use strict';

describe('Controller Tests', function() {

    describe('R_jenis_kelamin Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_jenis_kelamin;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_jenis_kelamin = jasmine.createSpy('MockR_jenis_kelamin');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_jenis_kelamin': MockR_jenis_kelamin
            };
            createController = function() {
                $injector.get('$controller')("R_jenis_kelaminDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_jenis_kelaminUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
