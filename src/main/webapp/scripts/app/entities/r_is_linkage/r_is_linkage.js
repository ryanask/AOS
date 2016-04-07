'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_is_linkage', {
                parent: 'entity',
                url: '/r_is_linkages',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_is_linkage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_is_linkage/r_is_linkages.html',
                        controller: 'R_is_linkageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_is_linkage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_is_linkage.detail', {
                parent: 'entity',
                url: '/r_is_linkage/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_is_linkage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_is_linkage/r_is_linkage-detail.html',
                        controller: 'R_is_linkageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_is_linkage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_is_linkage', function($stateParams, R_is_linkage) {
                        return R_is_linkage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_is_linkage.new', {
                parent: 'r_is_linkage',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_is_linkage/r_is_linkage-dialog.html',
                        controller: 'R_is_linkageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_is_linkage: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_is_linkage', null, { reload: true });
                    }, function() {
                        $state.go('r_is_linkage');
                    })
                }]
            })
            .state('r_is_linkage.edit', {
                parent: 'r_is_linkage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_is_linkage/r_is_linkage-dialog.html',
                        controller: 'R_is_linkageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_is_linkage', function(R_is_linkage) {
                                return R_is_linkage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_is_linkage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_is_linkage.delete', {
                parent: 'r_is_linkage',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_is_linkage/r_is_linkage-delete-dialog.html',
                        controller: 'R_is_linkageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_is_linkage', function(R_is_linkage) {
                                return R_is_linkage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_is_linkage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
