'use strict';

angular.module('aplikasiApp')
    .controller('R_kode_kolektibilitasDetailController', function ($scope, $rootScope, $stateParams, entity, R_kode_kolektibilitas) {
        $scope.r_kode_kolektibilitas = entity;
        $scope.load = function (id) {
            R_kode_kolektibilitas.get({id: id}, function(result) {
                $scope.r_kode_kolektibilitas = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_kode_kolektibilitasUpdate', function(event, result) {
            $scope.r_kode_kolektibilitas = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
