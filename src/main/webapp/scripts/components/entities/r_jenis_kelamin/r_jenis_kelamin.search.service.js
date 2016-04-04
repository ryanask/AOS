'use strict';

angular.module('aplikasiApp')
    .factory('R_jenis_kelaminSearch', function ($resource) {
        return $resource('api/_search/r_jenis_kelamins/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
