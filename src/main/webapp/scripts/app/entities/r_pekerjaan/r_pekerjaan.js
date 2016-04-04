'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_pekerjaan', {
                parent: 'entity',
                url: '/r_pekerjaans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_pekerjaan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_pekerjaan/r_pekerjaans.html',
                        controller: 'R_pekerjaanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_pekerjaan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_pekerjaan.detail', {
                parent: 'entity',
                url: '/r_pekerjaan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_pekerjaan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_pekerjaan/r_pekerjaan-detail.html',
                        controller: 'R_pekerjaanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_pekerjaan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_pekerjaan', function($stateParams, R_pekerjaan) {
                        return R_pekerjaan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_pekerjaan.new', {
                parent: 'r_pekerjaan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_pekerjaan/r_pekerjaan-dialog.html',
                        controller: 'R_pekerjaanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_pekerjaan: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_pekerjaan', null, { reload: true });
                    }, function() {
                        $state.go('r_pekerjaan');
                    })
                }]
            })
            .state('r_pekerjaan.edit', {
                parent: 'r_pekerjaan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_pekerjaan/r_pekerjaan-dialog.html',
                        controller: 'R_pekerjaanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_pekerjaan', function(R_pekerjaan) {
                                return R_pekerjaan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_pekerjaan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_pekerjaan.delete', {
                parent: 'r_pekerjaan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_pekerjaan/r_pekerjaan-delete-dialog.html',
                        controller: 'R_pekerjaanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_pekerjaan', function(R_pekerjaan) {
                                return R_pekerjaan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_pekerjaan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
