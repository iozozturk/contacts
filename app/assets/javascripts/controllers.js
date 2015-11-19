/** Controllers */
angular.module('contacts.controllers', ['contacts.services']).
controller('ContactCtrl', function ($scope, $http, contactService) {
    'use strict';

    $scope.uploadFile = function (files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        $http.post("/upload", fd, {
            withCredentials: true,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function(){
            console.log("success");
        }).error(function(){
            console.log("error")
        });

    };


});