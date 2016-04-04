'use strict';

angular.module('aplikasiApp')
    .factory('R_skemaSearch', function ($resource) {
        return $resource('api/_search/r_skemas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
