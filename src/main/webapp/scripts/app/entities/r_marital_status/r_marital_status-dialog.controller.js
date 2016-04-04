'use strict';

angular.module('aplikasiApp').controller('R_marital_statusDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_marital_status',
        function($scope, $stateParams, $uibModalInstance, entity, R_marital_status) {

        $scope.r_marital_status = entity;
        $scope.load = function(id) {
            R_marital_status.get({id : id}, function(result) {
                $scope.r_marital_status = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_marital_statusUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_marital_status.id != null) {
                R_marital_status.update($scope.r_marital_status, onSaveSuccess, onSaveError);
            } else {
                R_marital_status.save($scope.r_marital_status, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
