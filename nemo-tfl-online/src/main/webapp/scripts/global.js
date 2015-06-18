//String trim function
function trim(myString) {
    return myString.replace(/(?:(?:^|\n)\s+|\s+(?:$|\n))/g, '').replace(/\s+/g, ' ');
}

/**
 Check that value is a correctly formatted payment card expiry date, i.e. format is MM-YYYY
 */
function isValidExpiryDate(value) {
    var dateFormat = /^[0-1][0-9]\-[0-9]{4}$/;
    return dateFormat.test(value);
}

function stripNonDigitsFromString(value) {
    return value.replace(/[^\d]/g, '');
}

function replaceSlashWithHyphenInString(value) {
    return value.replace(/\//g, '-');
}

function isValidLuhn(value) {
    var sum = 0;
    var numDigits = value.length;
    var parity = numDigits % 2;
    for (var i = 0; i < numDigits; i++) {
        var digit = parseInt(value.charAt(i));
        if (i % 2 == parity) digit *= 2;
        if (digit > 9) digit -= 9;
        sum += digit;
    }
    return (sum % 10) == 0;
}

function returnDatePickerSettings() {
    return{
        minDate: "-56d",
        maxDate: "0",
        dateFormat: "dd/mm/yy"
    };
}

/**
 * Call hidePleaseWait on page load
 * See also showPleaseWait(), pleaseWait.jspf and pleaseWait.gif.
 */
function hidePleaseWait() {
    $("#pleaseWait").addClass("hidden");
}

/**
 * Call showPleaseWait in on submit observer
 * See also hidePleaseWait(), pleaseWait.jspf and pleaseWait.gif.
 */
function showPleaseWait() {
    $("#pleaseWait").removeClass("hidden");
}

