'use strict';

angular.module('aplikasiApp')
	.controller('R_status_akadDeleteController', function($scope, $uibModalInstance, entity, R_status_akad) {

        $scope.r_status_akad = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_status_akad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
