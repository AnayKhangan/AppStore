var app = angular.module('myServlet', ['ngFileUpload','ngRoute'])
/*.config(['$routeProvider',
         function($routeProvider) {
           $routeProvider
             .when('/home', {
               templateUrl: 'categories.html',
               controller: 'MyDivController',
             })
		}])*/
 ;

//function MyController(self, $http) {
app.controller('MyController', ['$scope','Upload', '$timeout', '$http', function ( $scope,Upload, $timeout, $http) {
	console.log("M loaded..");
	var self = this;
	self.isVisibleLogin = true;
	self.canUpload = false;
	self.canDownload = false;
	self.loggedIn = false;
	//self.isVisibleLogin = true;
	self.isVisibleCategory = false;
	self.progressPercentage = parseInt(0);
	console.log(self.progressPercentage);
        self.checkUser = function() {
                $http({
                        method : 'GET',
                        url : 'LoginServlet',
                        params: { "gsid" : self.user.gsid, "password" : self.user.password }
                }).success(function(data, status, headers, config) {
                	self.person = data;
                        if(self.person.role != "none")
                        {
                        	
                            if(self.person.role == "admin")
                            	self.canUpload = true;
                            if(self.person.role == "guest")
                            	self.canDownload = true;
                            self.loggedIn = true;
                            self.isVisibleLogin = false;
                            self.isVisibleCategory = true; 
                            //console.log(self.progressPercentage);
                            
                        }
                        else
                        {
                        	/*self.canUpload = false;
                        	self.canDownload = false;
                        	self.loggedIn = false;
                        	self.isVisibleLogin = true;*/
                        	self.user.gsid = "";
                        	self.user.password = "";
                        	self.user.invalidUser = "Authentication Failed. PLease try again.";
                        }
                        
                }).error(function(data, status, headers, config) {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                	console.log("Hello");
                });

        };
        
        /*self.getCategories = function () {
        	
        	$http({
                method : 'GET',
                url : 'GetCategories'
        }).success(function(data, status, headers, config) {
        	self.category = Object.keys(data);
        	console.log("From getCategories in sM "+self.category);
        	myService.setCategories(self.category);
        });
        };
        /*self.hideLogin = function()
        {
        	self.isVisibleLogin = false;
        }*/
        
        self.upload = function (files) {
            //if (files && files.length) {
                //for (var i = 0; i < files.length; i++) {
                    //var file = files[i];
        	self.log = '';
        	
                    Upload.upload({
                        url: 'UploadServlet',
                        fields: {
                            'name_of_the_software': self.software.name,
                            'version_of_the_software' : self.software.version,
                            'category_list' : self.software.category,
                            'uploaded_by' : self.software.uploaded_by,
                            'email_id' : self.software.email,
                            'description' : self.software.description,
                            
                        },
                        file: files
                    }).progress(function (evt) {
                    	
                        self.progressPercentage = parseInt(50.0 * evt.loaded / evt.total);
                        console.log(self.progressPercentage);
                        //self.log = 'progress: ' + progressPercentage + '% ' + '\n' + self.log;
                    }).success(function (data, status, headers, config) {
                        $timeout(function() {
                            //self.log = 'file: ' + ', Response: ' + JSON.stringify(data) + '\n' + self.log;
                        	self.progressPercentage = parseInt(75);
                        	self.progressPercentage = parseInt(100);
                        	alert(data.response);
                        	self.software.name = "";
                        	self.software.version = "";
                        	self.software.category = "";
                        	self.software.uploaded_by = "";
                        	self.software.email = "";
                        	self.software.description = "";
                        	self.progressPercentage = parseInt(0);
                        });
                    	
                    });
                //}
           // }
        };
        
        
        

}]);

app.directive('categories', function(){
	 return {
	   restrict: 'A',
	   //scope: false,
	   templateUrl: '/ApplicationStore/categories.html'
	 }
	});

/*app.directive('upLoadForm', function(){
	 return {
	   restrict: 'A',
	   scope: false,
	   templateUrl: '/AngularServlet/index.jsp'
	 }
	});*/
