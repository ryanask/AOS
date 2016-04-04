'use strict';

angular.module('aplikasiApp')
	.controller('R_skemaDeleteController', function($scope, $uibModalInstance, entity, R_skema) {

        $scope.r_skema = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_skema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
