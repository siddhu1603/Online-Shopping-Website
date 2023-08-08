<%
    if (!session.getAttribute("USERTYPE").toString().equals("S")) {
%>
<jsp:forward page="errorMsg.jsp">
    <jsp:param name="sysmsg" value="You must login as a seller first."></jsp:param>
</jsp:forward>
<%
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                $('#prodid').focus();
                $('#prodid').change(function () {
                    $('#sysmsg').html("");
                    if ($('#prodid').val() !== 'X') {
                        $('#speclist').html("loading...");
                        //alert($('#prodid').val());
                        $.ajax({
                            url: 'displaySpec',
                            data: {
                                prodid: $('#prodid').val()
                            },
                            success: function (responseText) {
                                //alert(responseText);
                                $('#speclist').html(responseText);
                            }
                        });
                    } else {
                        $('#speclist').html("");
                    }
                });
                $('#prodid').blur(function () {

                    $.ajax({
                        url: 'validateUID',
                        data: {
                            prodid: $('#prodid').val(),
                            MYPARAM: "SPECPRODID"
                        },
                        success: function (responseText) {
                            if (responseText.substring(0, 1) === "F") {
                                $('#hprod').val('F');
                                $('#sysmsgPRODID').text(responseText.substring(1));
                            } else {
                                $('#sysmsgPRODID').text("");
                                $('#hprod').val('T');
                            }
                        }
                    });
                });

                $('#frmprodspec').on('submit', function (e) {
                    if ($('#hprod').val() === 'F') {
                        $('#prodid').focus();
                        e.preventDefault();
                        return false;
                    }

                });
            });
        </script>
        <style type="text/css">
            .form-label{
                margin-bottom: 1px;
            }
            .form-control::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
                color: lightgray;
                opacity: 1; /* Firefox */
            }

            .form-control:-ms-input-placeholder { /* Internet Explorer 10-11 */
                color: lightgray;
            }

            .form-control::-ms-input-placeholder { /* Microsoft Edge */
                color: lightgray;
            }
            .table{
                max-width: 600px;
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
                    String msg = session.getAttribute("SYSMSG") == null ? "" : session.getAttribute("SYSMSG").toString();
                    session.removeAttribute("SYSMSG");
                %>
                <myheader>Add Product Specification</myheader>

                <form name="frmprodspec" id="frmprodspec" method="POST" action="insProdSpec">

                    <div class="mb-1">
                        <label class="form-label" for="inputCategory">Product</label>
                        <div class="divfrm">
                            <div class="form-outline w-25">
                                <select size="1" id="prodid" name="prodid" class="form-control">
                                    <option selected value="X">(Select)</option>
                                    <c:forEach items="${prod}" var="prodid">
                                        <option value="${prodid.getProdid()}">${prodid.getProd_desc()}(${prodid.getCrtdt()})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div id="sysmsgPRODID" style="padding:4px 8px;"></div><input type="hidden" id="hprod" name="hprod">
                        </div>
                    </div>                

                    <div id="speclist" style="display:inline-block;"></div>

                    <div id="sysmsg"><%=(msg != null) ? "<h6>" + msg + "</h6>" : ""%></div>
                </form>
                
                
                
                
                
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
