(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('Teseeest', Teseeest);

    Teseeest.$inject = ['$resource'];

    function Teseeest ($resource) {
        var resourceUrl =  'api/teseeests/:id';

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
