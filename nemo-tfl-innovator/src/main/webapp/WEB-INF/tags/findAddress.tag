<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="to" tagdir="/WEB-INF/tags" %>

<to:text id="postcode" mandatory="true" />
  <form:errors path="postcode" cssClass="field-validation-error"/>
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
		            if(isNotValidationErrorCheck(response)){
			            var obj = $.parseJSON(response);
			            var selectoption = "";
				            $.each(obj, function(i) {
				                selectoption += '<option value="'+obj[i]+'">'+obj[i]+'</option>';
			                });
				            if(!isEmptyPostCodeObj(selectoption)){
						         $("#buildingAddress").append(selectoption);
						         $("#address-box").show("slow");
				            } else {
				            	alert('Error: '+lastpostcode+' Postcode does not exist, Please try again');
				            }
		            } else {
		            	alert('Error: '+lastpostcode+' is not a valid Postcode, Please try again');
		            }
		        },
		        error : function(e) {
		            alert('Error: ' +e);
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
	
	function isNotValidationErrorCheck(response){
		return (response.toString().toLowerCase() !== "invalidPostcode.error".toLowerCase()) ? true : false;
	}

	function isEmptyPostCodeObj(postCodeObj){
		return (typeof postCodeObj  === 'undefined' || postCodeObj == null || postCodeObj.length <= 0 || postCodeObj == "" ) ? true : false;
	}
	
});
</script>
<div id="address-box">
    <select id="buildingAddress" size="10">
        
    </select>
</div>
