'use strict';

angular.module('aplikasiApp')
    .factory('R_pekerjaanSearch', function ($resource) {
        return $resource('api/_search/r_pekerjaans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
