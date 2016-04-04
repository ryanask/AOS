'use strict';

angular.module('aplikasiApp')
    .controller('R_status_rekeningDetailController', function ($scope, $rootScope, $stateParams, entity, R_status_rekening) {
        $scope.r_status_rekening = entity;
        $scope.load = function (id) {
            R_status_rekening.get({id: id}, function(result) {
                $scope.r_status_rekening = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_status_rekeningUpdate', function(event, result) {
            $scope.r_status_rekening = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
