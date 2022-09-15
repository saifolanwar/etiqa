angular.module('myCollege', ['datatables'])
  .controller('HomeController', function($scope, $http) {
	
  	var staticURL = 'http://192.168.1.13:8800/mycollegeapp/services/student';
    //login value is:
	//hash 256 of concatenation of username=user1, password=password123 and application public key=1dd395c19f3a1f7ff0e6bfe3ee6028e4373b41085a6da776ab02a8baf129abba
	//so it should be sha256(user1+password123+1dd395c19f3a1f7ff0e6bfe3ee6028e4373b41085a6da776ab02a8baf129abba)
	//in actual this should be implemented by login. to return back token if success
    $http({
          method: 'POST',
          url: staticURL+'/authenticate',
          params: {},
          headers: {
              "Content-Type": "application/x-www-form-urlencoded",
              "Authorization": "Enhanced a0d7af29c4e333987aca6b1db0198e422181b6d265794fb38267de9a3c03ca97"
          }
    }).then(function(response) {
        var data = response.data;
        $scope.accessToken = data.access_token;
        $http({
          method: 'GET',
          url: staticURL+'/get_course_list',
          params: {},
          headers: {
              "Content-Type": "application/json",
              "Authorization": "Bearer "+$scope.accessToken,
          }
	    }).then(function(response) {
	        var data = response.data;
	        $scope.list = data.list;
		  	$(document).ready(function () {
		    	$('#studentCourse').DataTable();
			});
	    });
    });
    
  	$scope.showAdd = function() {
	    document.getElementById("studentCourse_length").style.display = "none";
	    document.getElementById("studentCourse_filter").style.display = "none";
	    document.getElementById("studentCourse_info").style.display = "none";
	    document.getElementById("studentCourse_paginate").style.display = "none";
	    document.getElementById("studentCourse").style.display = "none";
	    document.getElementById("addUpdateDelete").style.display = "block";
	    document.getElementById("buttonCreateUpdate").innerHTML = "Create";
	    document.getElementById("buttonDelete").style.display = "none";
	    document.getElementById("buttonAdd").style.display = "none";
	};
    
  	$scope.showUpdateDelete = function(item) {
		$scope.item = item;
	    document.getElementById("studentCourse_length").style.display = "none";
	    document.getElementById("studentCourse_filter").style.display = "none";
	    document.getElementById("studentCourse_info").style.display = "none";
	    document.getElementById("studentCourse_paginate").style.display = "none";
	    document.getElementById("studentCourse").style.display = "none";
	    document.getElementById("addUpdateDelete").style.display = "block";
	    document.getElementById("buttonCreateUpdate").innerHTML = "Update";
	    document.getElementById("buttonDelete").style.display = "inline-block";
	    document.getElementById("buttonAdd").style.display = "none";
	    document.getElementById("name").value = item.name;
	    document.getElementById("phone").value = item.phone;
	    document.getElementById("course").value = item.course;
	    document.getElementById("email").value = item.email;
	};
    
  	$scope.showList = function() {
	    document.getElementById("studentCourse_length").style.display = "inline-block";
	    document.getElementById("studentCourse_filter").style.display = "inline-block";
	    document.getElementById("studentCourse_info").style.display = "inline-block";
	    document.getElementById("studentCourse_paginate").style.display = "inline-block";
	    document.getElementById("studentCourse").style.display = "block";
	    document.getElementById("addUpdateDelete").style.display = "none";
	    document.getElementById("buttonCreateUpdate").innerHTML = "Create / Update";
	    document.getElementById("buttonAdd").style.display = "block";
	};
	
	$scope.createUpdate = function() {
		if(document.getElementById("buttonCreateUpdate").innerHTML=="Create") {
			console.log("Do create");
	        $http({
	          method: 'POST',
	          url: staticURL+'/add_course_list',
	          params: {name: document.getElementById("name").value, 
	          course: document.getElementById("course").value, 
	          phone: document.getElementById("phone").value, 
	          email: document.getElementById("email").value },
	          headers: {
	              "Content-Type": "application/json",
	              "Authorization": "Bearer "+$scope.accessToken,
	          }
		    }).then(function(response) {
		    });
		} else if(document.getElementById("buttonCreateUpdate").innerHTML=="Update") {
			console.log("Do update");
	        $http({
	          method: 'POST',
	          url: staticURL+'/update_course_list',
	          params: {id: $scope.item.id, 
	          name: document.getElementById("name").value, 
	          course: document.getElementById("course").value, 
	          phone: document.getElementById("phone").value, 
	          email: document.getElementById("email").value },
	          headers: {
	              "Content-Type": "application/json",
	              "Authorization": "Bearer "+$scope.accessToken,
	          }
		    }).then(function(response) {
		    });
		}
	}
	
	$scope.doDelete = function() {
		console.log("Do delete");
	        $http({
	          method: 'POST',
	          url: staticURL+'/delete_course_list',
	          params: {id: $scope.item.id, 
	          name: document.getElementById("name").value, 
	          course: document.getElementById("course").value, 
	          phone: document.getElementById("phone").value, 
	          email: document.getElementById("email").value },
	          headers: {
	              "Content-Type": "application/json",
	              "Authorization": "Bearer "+$scope.accessToken,
	          }
		    }).then(function(response) {
		    });
	}

});