'use strict';

angular.module('aplikasiApp').controller('R_skemaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'R_skema',
        function($scope, $stateParams, $uibModalInstance, entity, R_skema) {

        $scope.r_skema = entity;
        $scope.load = function(id) {
            R_skema.get({id : id}, function(result) {
                $scope.r_skema = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('aplikasiApp:r_skemaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.r_skema.id != null) {
                R_skema.update($scope.r_skema, onSaveSuccess, onSaveError);
            } else {
                R_skema.save($scope.r_skema, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
