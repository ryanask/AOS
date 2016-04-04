'use strict';

describe('Controller Tests', function() {

    describe('R_is_subsidized Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockR_is_subsidized;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockR_is_subsidized = jasmine.createSpy('MockR_is_subsidized');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'R_is_subsidized': MockR_is_subsidized
            };
            createController = function() {
                $injector.get('$controller')("R_is_subsidizedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:r_is_subsidizedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
