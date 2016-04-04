'use strict';

angular.module('aplikasiApp').controller('R_status_akadDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_status_akad',
        function($scope, $stateParams, $uibModalInstance, entity, R_status_akad) {

        $scope.r_status_akad = entity;
        $scope.load = function(id) {
            R_status_akad.get({id : id}, function(result) {
                $scope.r_status_akad = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_status_akadUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_status_akad.id != null) {
                R_status_akad.update($scope.r_status_akad, onSaveSuccess, onSaveError);
            } else {
                R_status_akad.save($scope.r_status_akad, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
