'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_kode_kolektibilitas', {
                parent: 'entity',
                url: '/r_kode_kolektibilitass',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_kode_kolektibilitas.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_kode_kolektibilitas/r_kode_kolektibilitass.html',
                        controller: 'R_kode_kolektibilitasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_kode_kolektibilitas');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_kode_kolektibilitas.detail', {
                parent: 'entity',
                url: '/r_kode_kolektibilitas/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_kode_kolektibilitas.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_kode_kolektibilitas/r_kode_kolektibilitas-detail.html',
                        controller: 'R_kode_kolektibilitasDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_kode_kolektibilitas');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_kode_kolektibilitas', function($stateParams, R_kode_kolektibilitas) {
                        return R_kode_kolektibilitas.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_kode_kolektibilitas.new', {
                parent: 'r_kode_kolektibilitas',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_kode_kolektibilitas/r_kode_kolektibilitas-dialog.html',
                        controller: 'R_kode_kolektibilitasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_kode_kolektibilitas: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_kode_kolektibilitas', null, { reload: true });
                    }, function() {
                        $state.go('r_kode_kolektibilitas');
                    })
                }]
            })
            .state('r_kode_kolektibilitas.edit', {
                parent: 'r_kode_kolektibilitas',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_kode_kolektibilitas/r_kode_kolektibilitas-dialog.html',
                        controller: 'R_kode_kolektibilitasDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_kode_kolektibilitas', function(R_kode_kolektibilitas) {
                                return R_kode_kolektibilitas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_kode_kolektibilitas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_kode_kolektibilitas.delete', {
                parent: 'r_kode_kolektibilitas',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_kode_kolektibilitas/r_kode_kolektibilitas-delete-dialog.html',
                        controller: 'R_kode_kolektibilitasDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_kode_kolektibilitas', function(R_kode_kolektibilitas) {
                                return R_kode_kolektibilitas.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_kode_kolektibilitas', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
