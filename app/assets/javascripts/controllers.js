/** Controllers */
angular.module('contacts.controllers', ['contacts.services'])
    .controller('ContactCtrl', function ($scope, contactService, fileService) {
        'use strict';

        $scope.message = "";
        $scope.query = "";
        $scope.contacts = [];
        $scope.totalContactsCount = 0;
        $scope.validate = false;

        $scope.uploadFile = function (files) {
            var fd = new FormData();
            $scope.message = "";
            //Take the first selected file
            fd.append("file", files[0]);

            fileService.uploadFile(fd, $scope.validate)
                .success(function (response) {
                    $scope.message = response;
                    console.log("success");
                })
                .error(function (error) {
                    $scope.message = error;
                    console.log("error")
                });
        };

        $scope.search = function () {
            if ($scope.query == "") {
                $scope.contacts = [];
            } else {
                contactService.searchContacts($scope.query)
                    .success(function (response) {
                        $scope.contacts = response;
                        console.log("success");
                    })
                    .error(function (error) {
                        $scope.contacts = [];
                        console.log("error")
                    });
            }
        };


    });