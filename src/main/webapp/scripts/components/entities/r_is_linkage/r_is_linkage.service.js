'use strict';

angular.module('aplikasiApp')
    .factory('R_is_linkage', function ($resource, DateUtils) {
        return $resource('api/r_is_linkages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
