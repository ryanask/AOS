'use strict';

angular.module('aplikasiApp')
	.controller('R_is_subsidizedDeleteController', function($scope, $uibModalInstance, entity, R_is_subsidized) {

        $scope.r_is_subsidized = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_is_subsidized.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
