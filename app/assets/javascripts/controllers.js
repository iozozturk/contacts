/** Controllers */
angular.module('contacts.controllers', ['contacts.services'])
    .controller('ContactCtrl', function ($scope, $http, contactService, fileService) {
        'use strict';

        $scope.message = "";
        $scope.query = "";
        $scope.contacts = [];
        $scope.totalContactsCount = 0;
        $scope.validate = true;

        $scope.handleSSEMessage = function (e) {
            $scope.$apply(function () {
                if (e.data == "db_finish") {
                    $scope.message = "Processed contacts and saved!"
                } else {
                    $scope.contacts = JSON.parse(e.data);
                }
            });
        };

        $scope.registerSSEConnection = function () {
            $scope.closeSSEConnection();
            $scope.searchResults = [];
            $scope.eventSource = new EventSource("/register");
            $scope.eventSource.addEventListener("message", $scope.handleSSEMessage, false);
        };

        $scope.closeSSEConnection = function () {
            if (typeof $scope.eventSource != 'undefined') {
                $scope.eventSource.close();
            }
        };

        $scope.uploadFile = function (files) {
            var fd = new FormData();
            $scope.message = "";
            //Take the first selected file
            fd.append("file", files[0]);

            fileService.uploadFile(fd, $scope.validate)
                .success(function (response) {
                    $scope.message = response;
                })
                .error(function (error) {
                    $scope.message = error;
                    console.log("error in upload serice" + error)
                });
        };

        $scope.search = function () {
            contactService.searchContacts($scope.query)
                .success(function (response) {
                    $scope.contacts = response;
                })
                .error(function (error) {
                    $scope.contacts = [];
                    console.log("error in search service" + error)
                });
        };


    });