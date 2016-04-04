'use strict';

angular.module('aplikasiApp')
    .controller('R_is_subsidizedController', function ($scope, $state, R_is_subsidized, R_is_subsidizedSearch, ParseLinks) {

        $scope.r_is_subsidizeds = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_is_subsidized.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_is_subsidizeds = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_is_subsidizedSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_is_subsidizeds = result;
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
            $scope.r_is_subsidized = {
                id_r_is_subsidized: null,
                uraian: null,
                id: null
            };
        };
    });
