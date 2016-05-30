(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('Game', Game);

    Game.$inject = ['$resource', 'DateUtils'];

    function Game ($resource, DateUtils) {
        var resourceUrl =  'api/games/:id';

        return  { games: $resource(resourceUrl, {}, {
                  'query': { method: 'GET', isArray: true},
                  'get': {
                      method: 'GET',
                      transformResponse: function (data) {
                          data = angular.fromJson(data);
                          data.time = DateUtils.convertDateTimeFromServer(data.time);
                          return data;
                      }
                  },
                  'update': { method:'PUT' },
                  
                  
                  
              }), fixtures: $resource('api/fixtures/:id' , {
                  'query' : {method: 'GET', param:{id:'@id'}, isArray : true}
              })
        }    ;
    }
})();
