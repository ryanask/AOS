'use strict';

angular.module('aplikasiApp')
    .controller('R_pekerjaanDetailController', function ($scope, $rootScope, $stateParams, entity, R_pekerjaan) {
        $scope.r_pekerjaan = entity;
        $scope.load = function (id) {
            R_pekerjaan.get({id: id}, function(result) {
                $scope.r_pekerjaan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_pekerjaanUpdate', function(event, result) {
            $scope.r_pekerjaan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
