'use strict';

angular.module('aplikasiApp')
	.controller('R_status_rekeningDeleteController', function($scope, $uibModalInstance, entity, R_status_rekening) {

        $scope.r_status_rekening = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_status_rekening.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
