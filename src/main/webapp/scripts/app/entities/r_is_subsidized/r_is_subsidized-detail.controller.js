'use strict';

angular.module('aplikasiApp')
    .controller('R_is_subsidizedDetailController', function ($scope, $rootScope, $stateParams, entity, R_is_subsidized) {
        $scope.r_is_subsidized = entity;
        $scope.load = function (id) {
            R_is_subsidized.get({id: id}, function(result) {
                $scope.r_is_subsidized = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_is_subsidizedUpdate', function(event, result) {
            $scope.r_is_subsidized = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
