'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_marital_status', {
                parent: 'entity',
                url: '/r_marital_statuss',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_marital_status.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_marital_status/r_marital_statuss.html',
                        controller: 'R_marital_statusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_marital_status');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_marital_status.detail', {
                parent: 'entity',
                url: '/r_marital_status/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_marital_status.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_marital_status/r_marital_status-detail.html',
                        controller: 'R_marital_statusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_marital_status');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_marital_status', function($stateParams, R_marital_status) {
                        return R_marital_status.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_marital_status.new', {
                parent: 'r_marital_status',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_marital_status/r_marital_status-dialog.html',
                        controller: 'R_marital_statusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_marital_status: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_marital_status', null, { reload: true });
                    }, function() {
                        $state.go('r_marital_status');
                    })
                }]
            })
            .state('r_marital_status.edit', {
                parent: 'r_marital_status',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_marital_status/r_marital_status-dialog.html',
                        controller: 'R_marital_statusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_marital_status', function(R_marital_status) {
                                return R_marital_status.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_marital_status', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_marital_status.delete', {
                parent: 'r_marital_status',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_marital_status/r_marital_status-delete-dialog.html',
                        controller: 'R_marital_statusDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_marital_status', function(R_marital_status) {
                                return R_marital_status.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_marital_status', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
