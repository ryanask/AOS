'use strict';

angular.module('aplikasiApp')
    .factory('R_status_rekeningSearch', function ($resource) {
        return $resource('api/_search/r_status_rekenings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
