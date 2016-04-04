'use strict';

angular.module('aplikasiApp')
	.controller('R_is_linkageDeleteController', function($scope, $uibModalInstance, entity, R_is_linkage) {

        $scope.r_is_linkage = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_is_linkage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
