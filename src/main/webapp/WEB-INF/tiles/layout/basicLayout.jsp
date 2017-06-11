<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en" ng-app="SpringApp">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link href="<c:url value="/resources/css/api/bootstrap.min.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/css/api/bootstrap-theme.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/api/style.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/common.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/api/web.font.css" />"
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/fonts/css/font-awesome.css" />"
	rel="stylesheet" type="text/css" />

<!-- API JS -->
<script src="<c:url value="/resources/js/api/jquery.min.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/api/jquery-ui.min.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/api/bootstrap.min.js" />"
	type="text/javascript"></script>

<script src="<c:url value="/resources/js/api/jquery.bootpag.min.js" />"
	type="text/javascript"></script>

<!-- Angular JS -->
<script src="<c:url value="/resources/js/api/angular.min.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/api/angular-sanitize.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/api/xeditable.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/api/easypiechart.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/angular-config.js" />"
	type="text/javascript"></script>

<script src="<c:url value="/resources/js/common.js" />"></script>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>



<title><tiles:getAsString name="title" /></title>

</head>
<body onload="pageLoader(false)">


	<div id="container">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="body">
			<tiles:insertAttribute name="body" />
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>

	<div id="pageLoader" class="loading"></div>
	<div id="pageLoaderAPI" class="loading" style="display: none;"></div>

	<!-- /container -->

</body>
</html>