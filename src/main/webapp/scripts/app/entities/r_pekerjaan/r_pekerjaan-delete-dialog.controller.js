'use strict';

angular.module('aplikasiApp')
	.controller('R_pekerjaanDeleteController', function($scope, $uibModalInstance, entity, R_pekerjaan) {

        $scope.r_pekerjaan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_pekerjaan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
