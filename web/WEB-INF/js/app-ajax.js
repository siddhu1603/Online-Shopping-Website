$(document).ready(function () {
    $('#userid').blur(function () {
        $.ajax({
            url: 'validateUID',
            data: {
                UID: $('#userid').val(),
                MYPARAM: "USERID"
            },
            success: function (responseText) {
                if (responseText.substring(0, 1) === "F") {
                    $('#sysmsgUID').text(responseText.substring(1));
                    $('#huid').val('F');
                } else {
                    $('#sysmsgUID').text("");
                    $('#huid').val('T');
                }
            }
        });
    });

    $('#pwd').blur(function () {
        $.ajax({
            url: 'validateUID',
            data: {
                PWD: $('#pwd').val(),
                MYPARAM: "PWD"
            },
            success: function (responseText) {
                if (responseText.substring(0, 1) === "F") {
                    $('#sysmsgPWD').text(responseText.substring(1));
                    $('#hpwd').val('F');
                } else {
                    $('#hpwd').val('T');
                    $('#sysmsgPWD').text("");
                }
            }
        });
    });

    $('#username').blur(function () {
        $.ajax({
            url: 'validateUID',
            data: {
                UNAME: $('#username').val(),
                MYPARAM: "USERNAME"
            },
            success: function (responseText) {
                if (responseText.substring(0, 1) === "F") {
                    $('#hcust_name').val('F');
                    $('#sysmsgNAME').text(responseText.substring(1));
                } else {
                    $('#sysmsgNAME').text("");
                    $('#hcust_name').val('T');
                }
            }
        });
    });
    
    //MOBILE
    $('#mobile').blur(function () {
        $.ajax({
            url: 'validateUID',
            data: {
                mobile: $('#mobile').val(),
                MYPARAM: "MOBILE"
            },
            success: function (responseText) {
                if (responseText.substring(0, 1) === "F") {
                    $('#hmob').val('F');
                    $('#sysmsgMOB').text(responseText.substring(1));
                } else {
                    $('#sysmsgMOB').text("");
                    $('#hmob').val('T');
                }
            }
        });
    });
    
    //EMAIL
    $('#email').blur(function () {
        $.ajax({
            url: 'validateUID',
            data: {
                email: $('#email').val(),
                MYPARAM: "EMAIL"
            },
            success: function (responseText) {
                if (responseText.substring(0, 1) === "F") {
                    $('#hemail').val('F');
                    $('#sysmsgEMAIL').text(responseText.substring(1));
                } else {
                    $('#sysmsgEMAIL').text("");
                    $('#hemail').val('T');
                }
            }
        });
    });

    $('#frmreg').on('submit', function (e) {
        if ($('#huid').val() === 'F') {
            $('#userid').focus();
            e.preventDefault();
            return false;
        }

        if ($('#hpwd').val() === 'F') {
            $('#pwd').focus();
            e.preventDefault();
            return false;
        }
        if ($('#hcust_name').val() === 'F') {
            $('#cust_name').focus();
            e.preventDefault();
            return false;
        }
        
        if ($('#hemail').val() === 'F') {
            $('#email').focus();
            e.preventDefault();
            return false;
        }
        
        if ($('#hmob').val() === 'F') {
            $('#mobile').focus();
            e.preventDefault();
            return false;
        }
        
        if($('#gender').val() === 'X'){
            $('#sysmsgGENDER').text("Select Gender from list.");
            $('#gender').focus();
            e.preventDefault();
            return false;
        }
    });
});


 