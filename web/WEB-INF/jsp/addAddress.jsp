<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
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
                $('#addrlist').html("loading...");
                $.ajax({
                    url: 'displayAddress',
                    data: {},
                    success: function (responseText) {
                        $('#addrlist').html(responseText);
                    }
                });

                $('#pincode').blur(function () {
                    $.ajax({
                        url: 'validateUID',
                        data: {
                            pincode: $('#pincode').val(),
                            MYPARAM: "PINCODE"
                        },
                        success: function (responseText) {
                            if (responseText.substring(0, 1) === "F") {
                                $('#hpin').val('F');
                                $('#sysmsgpin').text(responseText.substring(1));
                            } else {
                                $('#sysmsgpin').text("");
                                $('#hpin').val('T');
                            }
                        }
                    });
                });

                $('#addrform').on('submit', function (e) {
                    if ($('#hpin').val() === 'F') {
                        $('#pincode').focus();
                        e.preventDefault();
                        return false;
                    }
                });
            });
        </script>
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
                            <myheader>Add Address</myheader>
                            <form name="addrform" id="addrform" method="POST" action="submitAddress">
                                <div class="mb-1">
                                    <label for="inputaddr1" class="form-label">Alias</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-25">
                                            <input type="text" class="form-control" id="alias_name" placeholder="home" name="alias_name" required>
                                        </div>
                                    </div>                
                                </div>

                                <div class="mb-1">
                                    <label for="inputaddr1" class="form-label">Line1</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-50">
                                            <input type="text" class="form-control" id="addr1" placeholder="address line 1" name="addr1" required>
                                        </div>
                                    </div>                
                                </div>

                                <div class="mb-1">
                                    <label for="inputaddr2" class="form-label">Line2</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-50">
                                            <input type="text" class="form-control" id="addr2" placeholder="address line 2" name="addr2">
                                        </div>
                                    </div>                
                                </div>

                                <div class="mb-1">
                                    <label for="inputaddr3" class="form-label">Line3</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-50">
                                            <input type="text" class="form-control" id="addr3" placeholder="address line 3" name="addr3">
                                        </div>
                                    </div>                
                                </div>

                                <div class="mb-1">
                                    <label for="inputcity" class="form-label">City</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-25">
                                            <input type="text" class="form-control" id="city" placeholder="City Name" name="city" required>
                                        </div>
                                    </div>                
                                </div>

                                <div class="mb-1">
                                    <label for="inputpin" class="form-label">Pincode</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-25">
                                            <input type="text" class="form-control" id="pincode" placeholder="500019" name="pincode" minlength="6" maxlength="6"    required>
                                        </div>
                                        <div id="sysmsgpin" style="padding:4px 8px;"></div>
                                    </div>                
                                    <input type="hidden" id="hpin" name="hpin">
                                </div>

                                <div class="mb-1">
                                    <label for="inputstate" class="form-label">State</label>
                                    <div class="divfrm" >
                                        <div class="form-outline w-25">
                                            <input type="text" class="form-control" id="state" placeholder="state Name" name="state" required>
                                        </div>
                                        <div id="sysmsgstate" style="padding:4px 8px;"></div>
                                    </div>                
                                    <input type="hidden" id="hstate" name="hstate">
                                </div>

                                <div class="col-auto" style="padding-top: 8px;">
                                    <button id="btn" name="btn" type="submit" class="btn btn-primary mb-3">Add Address</button>
                                </div>
                                <div id="sysmsg"><%=(msg != null) ? "<h6>" + msg + "</h6>" : ""%></div>
                            </form>
                        </div>
                    </div>
                    <div class="right m-4">
                        <h5>Existing Address</h5>
                        <div id="addrlist"></div>

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
