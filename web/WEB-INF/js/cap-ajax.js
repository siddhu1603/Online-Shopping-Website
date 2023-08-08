$(document).ready(function () {
    $('#txtcap').blur(function () {
        $.ajax({
            url: 'validateCap',
            data: {
                CAP: $('#txtcap').val()
            },
            success: function (responseText) {
                if (responseText.substring(0, 1) === "F") {
                    $('#sysmsg').text(responseText.substring(1));
                    $('#hcap').val('F');
                } else {
                    $('#sysmsg').text("");
                    $('#hcap').val('T');
                }
            }
        });
    });
    
    $('#frmlogin').on('submit', function (e) {
        if ($('#hcap').val() === 'F') {
            $('#txtcap').focus();
            e.preventDefault();
            return false;
        }
    });
 });