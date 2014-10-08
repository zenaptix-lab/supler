$(document).ready(function() {
    $.get('/rest/form1.json', function(data) {
        showForm(data);
    });
});

var formContainer = document.getElementById('form-container');
var form = null;

function showForm(formJson) {
    form = new SuplerForm(formContainer, {
        error_custom_lastNameLongerThanFirstName: function() { return "Last name must be longer than first name!"; },
        error_custom_illegalDateFormat: function() { return "Illegal date format"; }
    });
    form.render(formJson);
}

var feedback = $('#feedback');
feedback.hide();

$('#submit').click(function() {
    var hasErrors = form.validate();

    if (hasErrors) {
        feedback.html('There are client-side validation errors.');
        feedback.show();
    } else {
        $.ajax({
            url: '/rest/form1.json',
            type: 'POST',
            data: JSON.stringify(form.getValue()),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                if (form.processServerFormErrors(data.form_errors)) {
                    feedback.html('There are server-side apply or validation errors');
                } else {
                    feedback.html(data.msg);
                }
                feedback.show();
            }
        });
    }

    return false;
});