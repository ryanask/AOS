'use strict';

describe('Controller Tests', function() {

    describe('R_pendidikan Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_pendidikan;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_pendidikan = jasmine.createSpy('MockR_pendidikan');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_pendidikan': MockR_pendidikan
            };
            createController = function() {
                $injector.get('$controller')("R_pendidikanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_pendidikanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
