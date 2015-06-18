$(document).ready(function() {
    var sqlArea = $("#sqlArea").hide();
    $("#AddContent\\.placeholder").attr('checked', 'checked');
    $("#AddContent\\.label").attr('checked', 'checked');
    $("#AddContent\\.tip").attr('checked', 'checked');
    $("input[type=text]").blur(function() {
        callCreateSQL();
    });
    $("input[type=checkbox]").change(function() {
        callCreateSQL();
    });
    $("#showSQL-button").click(function(){
        sqlArea.toggle();
    });
});

function callCreateSQL() {
    $.post(sAddress + '/' + pageName + '/createSQL.htm', $("form").serialize(), function(response) {
        $("#sql").val($.parseJSON(response));
    });
}