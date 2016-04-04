'use strict';

angular.module('aplikasiApp').controller('R_is_subsidizedDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_is_subsidized',
        function($scope, $stateParams, $uibModalInstance, entity, R_is_subsidized) {

        $scope.r_is_subsidized = entity;
        $scope.load = function(id) {
            R_is_subsidized.get({id : id}, function(result) {
                $scope.r_is_subsidized = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_is_subsidizedUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_is_subsidized.id != null) {
                R_is_subsidized.update($scope.r_is_subsidized, onSaveSuccess, onSaveError);
            } else {
                R_is_subsidized.save($scope.r_is_subsidized, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
