<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">
$(document).ready(function () {
	$("#addPendingButton").click(function(){callAddPending();});
	$("#prestigeId").change(function(){callGetCard();});
});

function callAddPending(){
	$.post( "station.htm", { 
		    prestigeId: $("#prestigeId").val(), 
			stationId: $("#stationId").val()})
	  .done(function( data ) {
		callGetCard();
	    alert( "Data Loaded: " + data );
	  });
}	
function callGetCard(){
	if ($("#prestigeId").val().length > 11) {
		$.post( "station.htm", { 
				targetAction: "getCard",
	 		    prestigeId: $("#prestigeId").val()})
		  .done(function( data ) {
			  $("#cardOutput").val(data);
	  	});
	}
}


</script>


<h1>Station</h1>
<form:form  action="station.htm" modelAttribute="stationCmd">
	<p>
	Pick up all pending items for a card at a given station.
	<br /> 
	Provide the PrestigeId and the StationId
	</p>
    <div style="clear: both; float: left;">
    	<label for="prestigeId">Prestige Id</label>
    	<form:input path="prestigeId" />
    	<div style="clear: both;"></div>
    	<label for="prestigeId">Station Id</label>
    	<form:input path="stationId" />
    	<br />
    	<button type="button" name="targetAction" value="addPending" id="addPendingButton">Pickup all Pending Items</button>
    </div>
    <div class="clear"></div>
    <div id="message" >
    	<h4>Card Details</h4>
    	<textarea id="cardOutput"></textarea>
    </div>
        
    
</form:form>