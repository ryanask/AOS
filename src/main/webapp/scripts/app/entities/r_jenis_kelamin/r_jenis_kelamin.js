'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_jenis_kelamin', {
                parent: 'entity',
                url: '/r_jenis_kelamins',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_jenis_kelamin.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_jenis_kelamin/r_jenis_kelamins.html',
                        controller: 'R_jenis_kelaminController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_jenis_kelamin');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_jenis_kelamin.detail', {
                parent: 'entity',
                url: '/r_jenis_kelamin/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_jenis_kelamin.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_jenis_kelamin/r_jenis_kelamin-detail.html',
                        controller: 'R_jenis_kelaminDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_jenis_kelamin');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_jenis_kelamin', function($stateParams, R_jenis_kelamin) {
                        return R_jenis_kelamin.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_jenis_kelamin.new', {
                parent: 'r_jenis_kelamin',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_jenis_kelamin/r_jenis_kelamin-dialog.html',
                        controller: 'R_jenis_kelaminDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_jenis_kelamin: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_jenis_kelamin', null, { reload: true });
                    }, function() {
                        $state.go('r_jenis_kelamin');
                    })
                }]
            })
            .state('r_jenis_kelamin.edit', {
                parent: 'r_jenis_kelamin',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_jenis_kelamin/r_jenis_kelamin-dialog.html',
                        controller: 'R_jenis_kelaminDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_jenis_kelamin', function(R_jenis_kelamin) {
                                return R_jenis_kelamin.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_jenis_kelamin', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_jenis_kelamin.delete', {
                parent: 'r_jenis_kelamin',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_jenis_kelamin/r_jenis_kelamin-delete-dialog.html',
                        controller: 'R_jenis_kelaminDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_jenis_kelamin', function(R_jenis_kelamin) {
                                return R_jenis_kelamin.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_jenis_kelamin', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
