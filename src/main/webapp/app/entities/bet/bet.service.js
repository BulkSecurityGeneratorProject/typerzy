(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('Bet', Bet);

    Bet.$inject = ['$resource', 'DateUtils'];

    function Bet ($resource, DateUtils) {
        var resourceUrl =  'api/bets/:id';

        return { crud: $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        }), games:  $resource('api/bets/game/:gameId', {}, {
            'get': { method: 'PUT', isArray: true}
        })
        };
    }
})();
