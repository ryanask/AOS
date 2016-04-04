'use strict';

angular.module('aplikasiApp').controller('R_is_linkageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_is_linkage',
        function($scope, $stateParams, $uibModalInstance, entity, R_is_linkage) {

        $scope.r_is_linkage = entity;
        $scope.load = function(id) {
            R_is_linkage.get({id : id}, function(result) {
                $scope.r_is_linkage = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_is_linkageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_is_linkage.id != null) {
                R_is_linkage.update($scope.r_is_linkage, onSaveSuccess, onSaveError);
            } else {
                R_is_linkage.save($scope.r_is_linkage, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
