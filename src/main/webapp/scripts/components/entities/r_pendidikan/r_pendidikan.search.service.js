'use strict';

angular.module('aplikasiApp')
    .factory('R_pendidikanSearch', function ($resource) {
        return $resource('api/_search/r_pendidikans/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
