'use strict';

angular.module('aplikasiApp')
    .controller('R_status_akadController', function ($scope, $state, R_status_akad, R_status_akadSearch, ParseLinks) {

        $scope.r_status_akads = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_status_akad.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_status_akads = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_status_akadSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_status_akads = result;
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
            $scope.r_status_akad = {
                id_r_status_akad: null,
                uraian: null,
                id: null
            };
        };
    });
