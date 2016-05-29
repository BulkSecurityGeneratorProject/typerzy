(function() {
    'use strict';

    angular
        .module('test2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fixture-result', {
            parent: 'entity',
            url: '/fixture-result',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.fixtureResult.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fixture-result/fixture-results.html',
                    controller: 'FixtureResultController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fixtureResult');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('fixture-result-detail', {
            parent: 'entity',
            url: '/fixture-result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.fixtureResult.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fixture-result/fixture-result-detail.html',
                    controller: 'FixtureResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fixtureResult');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FixtureResult', function($stateParams, FixtureResult) {
                    return FixtureResult.get({id : $stateParams.id});
                }]
            }
        })
        .state('fixture-result.new', {
            parent: 'fixture-result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fixture-result/fixture-result-dialog.html',
                    controller: 'FixtureResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                home: null,
                                away: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fixture-result', null, { reload: true });
                }, function() {
                    $state.go('fixture-result');
                });
            }]
        })
        .state('fixture-result.edit', {
            parent: 'fixture-result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fixture-result/fixture-result-dialog.html',
                    controller: 'FixtureResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FixtureResult', function(FixtureResult) {
                            return FixtureResult.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('fixture-result', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fixture-result.delete', {
            parent: 'fixture-result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fixture-result/fixture-result-delete-dialog.html',
                    controller: 'FixtureResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FixtureResult', function(FixtureResult) {
                            return FixtureResult.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('fixture-result', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
