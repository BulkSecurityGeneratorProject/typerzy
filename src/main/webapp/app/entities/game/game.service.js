(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('Game', Game);

    Game.$inject = ['$resource', 'DateUtils'];

    function Game ($resource, DateUtils) {
        var resourceUrl =  'api/games/:id';
        return {
            projects: $resource('api/tournamentgames/:id', {}, {
              query: { method: 'GET', isArray: true }
            }),
            all : $resource(resourceUrl, {}, {
                'query': { method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                        return data;
                    }
                },
                'update': { method:'PUT' }
            })
          };
         
    }
})();
