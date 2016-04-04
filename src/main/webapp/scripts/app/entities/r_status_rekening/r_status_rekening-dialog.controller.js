'use strict';

angular.module('aplikasiApp').controller('R_status_rekeningDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_status_rekening',
        function($scope, $stateParams, $uibModalInstance, entity, R_status_rekening) {

        $scope.r_status_rekening = entity;
        $scope.load = function(id) {
            R_status_rekening.get({id : id}, function(result) {
                $scope.r_status_rekening = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_status_rekeningUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_status_rekening.id != null) {
                R_status_rekening.update($scope.r_status_rekening, onSaveSuccess, onSaveError);
            } else {
                R_status_rekening.save($scope.r_status_rekening, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
