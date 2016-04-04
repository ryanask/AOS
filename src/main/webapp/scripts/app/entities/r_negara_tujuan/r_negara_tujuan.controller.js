'use strict';

angular.module('aplikasiApp')
    .controller('R_negara_tujuanController', function ($scope, $state, R_negara_tujuan, R_negara_tujuanSearch, ParseLinks) {

        $scope.r_negara_tujuans = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_negara_tujuan.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_negara_tujuans = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_negara_tujuanSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_negara_tujuans = result;
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
            $scope.r_negara_tujuan = {
                id_r_negara_tujuan: null,
                uraian: null,
                id: null
            };
        };
    });
