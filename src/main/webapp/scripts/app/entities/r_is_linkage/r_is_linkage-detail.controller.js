'use strict';

angular.module('aplikasiApp')
    .controller('R_is_linkageDetailController', function ($scope, $rootScope, $stateParams, entity, R_is_linkage) {
        $scope.r_is_linkage = entity;
        $scope.load = function (id) {
            R_is_linkage.get({id: id}, function(result) {
                $scope.r_is_linkage = result;
            });
        };
        var unsubscribe = $rootScope.$on('aplikasiApp:r_is_linkageUpdate', function(event, result) {
            $scope.r_is_linkage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
