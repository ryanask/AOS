'use strict';

angular.module('aplikasiApp')
    .controller('R_skemaDetailController', function ($scope, $rootScope, $stateParams, entity, R_skema) {
        $scope.r_skema = entity;
        $scope.load = function (id) {
            R_skema.get({id: id}, function(result) {
                $scope.r_skema = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_skemaUpdate', function(event, result) {
            $scope.r_skema = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
