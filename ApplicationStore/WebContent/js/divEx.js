//var app = angular.module('myDiv', []);

/*categories = [];
console.log(categories);*/
angular.module('myServlet').controller('MyDivController', ['$http',function($http) {
	var self = this;
	self.isVisible = true;
	self.addingCategory = false;
	//self.alphabet = ['/DynamicDiv/DownloadServlet', '/UploadProject/index.html', 'C', 'D'];
	self.image = "folder1.png";
	self.names = [];
	self.links = [];
	//self.categories = ['database','ide','server','data_processing','other'];
	//self.categories = myService.getCategories();
	self.categories = [];
	
	//console.log("From controller in dE "+self.categories);
	/*console.log(self.categories);*/
	/*$http({
        method : 'GET',
        url : 'GetCategories'
	}).success(function(data, status, headers, config) {
	self.categories = Object.keys(data);
	console.log(self.categories);
	});
	self.category = self.categories[0];*/
	self.goToCategories = false;
	//self.outer = [1,2,3];
	//self.inner = [1,2,3];
	self.getSoftwares = function(category){
		
		console.log(category);
		
		//if(category == "Databases")
		//{
			self.names = [];
			self.links = [];
			$http({
                method : 'GET',
                url : 'GetSoftwares',
                params: { "category" : category }
        }).success(function(data, status, headers, config) {
        	
        	self.softwares = data;
        	console.log(Object.keys(self.softwares));
        	self.names = Object.keys(self.softwares);
        	//console.log(self.categories);
        	console.log(self.names);
        	/*self.links = Object.values(self.softwares);
        	console.log(self.links);*/
        	for(i=0;i<self.names.length;i++)
        	{
        		
        		//self.links[i] = self.softwares[self.names[i]];
        		self.links.push(self.softwares[self.names[i]])
        	}
        	//console.log(self.softwares[self.names[i]]);
        	console.log(self.links);
        	//self.links.push(1);
        	//console.log(self.links);
        	/*self.names = softwares.name;
        	self.links = softwares.link_to_update;*/
        	
        	self.isVisible = false;
        	self.goToCategories = true;
        	
        });
		/*}
		
		if(category == "IDEs")
		{
			self.names = [];
			self.links = [];
			$http({
                method : 'GET',
                url : '/AngularServlet/GetSoftwares',
                params: { "category" : "ide" }
        }).success(function(data, status, headers, config) {
        	
        	self.softwares = data;
        	console.log(Object.keys(self.softwares));
        	self.names = Object.keys(self.softwares);
        	console.log(self.categories);
        	console.log(self.names);
        	/*self.links = Object.values(self.softwares);
        	console.log(self.links);
        	for(i=0;i<self.names.length;i++)
        	{
        		
        		//self.links[i] = self.softwares[self.names[i]];
        		self.links.push(self.softwares[self.names[i]])
        	}
        	//console.log(self.softwares[self.names[i]]);
        	console.log(self.links);
        	//self.links.push(1);
        	//console.log(self.links);
        	/*self.names = softwares.name;
        	self.links = softwares.link_to_update;
        	
        	self.isVisible = false;
        	self.goToCategories = true;
        });
		}
		
		
		if(category == "Servers")
		{
			self.names = [];
			self.links = [];
			$http({
                method : 'GET',
                url : '/AngularServlet/GetSoftwares',
                params: { "category" : "server" }
        }).success(function(data, status, headers, config) {
        	
        	self.softwares = data;
        	console.log(Object.keys(self.softwares));
        	self.names = Object.keys(self.softwares);
        	console.log(self.categories);
        	console.log(self.names);
        	/*self.links = Object.values(self.softwares);
        	console.log(self.links);
        	for(i=0;i<self.names.length;i++)
        	{
        		
        		//self.links[i] = self.softwares[self.names[i]];
        		self.links.push(self.softwares[self.names[i]])
        	}
        	//console.log(self.softwares[self.names[i]]);
        	console.log(self.links);
        	//self.links.push(1);
        	//console.log(self.links);
        	/*self.names = softwares.name;
        	self.links = softwares.link_to_update;
        	
        	self.isVisible = false;
        	self.goToCategories = true;
        	
        });
		}
		
		if(category == "Processing")
		{
			self.names = [];
			self.links = [];
			$http({
                method : 'GET',
                url : '/AngularServlet/GetSoftwares',
                params: { "category" : "data_processing" }
        }).success(function(data, status, headers, config) {
        	
        	self.softwares = data;
        	console.log(Object.keys(self.softwares));
        	self.names = Object.keys(self.softwares);
        	console.log(self.categories);
        	console.log(self.names);
        	/*self.links = Object.values(self.softwares);
        	console.log(self.links);
        	for(i=0;i<self.names.length;i++)
        	{
        		
        		//self.links[i] = self.softwares[self.names[i]];
        		self.links.push(self.softwares[self.names[i]])
        	}
        	//console.log(self.softwares[self.names[i]]);
        	console.log(self.links);
        	//self.links.push(1);
        	//console.log(self.links);
        	/*self.names = softwares.name;
        	self.links = softwares.link_to_update;
        	
        	self.isVisible = false;
        	self.goToCategories = true;
        	
        });
		}
		
		if(category == "Others")
		{
			self.names = [];
			self.links = [];
			$http({
                method : 'GET',
                url : '/AngularServlet/GetSoftwares',
                params: { "category" : "other" }
        }).success(function(data, status, headers, config) {
        	
        	self.softwares = data;
        	console.log(Object.keys(self.softwares));
        	self.names = Object.keys(self.softwares);
        	console.log(self.categories);
        	console.log(self.names);
        	/*self.links = Object.values(self.softwares);
        	console.log(self.links);
        	for(i=0;i<self.names.length;i++)
        	{
        		
        		//self.links[i] = self.softwares[self.names[i]];
        		self.links.push(self.softwares[self.names[i]])
        	}
        	//console.log(self.softwares[self.names[i]]);
        	console.log(self.links);
        	//self.links.push(1);
        	//console.log(self.links);
        	/*self.names = softwares.name;
        	self.links = softwares.link_to_update;
        	
        	self.isVisible = false;
        	self.goToCategories = true;
        	
        });
		}*/
		
		
		
	};
	
	self.addCategory = function(){
		console.log(self.category);
		console.log(self.category.description);
		$http({
            method: "post",
            url: "AddCategory",
            params: {
                "category" : self.category,
                "description" : self.category.description
            }
        }).success(function(data, status, headers, config) {
        	
            self.getCategories();
            self.category = "";
            self.category.description = "";
         });
	};
	
	self.download = function(file){
		
		$http({
            method : 'GET',
            url : 'DownloadServlet',
            params: { "file" : file }
    }).success(function(data, status, headers, config) {
    	
    console.log(headers);
    console.log(data);
    	
    });
		
	};
	
	self.goToCategoriesDiv = function(category){
		
		self.isVisible = true;
    	self.goToCategories = false;
	};
	
	
	 self.getCategories = function () {
     	
     	$http({
             method : 'GET',
             url : 'GetCategories'
     }).success(function(data, status, headers, config) {
    	 self.temp = data;
     	self.categories = [];
     	console.log("Before "+self.categories);
     	self.categories=Object.keys(self.temp);
     	//self.category = self.categories[0];
     	console.log("After "+self.categories);
     	self.isVisible = true;
     	console.log(self.isVisible);
     });
     };
	
}]);

/*app.service('myService', function(){
	var category = [];
	this.setCategories= function(data){
		category = data;
		self.categories = category;
		console.log("From setCategories in dE "+category);
    };
    /*this.getCategories= function(){
    	console.log("From getCategories in dE "+category);
    	return category;
    	
    };
    
});*/
