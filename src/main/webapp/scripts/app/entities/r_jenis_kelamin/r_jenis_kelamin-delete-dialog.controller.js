'use strict';

angular.module('aplikasiApp')
	.controller('R_jenis_kelaminDeleteController', function($scope, $uibModalInstance, entity, R_jenis_kelamin) {

        $scope.r_jenis_kelamin = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_jenis_kelamin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
