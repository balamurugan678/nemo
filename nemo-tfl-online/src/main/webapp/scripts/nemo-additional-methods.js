/**
 * Add jQuery validator plugin custom methods
 */

/* Expiry date format validation */
jQuery.validator.addMethod('expirydate', function (value, element) {
    var valueWithHyphenInsteadOfSlash = replaceSlashWithHyphenInString(value);
    if (isValidExpiryDate(valueWithHyphenInsteadOfSlash)) {
        element.value = valueWithHyphenInsteadOfSlash;
        return isPaymentCardExpiryDateValidAndWithinRange(valueWithHyphenInsteadOfSlash,element);
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
function isPaymentCardExpiryDateValidAndWithinRange(value, element) {
	var dateOfExpiry= value;
	var currentDate = getFormattedCurrentDate();
	var allowedFutureExpiryDate= getAllowedFutureExpiryDate();
	
	if(getSeperatorIndex(dateOfExpiry)==-1)
		return false; 
	
	var expDateArr=splitExpiryDate(dateOfExpiry); 
	if(expDateArr.length>2)
		return false; 

	if(isExpiryDateValid(expDateArr[0]))
		return false;

	expiryYear=expDateArr[1];
	dateOfExpiry=getFormattedExpiryDate(expiryYear,expDateArr[0]);
	
	return (isExpiryDateInFuture(currentDate, dateOfExpiry) && isExpiryDateWithinAllowedFutureDate(dateOfExpiry, allowedFutureExpiryDate));
};

function getFormattedCurrentDate(){
	var today = new Date();
	return new Date(today.getFullYear(),today.getMonth(),1,0,0,0,0);
}

function getAllowedFutureExpiryDate(){
	var today = new Date();
	return new Date(today.getFullYear()+15,today.getMonth(),1,0,0,0,0);
}

function getFormattedExpiryDate(expiryYear, expiryMonth){
	return new Date(eval(expiryYear),(eval(expiryMonth)-1),1,0,0,0,0);
}

function getSeperatorIndex(dateOfExpiry){
	return dateOfExpiry.indexOf('-');
}

function splitExpiryDate(dateOfExpiry){
	return dateOfExpiry.split('-');
}

function isExpiryDateValid(dateOfExpiry){
	return eval(dateOfExpiry)<1 || eval(dateOfExpiry)>12;
}

function isExpiryDateWithinAllowedFutureDate(dateOfExpiry, allowedFutureExpiryDate){
	return Date.parse(dateOfExpiry) <= Date.parse(allowedFutureExpiryDate);
}

function isExpiryDateInFuture(currentDate, dateOfExpiry){
	return Date.parse(currentDate) <= Date.parse(dateOfExpiry);
}