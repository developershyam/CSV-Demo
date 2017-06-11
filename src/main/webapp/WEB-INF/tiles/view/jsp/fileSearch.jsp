<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(document).ready(function() {
		$('.nav li').removeClass('active');
		$('#csvFileSearchTab').addClass('active');
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="<c:url value="/resources/js/file-search.js" />"></script>
<div class="body_content">
	<div class="container" id="fileSearchController"
		ng-controller="FileSearchController">
		<div class="row"
			style="border: grey solid 1px; padding: 10px 0 20px 20px; margin-top: 80px;">
			<span style="font-size: 1.2em; font-weight: bold;">Search CSV
				File</span>
			<div class="col-sm-12">
				<div class="col-sm-4">
					<div class="form-group">
						<div class="icon-addon addon-md">
							<input id="searchText" ng-model="searchText" type="text"
								placeholder="Search Name Here"
								ng-keypress="($event.which === 13)?searchValidDeals():0"
								class="form-control" style="border-radius: 25px;"> <label
								class="glyphicon glyphicon-search" rel="tooltip"
								title="Search Product" style="cursor: pointer;"
								ng-click="searchValidDeals()"></label>
						</div>
					</div>
				</div>
				<div class="col-sm-3"
					ng-if="(validDealWrapper.data.length + invalidDealWrapper.data.length)>0">
					<button type="button" class="btn btn-danger" ng-click="deleteCSV()">Delete
						CSV</button>
				</div>

			</div>
		</div>

		<div id="validDealDiv" class="shadowR5">
			<div class="row"
				style="margin-top: 20px; text-align: center; margin-left: 0px; margin-right: 0px;">
				<span style="font-size: 1.2em; font-weight: bold;"> <i
					class="glyphicon glyphicon-ok-circle fa-lg" style="color: #42A129;"></i>
					&nbsp; Valid Deals
				</span>
				<div ng-if="validDealWrapper.data.length>0" class="totalRecords">Total
					Records: {{validDealWrapper.totalElement}} , Total Pages:
					{{validDealWrapper.totalPages}}</div>
			</div>
			<div class="row"
				style="max-height: 330px; overflow: auto; margin-left: 0px; margin-right: 0px;">
				<table class="table table-striped table-bordered" style="margin-bottom: 0;">
					<thead>
						<tr>
							<th>Sr</th>
							<th>DealId</th>
							<th>FromCurrencyISOCode</th>
							<th>ToCurrencyISOCode</th>
							<th>FromCurrencyISOCodeAmount</th>
							<th>DealTimeStamp</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="validDeal in validDealWrapper.data">
							<td>#
								{{(validDealWrapper.pageNumber-1)*validDealWrapper.pageSize+$index+1}}</td>
							<td>{{validDeal.dealId}}</td>
							<td>{{validDeal.fromCurrencyISOCode}}</td>
							<td>{{validDeal.toCurrencyISOCode}}</td>
							<td>{{validDeal.fromCurrencyISOCodeAmount}}</td>
							<td>{{validDeal.dealTimeStamp}}</td>
						</tr>
					</tbody>
				</table>
				<div ng-if="!validDealWrapper.data.length>0" class="recordNotFound">
					ValidDeals Not Available</div>
			</div>
			<div class="row" style="margin-left: 0px; margin-right: 0px;">
				<div id="validDealPaginationFooter"
					class="paginationFooter text-center"></div>
			</div>
		</div>

		<div id="invalidDealDiv" class="shadowR5">
			<div class="row"
				style="margin-top: 20px; text-align: center; margin-left: 0px; margin-right: 0px;">

				<span style="font-size: 1.2em; font-weight: bold;" > <i
					class="glyphicon glyphicon glyphicon-ban-circle fa-lg"
					style="color: #FF6701;"></i> &nbsp; Invalid Deals
				</span>
				<div ng-if="invalidDealWrapper.data.length>0" class="totalRecords">Total
					Records: {{invalidDealWrapper.totalElement}} , Total Pages:
					{{invalidDealWrapper.totalPages}}</div>
			</div>
			<div class="row"
				style="max-height: 330px; overflow: auto; margin-left: 0px; margin-right: 0px;">
				<table class="table table-striped table-bordered" style="margin-bottom: 0;">
					<thead>
						<tr>
							<th>Sr</th>
							<th>DealId</th>
							<th>FromCurrencyISOCode</th>
							<th>ToCurrencyISOCode</th>
							<th>FromCurrencyISOCodeAmount</th>
							<th>DealTimeStamp</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="invalidDeal in invalidDealWrapper.data">
							<td>#
								{{(invalidDealWrapper.pageNumber-1)*invalidDealWrapper.pageSize+$index+1}}</td>
							<td>{{invalidDeal.dealId}}</td>
							<td>{{invalidDeal.fromCurrencyISOCode}}</td>
							<td>{{invalidDeal.toCurrencyISOCode}}</td>
							<td>{{invalidDeal.fromCurrencyISOCodeAmount}}</td>
							<td>{{invalidDeal.dealTimeStamp}}</td>
						</tr>
					</tbody>
				</table>
				<div ng-if="!invalidDealWrapper.data.length>0"
					class="recordNotFound">InvalidDeals Not Available</div>
			</div>
			<div class="row" style="margin-left: 0px; margin-right: 0px;">
				<div id="invalidDealPaginationFooter"
					class="paginationFooter text-center"></div>
			</div>
		</div>
		
		<div id="accumulateCurrencyDiv" class="shadowR5">
			<div class="row"
				style="margin-top: 20px; text-align: center; margin-left: 0px; margin-right: 0px;">

				<span style="font-size: 1.2em; font-weight: bold;" > <i
					class="fa fa-money fa-lg"
					style="color: #42A129;"></i> &nbsp; Valid Accumulative ISO Currency
				</span>
				<div ng-if="accumulateCurrencyWrapper.data.length>0" class="totalRecords">Total
					Records: {{accumulateCurrencyWrapper.totalElement}} , Total Pages:
					{{accumulateCurrencyWrapper.totalPages}}</div>
			</div>
			<div class="row"
				style="max-height: 330px; overflow: auto; margin-left: 0px; margin-right: 0px;">
				<table class="table table-striped table-bordered" style="margin-bottom: 0;">
					<thead>
						<tr>
							<th>Sr</th>
							<th>FromCurrencyISOCode</th>
							<th>TotalCount</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="accumulateCurrency in accumulateCurrencyWrapper.data">
							<td>#
								{{(accumulateCurrencyWrapper.pageNumber-1)*accumulateCurrencyWrapper.pageSize+$index+1}}</td>
							<td>{{accumulateCurrency.fromCurrencyISOCode}}</td>
							<td>{{accumulateCurrency.totalCount}}</td>
						</tr>
					</tbody>
				</table>
				<div ng-if="!accumulateCurrencyWrapper.data.length>0"
					class="recordNotFound">Accumulate ISO Currency Not Available</div>
			</div>
			<div class="row" style="margin-left: 0px; margin-right: 0px;">
				<div id="accumulateCurrencyPaginationFooter"
					class="paginationFooter text-center"></div>
			</div>
		</div>

	</div>
</div>

