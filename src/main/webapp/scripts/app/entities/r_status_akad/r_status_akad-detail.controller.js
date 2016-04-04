'use strict';

angular.module('aplikasiApp')
    .controller('R_status_akadDetailController', function ($scope, $rootScope, $stateParams, entity, R_status_akad) {
        $scope.r_status_akad = entity;
        $scope.load = function (id) {
            R_status_akad.get({id: id}, function(result) {
                $scope.r_status_akad = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_status_akadUpdate', function(event, result) {
            $scope.r_status_akad = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
