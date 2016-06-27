var app = angular.module('myServlet', []);

function MyController($scope, $http) {

	$scope.isVisibleLogin = true;
	$scope.canUpload = false;
	$scope.canDownload = false;
        $scope.checkUser = function() {
                $http({
                        method : 'GET',
                        url : 'AngularJServlet',
                        params: { "gsid" : $scope.user.gsid, "password" : $scope.user.password }
                }).success(function(data, status, headers, config) {
                        $scope.person = data;
                        if($scope.person.role == "admin")
                        	$scope.canUpload = true;
                        if($scope.person.role == "guest")
                        	$scope.canDownload = true;
                }).error(function(data, status, headers, config) {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                	System.out.println("Hello");
                });

        };
        
        $scope.hideLogin = function()
        {
        	$scope.isVisibleLogin = false;
        }
        
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip();   
        });
        

};
