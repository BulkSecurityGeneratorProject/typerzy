(function() {
    'use strict';

    angular
        .module('test2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tournament', {
            parent: 'entity',
            url: '/tournament',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.tournament.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tournament/tournaments.html',
                    controller: 'TournamentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tournament');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tournament-detail', {
            parent: 'entity',
            url: '/tournament/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'test2App.tournament.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tournament/tournament-detail.html',
                    controller: 'TournamentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tournament');
                    $translatePartialLoader.addPart('game');
                    $translatePartialLoader.addPart('bet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tournament', function($stateParams, Tournament) {
                    return Tournament.get({id : $stateParams.id});
                }]
            }
        })
        .state('tournament.new', {
            parent: 'tournament',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament/tournament-dialog.html',
                    controller: 'TournamentDialogController',
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
                    $state.go('tournament', null, { reload: true });
                }, function() {
                    $state.go('tournament');
                });
            }]
        })
        .state('tournament.edit', {
            parent: 'tournament',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament/tournament-dialog.html',
                    controller: 'TournamentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tournament', function(Tournament) {
                            return Tournament.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tournament', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        
          .state('newgame', {
        	parent: 'tournament-detail',
            url: '/newgame',
            params: {owner:null},
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/game-dialog.html',
                    controller: 'GameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                time: new Date(),
                                id: null,
                                tournament: $stateParams.owner
                            };
                        }
                        
                        
                    }
                }).result.then(function() {
                	
                    $state.go('tournament-detail', {id: $stateParams.owner.id}, { reload: true });
                }, function() {
                	  $state.go('tournament-detail', {id: $stateParams.owner.id});
                });
            }]
        })
        .state('tournament.delete', {
            parent: 'tournament',
            url: '/{gameid}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tournament/tournament-delete-dialog.html',
                    controller: 'TournamentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tournament', function(Tournament) {
                            return Tournament.get({id : $stateParams.gameid});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tournament', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })  .state('game.edit', {
            parent: 'tournament-detail',
            url: '/{gameid}/edit',
            params: {owner:null},
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/game-dialog.html',
                    controller: 'GameDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Game', function(Game) {
                            return Game.games.get({id : $stateParams.gameid});
                        }]
                    }
                }).result.then(function() {
                	   $state.go('tournament-detail', {id: $stateParams.owner.id}, { reload: true });
                }, function() {
                	  $state.go('tournament-detail', {id: $stateParams.owner.id});
                });
            }]
        })
        .state('game.delete', {
            parent: 'tournament-detail',
            url: '/{id}/delete',
            params: {owner:null},
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/game-delete-dialog.html',
                    controller: 'GameDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Game', function(Game) {
                        		
                            return Game.games.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                	   $state.go('tournament-detail', {id: $stateParams.owner.id}, { reload: true });
                }, function() {
                	  $state.go('tournament-detail', {id: $stateParams.owner.id});
                });
            }]
        })
        .state('bet.betOrEdit', {
            parent: 'tournament-detail',
            url: '/bet',
            params: {owner:null, game:null},
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bet/bet-dialog.html',
                    controller: 'BetDialogController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bet', function(Bet) {
                        		
                            var bet =Bet.games.getForUserAndGame({gameId : $stateParams.game.id});
                            bet.fixture = $stateParams.game;
                            return bet;
                        }]
                    }
                }).result.then(function() {
                	   $state.go('tournament-detail', {id: $stateParams.owner.id}, { reload: true });
                }, function() {
                	  $state.go('tournament-detail', {id: $stateParams.owner.id});
                });
            }]
        });
    }

})();
