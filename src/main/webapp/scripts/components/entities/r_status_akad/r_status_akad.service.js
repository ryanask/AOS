'use strict';

angular.module('aplikasiApp')
    .factory('R_status_akad', function ($resource, DateUtils) {
        return $resource('api/r_status_akads/:id', {}, {
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
