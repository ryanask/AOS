'use strict';

angular.module('aplikasiApp')
    .controller('R_jenis_kelaminDetailController', function ($scope, $rootScope, $stateParams, entity, R_jenis_kelamin) {
        $scope.r_jenis_kelamin = entity;
        $scope.load = function (id) {
            R_jenis_kelamin.get({id: id}, function(result) {
                $scope.r_jenis_kelamin = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_jenis_kelaminUpdate', function(event, result) {
            $scope.r_jenis_kelamin = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
