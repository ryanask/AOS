'use strict';

angular.module('aplikasiApp')
    .controller('R_kode_kolektibilitasController', function ($scope, $state, R_kode_kolektibilitas, R_kode_kolektibilitasSearch, ParseLinks) {

        $scope.r_kode_kolektibilitass = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            R_kode_kolektibilitas.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.r_kode_kolektibilitass = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            R_kode_kolektibilitasSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.r_kode_kolektibilitass = result;
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
            $scope.r_kode_kolektibilitas = {
                id_r_kode_kolektibilitas: null,
                uraian: null,
                id: null
            };
        };
    });
