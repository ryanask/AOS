'use strict';

angular.module('aplikasiApp')
    .controller('R_status_rekeningController', function ($scope, $state, R_status_rekening, R_status_rekeningSearch, ParseLinks) {

        $scope.r_status_rekenings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_status_rekening.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_status_rekenings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_status_rekeningSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_status_rekenings = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.r_status_rekening = {
                id_r_status_rekening: null,
                uraian: null,
                id: null
            };
        };
    });
