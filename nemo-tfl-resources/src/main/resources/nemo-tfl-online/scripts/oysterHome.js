    $(document).ready(function () {
		checkTFLOnlineUserAccountDeactivatedOrNot(deactivatedUserAccountVal);
    });
    
    function checkTFLOnlineUserAccountDeactivatedOrNot(deactivatedUserAccountVal) {
    	if(jQuery.type(deactivatedUserAccountVal) === "string") {
    		if(deactivatedUserAccountVal === "true"){ 
    			deactivatedUserAccountVal = false;
    			alert("Your account is Deactivated. You cannot log in");
    		}
    	}
    }