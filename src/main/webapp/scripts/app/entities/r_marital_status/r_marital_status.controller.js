'use strict';

angular.module('aplikasiApp')
    .controller('R_marital_statusController', function ($scope, $state, R_marital_status, R_marital_statusSearch, ParseLinks) {

        $scope.r_marital_statuss = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_marital_status.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_marital_statuss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_marital_statusSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_marital_statuss = result;
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
            $scope.r_marital_status = {
                id_r_marital_status: null,
                uraian: null,
                id: null
            };
        };
    });
