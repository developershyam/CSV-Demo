$(document).ready(function() {

	$('#tableData').hide();
	$('#noDataFound').hide();
});
$(document).on("keypress", "#searchText", function(e) {
	if (e.which == 13) {
		applyFilter();
	}
});

applyFilter = function() {
	var searchText = $('#searchText').val();
	if (!searchText) {
		return alert("Enter file name");
	}
	pageLoader(true)
	$.get(contextPath + "/getCSVFileInfo?fileName=" + searchText, function(
			response, status) {
		pageLoader(false);
		if (response.data) {
			$('#tableData').show();
			$('#noDataFound').hide();
			$('#tblFileName').text(response.data.fileName);
			$('#tblValidCount').text(response.data.validDealCount);
			$('#tblInvalidCount').text(response.data.invalidDealCount);

		} else {
			$('#tableData').hide();
			$('#noDataFound').show();
		}
	});
}