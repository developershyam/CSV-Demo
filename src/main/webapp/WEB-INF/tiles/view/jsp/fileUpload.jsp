<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="<c:url value="/resources/js/file-upload.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.nav li').removeClass('active');
		$('#csvFileUploadTab').addClass('active');
	});
</script>

<div class="body_content">
	<div class="container">

		<div class="row"
			style="border: grey solid 1px; padding: 10px 0 20px 20px; margin-top: 80px;">
			<span style="font-size: 1.2em; font-weight: bold;">File Upload
			</span>

			<form method="POST" action="${contextPath}/file/upload"
				enctype="multipart/form-data">
				<div class="col-sm-12">
					<div class="col-sm-6">
						<input type="file" name="file" accept=".csv" class="form-control" />

					</div>
					<div class="col-sm-3">
						<input type="submit" value="Submit" class="btn btn-primary" />
					</div>
				</div>

			</form>

		</div>
		<c:if test="${not empty message}">
			<div class="row alert alert-info"
				style="padding: 5px; margin-top: 20px;">
				<a class="close" data-dismiss="alert">×</a> <strong>Info!</strong>
				${message}
			</div>
		</c:if>

		<div class="row">
			<div class="col-sm-12" style="padding: 5px; margin-top: 20px;">
				<div class="col-sm-4">
					<div class="form-group">
						<div class="icon-addon addon-md">
							<input id="searchText" type="text" placeholder="Search Name Here"
								class="form-control" style="border-radius: 25px;"> <label
								class="glyphicon glyphicon-search" rel="tooltip"
								title="Search Product" style="cursor: pointer;"
								onclick="applyFilter()"></label>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="row" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Sr#</th>
						<th>File Name</th>
						<th>Valid Count</th>
						<th>Invalid Count</th>
					</tr>
				</thead>
				<tbody id="tableData">
					<tr>
						<th>1</th>
						<th><span id="tblFileName"></span></th>
						<th><span id="tblValidCount"></span></th>
						<th><span id="tblInvalidCount"></span></th>
					</tr>
				</tbody>
			</table>

		</div>
		<div id="noDataFound" class="row alert alert-danger"
			style="padding: 5px; margin-top: 20px;">
			<strong>Info!</strong> File not found
		</div>

	</div>
</div>

