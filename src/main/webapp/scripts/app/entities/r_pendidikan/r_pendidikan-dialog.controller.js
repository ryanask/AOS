'use strict';

angular.module('aplikasiApp').controller('R_pendidikanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_pendidikan',
        function($scope, $stateParams, $uibModalInstance, entity, R_pendidikan) {

        $scope.r_pendidikan = entity;
        $scope.load = function(id) {
            R_pendidikan.get({id : id}, function(result) {
                $scope.r_pendidikan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_pendidikanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_pendidikan.id != null) {
                R_pendidikan.update($scope.r_pendidikan, onSaveSuccess, onSaveError);
            } else {
                R_pendidikan.save($scope.r_pendidikan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
