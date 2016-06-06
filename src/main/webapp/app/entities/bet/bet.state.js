(function() {
    'use strict';

    angular
        .module('test2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bet', {
            parent: 'entity',
            url: '/bet',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.bet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bet/bets.html',
                    controller: 'BetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bet-detail', {
            parent: 'entity',
            url: '/bet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.bet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bet/bet-detail.html',
                    controller: 'BetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Bet', function($stateParams, Bet) {
                    return Bet.crud.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('bet.new', {
            parent: 'bet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bet/bet-dialog.html',
                    controller: 'BetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                time: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bet', null, { reload: true });
                }, function() {
                    $state.go('bet');
                });
            }]
        })
        .state('bet.edit', {
            parent: 'bet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bet/bet-dialog.html',
                    controller: 'BetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bet', function(Bet) {
                            return Bet.crud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bet', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bet.delete', {
            parent: 'bet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bet/bet-delete-dialog.html',
                    controller: 'BetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bet', function(Bet) {
                            return Bet.crud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bet', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
