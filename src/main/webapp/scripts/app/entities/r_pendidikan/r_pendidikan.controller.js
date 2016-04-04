'use strict';

angular.module('aplikasiApp')
    .controller('R_pendidikanController', function ($scope, $state, R_pendidikan, R_pendidikanSearch, ParseLinks) {

        $scope.r_pendidikans = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_pendidikan.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_pendidikans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_pendidikanSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_pendidikans = result;
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
            $scope.r_pendidikan = {
                id_r_pendidikan: null,
                uraian: null,
                id: null
            };
        };
    });
