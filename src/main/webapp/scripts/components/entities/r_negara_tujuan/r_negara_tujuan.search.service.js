'use strict';

angular.module('aplikasiApp')
    .factory('R_negara_tujuanSearch', function ($resource) {
        return $resource('api/_search/r_negara_tujuans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
