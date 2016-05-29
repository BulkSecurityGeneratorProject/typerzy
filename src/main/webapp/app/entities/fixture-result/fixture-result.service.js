(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('FixtureResult', FixtureResult);

    FixtureResult.$inject = ['$resource'];

    function FixtureResult ($resource) {
        var resourceUrl =  'api/fixture-results/:id';

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
