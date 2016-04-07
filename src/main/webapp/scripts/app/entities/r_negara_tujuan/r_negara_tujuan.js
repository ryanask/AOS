'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_negara_tujuan', {
                parent: 'entity',
                url: '/r_negara_tujuans',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_negara_tujuan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_negara_tujuan/r_negara_tujuans.html',
                        controller: 'R_negara_tujuanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_negara_tujuan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_negara_tujuan.detail', {
                parent: 'entity',
                url: '/r_negara_tujuan/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'aplikasiApp.r_negara_tujuan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_negara_tujuan/r_negara_tujuan-detail.html',
                        controller: 'R_negara_tujuanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_negara_tujuan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_negara_tujuan', function($stateParams, R_negara_tujuan) {
                        return R_negara_tujuan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_negara_tujuan.new', {
                parent: 'r_negara_tujuan',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_negara_tujuan/r_negara_tujuan-dialog.html',
                        controller: 'R_negara_tujuanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_negara_tujuan: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_negara_tujuan', null, { reload: true });
                    }, function() {
                        $state.go('r_negara_tujuan');
                    })
                }]
            })
            .state('r_negara_tujuan.edit', {
                parent: 'r_negara_tujuan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_negara_tujuan/r_negara_tujuan-dialog.html',
                        controller: 'R_negara_tujuanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_negara_tujuan', function(R_negara_tujuan) {
                                return R_negara_tujuan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_negara_tujuan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_negara_tujuan.delete', {
                parent: 'r_negara_tujuan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_negara_tujuan/r_negara_tujuan-delete-dialog.html',
                        controller: 'R_negara_tujuanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_negara_tujuan', function(R_negara_tujuan) {
                                return R_negara_tujuan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_negara_tujuan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
