/**
 * Add jQuery validator plugin custom methods
 */

/* Expiry date format validation */
jQuery.validator.addMethod('expirydate', function (value, element) {
    var valueWithHyphenInsteadOfSlash = replaceSlashWithHyphenInString(value);
    if (isValidExpiryDate(valueWithHyphenInsteadOfSlash)) {
        element.value = valueWithHyphenInsteadOfSlash;
        return true;
    }
    return false;
});

/* Luhn check digit validation */
jQuery.validator.addMethod('luhnCardNumber', function (value, element) {
    var strippedValue = stripNonDigitsFromString(value);
    if (isValidLuhn(strippedValue)) {
        element.value = strippedValue;
        return true;
    }
    return false;
});

/* Payment Card Expiry date - Restricting Expiry date in the past and future*/
jQuery.validator.addMethod(
		"cardexpirydate",
		function (value, element) {
			var expDate = value;
			var today = new Date();
			var currentDate = new Date(today.getFullYear(),today.getMonth(),1,0,0,0,0);
			var futureLimitDate= new Date(today.getFullYear()+15,today.getMonth(),1,0,0,0,0);
			
			var separatorIndex = expDate.indexOf('-');
			if(separatorIndex==-1)return false; 
			var expDateArr=expDate.split('-'); 
			if(expDateArr.length>2)return false; 

			if(eval(expDateArr[0])<1||eval(expDateArr[0])>12)
			{
				return false;
			}

			expYearCheck=expDateArr[1];
			expDate=new Date(eval(expYearCheck),(eval(expDateArr[0])-1),1,0,0,0,0);
			if(Date.parse(currentDate) <= Date.parse(expDate))
			{
				return (Date.parse(expDate) <= Date.parse(futureLimitDate));
			}
			return false;
		}
);