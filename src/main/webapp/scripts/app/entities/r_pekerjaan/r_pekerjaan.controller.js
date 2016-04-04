'use strict';

angular.module('aplikasiApp')
    .controller('R_pekerjaanController', function ($scope, $state, R_pekerjaan, R_pekerjaanSearch, ParseLinks) {

        $scope.r_pekerjaans = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_pekerjaan.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_pekerjaans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_pekerjaanSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_pekerjaans = result;
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
            $scope.r_pekerjaan = {
                id_r_pekerjaan: null,
                uraian: null,
                id: null
            };
        };
    });
