/* jQuery constant for short_date_format */
var shortDatePattern = "dd/mm/yy";

//String trim function
function trim(myString) {
    return myString.replace(/(?:(?:^|\n)\s+|\s+(?:$|\n))/g, '').replace(/\s+/g,' ');
}

// adds endsWith method to String type
if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

function returnDatePickerSettings(){
    return {
        maxDate: new Date(),
        dateFormat: 'dd/mm/yy'
     };
}
/* innovator url, description of page/tab, hover hint, innovator image to load, tab name*/
function openLink(url, description, hint, image, name){
    if (typeof(window.parent.showTab) != "undefined") {
        window.parent.showTab(url, description,  hint, "", image, name, true ,"" ,false);
    } else {
        window.location = url;    
    }
}

addOptionsValueAndText = function(opts, container, value, text) {
    $.each(opts, function(i, opt) {
        if (typeof (opt) == 'object') {
            container.append($("<option />").val($(opt).prop(value)).text($(opt).prop(text)));
        }else if (typeof (opt) == 'string') {
            container.append($("<option />").val(opt).text(opt));
        } else {
            var optgr = $("<optgroup />").attr('label', i);
            addOptionsValueAndText(opt, optgr);
            container.append(optgr);
        }
    });
};

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "currency-pre": function ( a ) {
        a = (a==="-") ? 0 : a.replace( /[^\d\-\.]/g, "" );
        return parseFloat( a );
    },
 
    "currency-asc": function ( a, b ) {
        return a - b;
    },
 
    "currency-desc": function ( a, b ) {
        return b - a;
    }
} );

function toggleLoading(results){
    $("#loading-icon").toggle();
    if (results == 0) {
        $("#messageArea").html("No results found");
    } else if( results > 0 || results == -2){
        $("#messageArea").html("");
    } else {
        $("#messageArea").html("Loading");
    }
}

function showPageMessage(){
    if ( typeof message != 'undefined' && message != '') {
        alert(message);
    }
}

$(document).ready(function () {
    showPageMessage();
    
});