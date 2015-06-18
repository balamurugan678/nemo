<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags" %>

<to:text id="postcode"/>
<span class="left"><button id="findAddress" type="button">Find Address</button></span>
<script type="text/javascript">
$(document).ready(function () {
	var lastpostcode = "";
	$("#address-box").hide();
	$("#findAddress").click(function(){
		if (lastpostcode != $("#${pageName}\\.postcode").val()){
			$('#buildingAddress').find('option').remove().end();
		    $.ajax({
		        type : "POST",
		        url : "findAddress/ajax.htm?postcode="+$("#${pageName}\\.postcode").val(),
		        success : function(response) {
		            lastpostcode = $("#${pageName}\\.postcode").val();
		            var obj = $.parseJSON(response);
		            var selectoption = "";
		            $.each(obj, function(i) {
		                selectoption += '<option value="'+obj[i]+'">'+obj[i]+'</option>';
	                });
		            $("#buildingAddress").append(selectoption);
		            $("#address-box").show("slow");
		        },
		        error : function(e) {
		            alert('Error: ' + e);
		        }
		    });
		} else {
			$("#address-box").show("slow");
		}
	});
	$("#buildingAddress").change(function(e){
		var buildingAddress = $('#buildingAddress :selected').val().split(",");
		$("#${pageName}\\.houseNameNumber").val(trim(buildingAddress[0]));
		$("#${pageName}\\.street").val(trim(buildingAddress[1]));
		$("#${pageName}\\.town").val(trim(buildingAddress[2]));
		if (buildingAddress[3] != undefined){
			  $("#${pageName}\\.county").val(trim(buildingAddress[3]));
		}
		$("#country").val("GB");
		$("#address-box").hide("slow");
		
	});
});
</script>
<div id="address-box">
    <select id="buildingAddress" size="10">
        
    </select>
</div>
