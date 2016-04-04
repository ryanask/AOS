'use strict';

angular.module('aplikasiApp')
    .factory('R_marital_status', function ($resource, DateUtils) {
        return $resource('api/r_marital_statuss/:id', {}, {
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
