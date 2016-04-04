'use strict';

angular.module('aplikasiApp')
    .controller('R_skemaController', function ($scope, $state, R_skema, R_skemaSearch, ParseLinks) {

        $scope.r_skemas = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_skema.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_skemas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_skemaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_skemas = result;
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
            $scope.r_skema = {
                id_r_skema: null,
                uraian: null,
                max_plafon: null,
                max_tenor: null,
                id: null
            };
        };
    });
