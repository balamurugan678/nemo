$(document).ready(function () {
	$(".nav-toggle").each(function() {
		var collapse_content_selector = $(this).attr('href');
		$(collapse_content_selector).hide();
	});

	$(".nav-toggle").on("click", function(e) {
		var collapse_content_selector = $(this).attr('href');
		var toggle_switch = $(this);
		
		$(collapse_content_selector).toggle();
		if ($(collapse_content_selector).is(":visible")) {
			$(toggle_switch).html(toggleHideLabel);
		} else {
			$(toggle_switch).html(toggleShowLabel);
		}
		return false;
	});
});
