(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('Tournament', Tournament);

    Tournament.$inject = ['$resource'];

    function Tournament ($resource) {
        var resourceUrl =  'api/tournaments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
