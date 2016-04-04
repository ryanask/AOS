'use strict';

angular.module('aplikasiApp')
    .factory('R_status_akadSearch', function ($resource) {
        return $resource('api/_search/r_status_akads/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
