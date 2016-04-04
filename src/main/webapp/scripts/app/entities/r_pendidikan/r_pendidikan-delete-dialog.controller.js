'use strict';

angular.module('aplikasiApp')
	.controller('R_pendidikanDeleteController', function($scope, $uibModalInstance, entity, R_pendidikan) {

        $scope.r_pendidikan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_pendidikan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
