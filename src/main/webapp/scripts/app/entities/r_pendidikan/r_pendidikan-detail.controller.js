'use strict';

angular.module('aplikasiApp')
    .controller('R_pendidikanDetailController', function ($scope, $rootScope, $stateParams, entity, R_pendidikan) {
        $scope.r_pendidikan = entity;
        $scope.load = function (id) {
            R_pendidikan.get({id: id}, function(result) {
                $scope.r_pendidikan = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_pendidikanUpdate', function(event, result) {
            $scope.r_pendidikan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
