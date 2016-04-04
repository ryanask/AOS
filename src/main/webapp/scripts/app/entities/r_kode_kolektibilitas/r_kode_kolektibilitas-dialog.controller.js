'use strict';

angular.module('aplikasiApp').controller('R_kode_kolektibilitasDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_kode_kolektibilitas',
        function($scope, $stateParams, $uibModalInstance, entity, R_kode_kolektibilitas) {

        $scope.r_kode_kolektibilitas = entity;
        $scope.load = function(id) {
            R_kode_kolektibilitas.get({id : id}, function(result) {
                $scope.r_kode_kolektibilitas = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_kode_kolektibilitasUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_kode_kolektibilitas.id != null) {
                R_kode_kolektibilitas.update($scope.r_kode_kolektibilitas, onSaveSuccess, onSaveError);
            } else {
                R_kode_kolektibilitas.save($scope.r_kode_kolektibilitas, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
