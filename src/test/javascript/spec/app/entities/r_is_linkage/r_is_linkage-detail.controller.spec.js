'use strict';

describe('Controller Tests', function() {

    describe('R_is_linkage Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_is_linkage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_is_linkage = jasmine.createSpy('MockR_is_linkage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_is_linkage': MockR_is_linkage
            };
            createController = function() {
                $injector.get('$controller')("R_is_linkageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_is_linkageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
