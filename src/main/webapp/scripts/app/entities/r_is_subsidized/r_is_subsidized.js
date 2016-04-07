'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_is_subsidized', {
                parent: 'entity',
                url: '/r_is_subsidizeds',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_is_subsidized.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_is_subsidized/r_is_subsidizeds.html',
                        controller: 'R_is_subsidizedController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_is_subsidized');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_is_subsidized.detail', {
                parent: 'entity',
                url: '/r_is_subsidized/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_is_subsidized.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_is_subsidized/r_is_subsidized-detail.html',
                        controller: 'R_is_subsidizedDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_is_subsidized');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_is_subsidized', function($stateParams, R_is_subsidized) {
                        return R_is_subsidized.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_is_subsidized.new', {
                parent: 'r_is_subsidized',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_is_subsidized/r_is_subsidized-dialog.html',
                        controller: 'R_is_subsidizedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_is_subsidized: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_is_subsidized', null, { reload: true });
                    }, function() {
                        $state.go('r_is_subsidized');
                    })
                }]
            })
            .state('r_is_subsidized.edit', {
                parent: 'r_is_subsidized',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_is_subsidized/r_is_subsidized-dialog.html',
                        controller: 'R_is_subsidizedDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_is_subsidized', function(R_is_subsidized) {
                                return R_is_subsidized.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_is_subsidized', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_is_subsidized.delete', {
                parent: 'r_is_subsidized',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_is_subsidized/r_is_subsidized-delete-dialog.html',
                        controller: 'R_is_subsidizedDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_is_subsidized', function(R_is_subsidized) {
                                return R_is_subsidized.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_is_subsidized', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
