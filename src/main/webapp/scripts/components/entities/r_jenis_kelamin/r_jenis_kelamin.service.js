'use strict';

angular.module('aplikasiApp')
    .factory('R_jenis_kelamin', function ($resource, DateUtils) {
        return $resource('api/r_jenis_kelamins/:id', {}, {
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
