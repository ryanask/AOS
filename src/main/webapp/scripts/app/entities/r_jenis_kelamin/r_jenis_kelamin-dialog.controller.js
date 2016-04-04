'use strict';

angular.module('aplikasiApp').controller('R_jenis_kelaminDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_jenis_kelamin',
        function($scope, $stateParams, $uibModalInstance, entity, R_jenis_kelamin) {

        $scope.r_jenis_kelamin = entity;
        $scope.load = function(id) {
            R_jenis_kelamin.get({id : id}, function(result) {
                $scope.r_jenis_kelamin = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_jenis_kelaminUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_jenis_kelamin.id != null) {
                R_jenis_kelamin.update($scope.r_jenis_kelamin, onSaveSuccess, onSaveError);
            } else {
                R_jenis_kelamin.save($scope.r_jenis_kelamin, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
