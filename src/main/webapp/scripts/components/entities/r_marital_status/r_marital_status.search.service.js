'use strict';

angular.module('aplikasiApp')
    .factory('R_marital_statusSearch', function ($resource) {
        return $resource('api/_search/r_marital_statuss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
