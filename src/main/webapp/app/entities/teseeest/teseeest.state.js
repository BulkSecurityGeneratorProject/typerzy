(function() {
    'use strict';

    angular
        .module('test2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('teseeest', {
            parent: 'entity',
            url: '/teseeest',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.teseeest.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/teseeest/teseeests.html',
                    controller: 'TeseeestController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('teseeest');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('teseeest-detail', {
            parent: 'entity',
            url: '/teseeest/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.teseeest.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/teseeest/teseeest-detail.html',
                    controller: 'TeseeestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('teseeest');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Teseeest', function($stateParams, Teseeest) {
                    return Teseeest.get({id : $stateParams.id});
                }]
            }
        })
        .state('teseeest.new', {
            parent: 'teseeest',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teseeest/teseeest-dialog.html',
                    controller: 'TeseeestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('teseeest', null, { reload: true });
                }, function() {
                    $state.go('teseeest');
                });
            }]
        })
        .state('teseeest.edit', {
            parent: 'teseeest',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teseeest/teseeest-dialog.html',
                    controller: 'TeseeestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Teseeest', function(Teseeest) {
                            return Teseeest.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('teseeest', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('teseeest.delete', {
            parent: 'teseeest',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teseeest/teseeest-delete-dialog.html',
                    controller: 'TeseeestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Teseeest', function(Teseeest) {
                            return Teseeest.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('teseeest', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
