'use strict';

angular.module('aplikasiApp')
    .controller('R_negara_tujuanDetailController', function ($scope, $rootScope, $stateParams, entity, R_negara_tujuan) {
        $scope.r_negara_tujuan = entity;
        $scope.load = function (id) {
            R_negara_tujuan.get({id: id}, function(result) {
                $scope.r_negara_tujuan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_negara_tujuanUpdate', function(event, result) {
            $scope.r_negara_tujuan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
