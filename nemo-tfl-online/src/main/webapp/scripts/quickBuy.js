$(document).ready(
			function() {
				$(".box").css("width", "750px");
				setLineNoIfDeleteTravelCardClicked();	
});
	
function setLineNoIfDeleteTravelCardClicked() {
		$(".delete").each(function(){
			$(this).click(function(){
				setLineNoFromDeleteIdValue($(this).attr('id'));
				return true;
			});
});
}

function setLineNoFromDeleteIdValue(idValue) {
		idValue = idValue.replace("delete", "");
		$("#lineNo").val(idValue);
}