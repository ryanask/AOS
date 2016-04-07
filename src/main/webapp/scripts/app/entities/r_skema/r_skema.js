'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_skema', {
                parent: 'entity',
                url: '/r_skemas',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_skema.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_skema/r_skemas.html',
                        controller: 'R_skemaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_skema');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_skema.detail', {
                parent: 'entity',
                url: '/r_skema/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_skema.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_skema/r_skema-detail.html',
                        controller: 'R_skemaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_skema');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_skema', function($stateParams, R_skema) {
                        return R_skema.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_skema.new', {
                parent: 'r_skema',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_skema/r_skema-dialog.html',
                        controller: 'R_skemaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_skema: null,
                                    uraian: null,
                                    max_plafon: null,
                                    max_tenor: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_skema', null, { reload: true });
                    }, function() {
                        $state.go('r_skema');
                    })
                }]
            })
            .state('r_skema.edit', {
                parent: 'r_skema',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_skema/r_skema-dialog.html',
                        controller: 'R_skemaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_skema', function(R_skema) {
                                return R_skema.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_skema', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_skema.delete', {
                parent: 'r_skema',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_skema/r_skema-delete-dialog.html',
                        controller: 'R_skemaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_skema', function(R_skema) {
                                return R_skema.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_skema', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
