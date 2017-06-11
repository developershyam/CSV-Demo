var SpringApp = angular.module('SpringApp',["xeditable", 'easypiechart']);

SpringApp.run(function(editableOptions) {
  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});

SpringApp.filter('to_trusted', ['$sce', function($sce){
  return function(text) {
      return $sce.trustAsHtml(text);
  };
}]);

SpringApp.directive('repeatDone', function() {
  return function(scope, element, attrs) {
    if (scope.$last) { // all are rendered
      scope.$eval(attrs.repeatDone);
    }
  };
});

SpringApp.directive('afterRender', ['$timeout', function ($timeout) {
  var def = {
      restrict: 'A',
      terminal: true,
      transclude: false,
      link: function (scope, element, attrs) {
          $timeout(scope.$eval(attrs.afterRender), 0);  //Calling a scoped method
      }
  };
  return def;
}]);


function getNgScope(id) {
  $scope = angular.element($("#" + id)).scope();
  return $scope;
}

function getNgService(name) {
  var service=angular.injector([ 'ng', 'SpringApp' ]).get(name);
  return service;
}

function setScopeVarValue ($scope, varName, varValue) {
  
  $scope.$apply(function(){
    $scope[varName]=varValue;
  });  
};


SpringApp.directive("fileread", [function () {
  return {
      scope: {
          fileread: "="
      },
      link: function (scope, element, attributes) {
          element.bind("change", function (changeEvent) {
              var reader = new FileReader();
              reader.onload = function (loadEvent) {
                  scope.$apply(function () {
                      scope.fileread = loadEvent.target.result;
                  });
              }
              reader.readAsDataURL(changeEvent.target.files[0]);
          });
      }
  }
}]);

SpringApp.directive('toggle', function(){
	  return {
	    restrict: 'A',
	    link: function(scope, element, attrs){
	      if (attrs.toggle=="popover"){
	        $(element).popover({
	        	container: 'body',
	        	html : true, 
	         });
	      }
	    }
	  };
	})

