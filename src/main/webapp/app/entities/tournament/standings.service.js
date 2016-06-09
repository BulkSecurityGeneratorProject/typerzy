(function() {
    'use strict';
    angular
        .module('test2App')
        .factory('Standing', Standing);

    Standing.$inject = ['$resource'];

    function Standing ($resource) {
        var resourceUrl =  'api/standings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
        });
    }
})();
