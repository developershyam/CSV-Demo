SpringApp.factory("FileSearchService", function($http) {

	return {

		getValidDeals : function($scope, currentPage) {

			var searchText = $scope.searchText;
			if (!searchText)
				return false;
			setLoader("validDealDiv");
			$.get(contextPath + "/getCSVFileValidDeals?currentPage="
					+ currentPage + "&pageSize=" + pageSize + "&fileName="
					+ searchText, function(dataWrapper, status) {
				$scope.$apply(function() {
					removeLoader("validDealDiv");
					$scope.validDealWrapper = dataWrapper;
					if ($scope.validDealWrapper && $scope.validDealWrapper.data
							&& $scope.validDealWrapper.data.length > 0) {
						$("#validDealPaginationFooter").unbind();
						validDealPaginationFooter(dataWrapper.pageNumber,
								dataWrapper.pageSize, dataWrapper.totalPages,
								dataWrapper.totalElement);
					} else {
						$('#validDealPaginationFooter').html('');

					}
				});

			});
		},
		getInvalidDeals : function($scope, currentPage) {

			var searchText = $scope.searchText;
			if (!searchText)
				return false;
			setLoader("invalidDealDiv");
			$.get(contextPath + "/getCSVFileInvalidDeals?currentPage="
					+ currentPage + "&pageSize=" + pageSize + "&fileName="
					+ searchText, function(dataWrapper, status) {
				$scope.$apply(function() {
					removeLoader("invalidDealDiv");
					$scope.invalidDealWrapper = dataWrapper;
					if ($scope.invalidDealWrapper
							&& $scope.invalidDealWrapper.data
							&& $scope.invalidDealWrapper.data.length > 0) {
						$("#invalidDealPaginationFooter").unbind();
						invalidDealPaginationFooter(dataWrapper.pageNumber,
								dataWrapper.pageSize, dataWrapper.totalPages,
								dataWrapper.totalElement);
					} else {
						$('#invalidDealPaginationFooter').html('');

					}
				});

			});
		},
		getAccumulateCurrency : function($scope, currentPage) {

			setLoader("accumulateCurrencyDiv");
			$.get(contextPath + "/getAccumulateOrderingCurrency?currentPage="
					+ currentPage + "&pageSize=" + pageSize, function(dataWrapper, status) {
				$scope.$apply(function() {
					removeLoader("accumulateCurrencyDiv");
					$scope.accumulateCurrencyWrapper = dataWrapper;
					if ($scope.accumulateCurrencyWrapper
							&& $scope.accumulateCurrencyWrapper.data
							&& $scope.accumulateCurrencyWrapper.data.length > 0) {
						$("#accumulateCurrencyPaginationFooter").unbind();
						accumulateCurrencyPaginationFooter(dataWrapper.pageNumber,
								dataWrapper.pageSize, dataWrapper.totalPages,
								dataWrapper.totalElement);
					} else {
						$('#accumulateCurrencyPaginationFooter').html('');

					}
				});

			});
		},
		deleteByFileName : function($scope) {

			var searchText = $scope.searchText;
			if (!searchText)
				return false;
			pageLoaderAPI(true);
			$.ajax({
				headers : {
					"Accept" : "application/json",
					"Content-Type" : "application/json",
				},
				type : "DELETE",
				url : contextPath + "/deleteByFile?fileName=" + searchText,
				dataType : "json",
				success : function(response) {
					pageLoaderAPI(false);
					$scope.searchValidDeals();

				},
				error : function(e) {
					alert("error");
					pageLoaderAPI(false);
				},
				done : function(e) {
					alert("done");
					pageLoaderAPI(false);
				}
			});
		},
	};
});
SpringApp.controller('FileSearchController', [ '$scope', '$http',
		'FileSearchService', '$timeout',
		function($scope, $http, FileSearchService, $timeout) {

			$scope.searchValidDeals = function() {
				FileSearchService.getValidDeals($scope, 0);
				FileSearchService.getInvalidDeals($scope, 0);
				FileSearchService.getAccumulateCurrency($scope, 0);
			}
			
			$scope.deleteCSV = function() {
				FileSearchService.deleteByFileName($scope);
			}
			
			FileSearchService.getAccumulateCurrency($scope, 0);

		} ]);

function validDealPaginationFooter(pageNumber, pageSize, totalPages,
		totalElement) {

	var maxVisiblePages = totalPages;
	if (parseInt(totalPages) > 5) {
		maxVisiblePages = 5;
	}
	$('#validDealPaginationFooter').bootpag({
		total : totalPages,
		page : pageNumber,
		maxVisible : maxVisiblePages,
		leaps : true,
		firstLastUse : true,
		first : 'FIRST',
		last : 'LAST',
		next : 'NEXT',
		prev : 'PREV',
		wrapClass : 'pagination',
		activeClass : 'active',
		disabledClass : 'disabled',
		nextClass : 'next',
		prevClass : 'prev',
		lastClass : 'last',
		firstClass : 'first'
	}).on(
			"page",
			function(event, currentPage) {
				getNgService("FileSearchService").getValidDeals(
						getNgScope("fileSearchController"), currentPage);
			});
}

function invalidDealPaginationFooter(pageNumber, pageSize, totalPages,
		totalElement) {

	var maxVisiblePages = totalPages;
	if (parseInt(totalPages) > 5) {
		maxVisiblePages = 5;
	}
	$('#invalidDealPaginationFooter').bootpag({
		total : totalPages,
		page : pageNumber,
		maxVisible : maxVisiblePages,
		leaps : true,
		firstLastUse : true,
		first : 'FIRST',
		last : 'LAST',
		next : 'NEXT',
		prev : 'PREV',
		wrapClass : 'pagination',
		activeClass : 'active',
		disabledClass : 'disabled',
		nextClass : 'next',
		prevClass : 'prev',
		lastClass : 'last',
		firstClass : 'first'
	}).on(
			"page",
			function(event, currentPage) {
				getNgService("FileSearchService").getInvalidDeals(
						getNgScope("fileSearchController"), currentPage);
			});
}

function accumulateCurrencyPaginationFooter(pageNumber, pageSize, totalPages,
		totalElement) {

	var maxVisiblePages = totalPages;
	if (parseInt(totalPages) > 5) {
		maxVisiblePages = 5;
	}
	$('#accumulateCurrencyPaginationFooter').bootpag({
		total : totalPages,
		page : pageNumber,
		maxVisible : maxVisiblePages,
		leaps : true,
		firstLastUse : true,
		first : 'FIRST',
		last : 'LAST',
		next : 'NEXT',
		prev : 'PREV',
		wrapClass : 'pagination',
		activeClass : 'active',
		disabledClass : 'disabled',
		nextClass : 'next',
		prevClass : 'prev',
		lastClass : 'last',
		firstClass : 'first'
	}).on(
			"page",
			function(event, currentPage) {
				getNgService("FileSearchService").getAccumulateCurrency(
						getNgScope("fileSearchController"), currentPage);
			});
}