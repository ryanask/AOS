'use strict';

angular.module('aplikasiApp')
    .controller('R_marital_statusDetailController', function ($scope, $rootScope, $stateParams, entity, R_marital_status) {
        $scope.r_marital_status = entity;
        $scope.load = function (id) {
            R_marital_status.get({id: id}, function(result) {
                $scope.r_marital_status = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_marital_statusUpdate', function(event, result) {
            $scope.r_marital_status = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
