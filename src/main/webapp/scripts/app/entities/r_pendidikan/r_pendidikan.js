'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_pendidikan', {
                parent: 'entity',
                url: '/r_pendidikans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_pendidikan.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_pendidikan/r_pendidikans.html',
                        controller: 'R_pendidikanController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_pendidikan');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_pendidikan.detail', {
                parent: 'entity',
                url: '/r_pendidikan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_pendidikan.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_pendidikan/r_pendidikan-detail.html',
                        controller: 'R_pendidikanDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_pendidikan');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_pendidikan', function($stateParams, R_pendidikan) {
                        return R_pendidikan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_pendidikan.new', {
                parent: 'r_pendidikan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_pendidikan/r_pendidikan-dialog.html',
                        controller: 'R_pendidikanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_pendidikan: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_pendidikan', null, { reload: true });
                    }, function() {
                        $state.go('r_pendidikan');
                    })
                }]
            })
            .state('r_pendidikan.edit', {
                parent: 'r_pendidikan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_pendidikan/r_pendidikan-dialog.html',
                        controller: 'R_pendidikanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_pendidikan', function(R_pendidikan) {
                                return R_pendidikan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_pendidikan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_pendidikan.delete', {
                parent: 'r_pendidikan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_pendidikan/r_pendidikan-delete-dialog.html',
                        controller: 'R_pendidikanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_pendidikan', function(R_pendidikan) {
                                return R_pendidikan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_pendidikan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
