'use strict';

angular.module('aplikasiApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('r_status_akad', {
                parent: 'entity',
                url: '/r_status_akads',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_status_akad.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_status_akad/r_status_akads.html',
                        controller: 'R_status_akadController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_status_akad');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('r_status_akad.detail', {
                parent: 'entity',
                url: '/r_status_akad/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'aplikasiApp.r_status_akad.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/r_status_akad/r_status_akad-detail.html',
                        controller: 'R_status_akadDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('r_status_akad');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'R_status_akad', function($stateParams, R_status_akad) {
                        return R_status_akad.get({id : $stateParams.id});
                    }]
                }
            })
            .state('r_status_akad.new', {
                parent: 'r_status_akad',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_status_akad/r_status_akad-dialog.html',
                        controller: 'R_status_akadDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_r_status_akad: null,
                                    uraian: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('r_status_akad', null, { reload: true });
                    }, function() {
                        $state.go('r_status_akad');
                    })
                }]
            })
            .state('r_status_akad.edit', {
                parent: 'r_status_akad',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_status_akad/r_status_akad-dialog.html',
                        controller: 'R_status_akadDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['R_status_akad', function(R_status_akad) {
                                return R_status_akad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_status_akad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('r_status_akad.delete', {
                parent: 'r_status_akad',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/r_status_akad/r_status_akad-delete-dialog.html',
                        controller: 'R_status_akadDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['R_status_akad', function(R_status_akad) {
                                return R_status_akad.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('r_status_akad', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
