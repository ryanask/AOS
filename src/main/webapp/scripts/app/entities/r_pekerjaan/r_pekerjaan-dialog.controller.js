'use strict';

angular.module('aplikasiApp').controller('R_pekerjaanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_pekerjaan',
        function($scope, $stateParams, $uibModalInstance, entity, R_pekerjaan) {

        $scope.r_pekerjaan = entity;
        $scope.load = function(id) {
            R_pekerjaan.get({id : id}, function(result) {
                $scope.r_pekerjaan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_pekerjaanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_pekerjaan.id != null) {
                R_pekerjaan.update($scope.r_pekerjaan, onSaveSuccess, onSaveError);
            } else {
                R_pekerjaan.save($scope.r_pekerjaan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
