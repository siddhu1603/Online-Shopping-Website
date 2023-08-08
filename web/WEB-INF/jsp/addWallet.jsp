<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
        <style type="text/css">
            
            .outer{
                display: inline-flex;
                flex-direction: row;
            }
            .left{
                min-width:500px;
                width:50%;
                text-align:left;
            }
            .right{
                flex-grow: 1;
            }
            h5{
                color: #0d6efd;
            }
        </style>
        <script type="text/javascript">
            function listchangerequest(val) {
                if (val !== 'X') {
                    window.location = val;
                }
            }
        </script>
        <script type="text/javascript" src="js/jquery-3.7.0.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#wlist').html("loading...");
                $.ajax({
                    url: 'displayWallets',
                    data: {},
                    success: function (responseText) {
                        $('#wlist').html(responseText);
                    }
                });

                $('#amount').blur(function () {
                    $.ajax({
                        url: 'validateUID',
                        data: {
                            pincode: $('#amount').val(),
                            MYPARAM: "AMOUNT"
                        },
                        success: function (responseText) {
                            if (responseText.substring(0, 1) === "F") {
                                $('#hamt').val('F');
                                $('#sysmsgpin').text(responseText.substring(1));
                            } else {
                                $('#sysmsgpin').text("");
                                $('#hamt').val('T');
                            }
                        }
                    });
                });

                $('#frmw').on('submit', function (e) {
                    if ($('#hamt').val() === 'F') {
                        $('#amount').focus();
                        e.preventDefault();
                        return false;
                    }
                });
            });
        </script>
    </head>
    <body>
    <outer>
        <cheader>
            <jsp:include page="pgheader.jsp" flush="true" />
        </cheader>
        <cmenu>
            <jsp:include page="pgmenu.jsp" flush="true" />
        </cmenu>
        <cbody>
            <div class="bodyleft">
                <jsp:include page="leftmenu.jsp" flush="true"/>
            </div>
            <div class="bodymain">
                <%
                    String msg = (String) request.getAttribute("retval");
                %>
                <div class="outer">
                    <div class="left">
                        <div class="m-4" style="width:500px;text-align:left;">
                            <myheader>Add Wallet</myheader>
                            <form name="frmw" id="frmw" method="POST" action="addWalt">
                                <div class="mb-1">
                                    <label for="inputwname" class="form-label">Name</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-25">
                                            <input type="text" class="form-control" id="wallet_name" placeholder="home" name="wallet_name" required>
                                        </div>
                                    </div>                
                                </div>
                                <div class="mb-1">
                                    <label for="inputamt" class="form-label">Initial Amount</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-25">
                                            <input type="text" class="form-control" id="amount" placeholder="99999.99" name="amount" maxlength="8" required>
                                        </div>
                                        <div id="sysmsgpin" style="padding:4px 8px;"></div>
                                    </div>                
                                    <input type="hidden" id="hamt" name="hamt">
                                </div>                            

                                <div class="col-auto" style="padding-top: 8px;">
                                    <button id="btn" name="btn" type="submit" class="btn btn-primary mb-3">Add Wallet</button>
                                </div>
                                <div id="sysmsg"><%=(msg != null) ? "<h6>" + msg + "</h6>" : ""%></div>
                            </form>
                        </div>
                    </div>
                    <div class="right m-4">
                        <h5>Existing Wallet</h5>
                        <div id="wlist"></div>
                    </div>
                </div>
            </div>
            <div class="bodyright">
                <div class="bodyrighttop card text-bg-info mb-1">                    
                    <jsp:include page="userdet.jsp" flush="true" />
                </div>
                <div class="bodyrightbottom">

                </div>
            </div>
        </cbody>
        <footer>
            <jsp:include page="footer.jsp" flush="true" />
        </footer>
    </outer>
</body>
</html>
