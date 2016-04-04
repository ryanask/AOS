'use strict';

angular.module('aplikasiApp')
    .factory('R_is_linkageSearch', function ($resource) {
        return $resource('api/_search/r_is_linkages/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
