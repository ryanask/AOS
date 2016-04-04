'use strict';

angular.module('aplikasiApp')
    .factory('R_is_subsidizedSearch', function ($resource) {
        return $resource('api/_search/r_is_subsidizeds/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
