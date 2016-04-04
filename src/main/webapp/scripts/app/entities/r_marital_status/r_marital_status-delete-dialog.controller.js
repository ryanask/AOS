'use strict';

angular.module('aplikasiApp')
	.controller('R_marital_statusDeleteController', function($scope, $uibModalInstance, entity, R_marital_status) {

        $scope.r_marital_status = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_marital_status.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
