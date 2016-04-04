'use strict';

angular.module('aplikasiApp').controller('R_negara_tujuanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_negara_tujuan',
        function($scope, $stateParams, $uibModalInstance, entity, R_negara_tujuan) {

        $scope.r_negara_tujuan = entity;
        $scope.load = function(id) {
            R_negara_tujuan.get({id : id}, function(result) {
                $scope.r_negara_tujuan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_negara_tujuanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_negara_tujuan.id != null) {
                R_negara_tujuan.update($scope.r_negara_tujuan, onSaveSuccess, onSaveError);
            } else {
                R_negara_tujuan.save($scope.r_negara_tujuan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
