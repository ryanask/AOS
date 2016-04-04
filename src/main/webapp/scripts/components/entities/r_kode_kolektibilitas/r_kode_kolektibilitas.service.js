'use strict';

angular.module('aplikasiApp')
    .factory('R_kode_kolektibilitas', function ($resource, DateUtils) {
        return $resource('api/r_kode_kolektibilitass/:id', {}, {
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
