/** Controllers */
angular.module('contacts.controllers', ['contacts.services']).
controller('ContactCtrl', function ($scope, $http, contactService) {
    'use strict';

    $scope.message = "";
    $scope.query = "";
    $scope.contacts = [];

    $scope.uploadFile = function (files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        $http.post("/upload", fd, {
            withCredentials: true,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function (response) {
            $scope.message = response;
            console.log("success");
        }).error(function (error) {
            $scope.message = response;
            console.log("error")
        });
    };

    $scope.search = function () {
        if ($scope.query == "") {
            $scope.contacts = [];
        } else {
            $http.get("/contacts", {
                params: {q: $scope.query}
            }).success(function (response) {
                $scope.contacts = response;
                console.log("success");
            }).error(function (error) {
                $scope.contacts = response;
                console.log("error")
            });
        }
    };


});