'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_status_rekening', {
                parent: 'entity',
                url: '/r_status_rekenings',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_status_rekening.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_status_rekening/r_status_rekenings.html',
                        controller: 'R_status_rekeningController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_status_rekening');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_status_rekening.detail', {
                parent: 'entity',
                url: '/r_status_rekening/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_status_rekening.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_status_rekening/r_status_rekening-detail.html',
                        controller: 'R_status_rekeningDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_status_rekening');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_status_rekening', function($stateParams, R_status_rekening) {
                        return R_status_rekening.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_status_rekening.new', {
                parent: 'r_status_rekening',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_status_rekening/r_status_rekening-dialog.html',
                        controller: 'R_status_rekeningDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_status_rekening: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_status_rekening', null, { reload: true });
                    }, function() {
                        $state.go('r_status_rekening');
                    })
                }]
            })
            .state('r_status_rekening.edit', {
                parent: 'r_status_rekening',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_status_rekening/r_status_rekening-dialog.html',
                        controller: 'R_status_rekeningDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_status_rekening', function(R_status_rekening) {
                                return R_status_rekening.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_status_rekening', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_status_rekening.delete', {
                parent: 'r_status_rekening',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_status_rekening/r_status_rekening-delete-dialog.html',
                        controller: 'R_status_rekeningDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_status_rekening', function(R_status_rekening) {
                                return R_status_rekening.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_status_rekening', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
