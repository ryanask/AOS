'use strict';

angular.module('aplikasiApp')
	.controller('R_negara_tujuanDeleteController', function($scope, $uibModalInstance, entity, R_negara_tujuan) {

        $scope.r_negara_tujuan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_negara_tujuan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
