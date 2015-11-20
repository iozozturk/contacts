/** contact service */
angular.module('contacts.services', [])
    .service('contactService', function ($http) {
        'use strict';

        this.searchContacts = function (query) {
            return $http.get("/contacts", {
                params: {q: query}
            })
        }


    })
    .service('fileService', function ($http) {
        'use strict';

        this.uploadFile = function (fd) {
            return $http.post("/upload", fd, {
                withCredentials: true,
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity
            })
        }


    });