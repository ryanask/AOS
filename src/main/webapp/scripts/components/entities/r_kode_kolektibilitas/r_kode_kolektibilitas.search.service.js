'use strict';

angular.module('aplikasiApp')
    .factory('R_kode_kolektibilitasSearch', function ($resource) {
        return $resource('api/_search/r_kode_kolektibilitass/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
