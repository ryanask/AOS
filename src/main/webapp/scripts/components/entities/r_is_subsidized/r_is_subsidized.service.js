'use strict';

angular.module('aplikasiApp')
    .factory('R_is_subsidized', function ($resource, DateUtils) {
        return $resource('api/r_is_subsidizeds/:id', {}, {
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
