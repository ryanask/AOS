'use strict';

angular.module('aplikasiApp')
	.controller('R_kode_kolektibilitasDeleteController', function($scope, $uibModalInstance, entity, R_kode_kolektibilitas) {

        $scope.r_kode_kolektibilitas = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            R_kode_kolektibilitas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
