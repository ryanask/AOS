'use strict';

angular.module('aplikasiApp')
    .controller('R_jenis_kelaminController', function ($scope, $state, R_jenis_kelamin, R_jenis_kelaminSearch, ParseLinks) {

        $scope.r_jenis_kelamins = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_jenis_kelamin.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_jenis_kelamins = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_jenis_kelaminSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_jenis_kelamins = result;
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
            $scope.r_jenis_kelamin = {
                id_r_jenis_kelamin: null,
                uraian: null,
                id: null
            };
        };
    });
