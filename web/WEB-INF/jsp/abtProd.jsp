<%
    if (!session.getAttribute("USERTYPE").toString().equals("S")) {
%>
<jsp:forward page="errorMsg.jsp">
    <jsp:param name="sysmsg" value="You must login as a seller first."></jsp:param>
</jsp:forward>
<%
    }
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                $('#prodid').focus();
                $('#prodid').blur(function () {
                    //$('#sysmsg').text("");
                    if ($('#prodid').val() === 'X') {
                        $('#sysmsgPRODID').text("Select Product from list.");
                        $('#hprod').val('F');
                    } else {
                        $('#sysmsgPRODID').text("");
                        $('#hprod').val('T');
                    }
                });
                $('#prodid').change(function () {

                    $('#sysmsg').html("");
                    if ($('#prodid').val() !== 'X') {
                        $('#sysmsg').html("loading...");
                        $('#sysmsgPRODID').text("");
                        $.ajax({
                            url: 'displayProdAbout',
                            data: {
                                prodid: $('#prodid').val()
                            },
                            success: function (responseText) {
                                //alert(responseText);
                                $('#sysmsg').html(responseText);
                            }
                        });
                    } else {
                        $('#sysmsg').html("");
                    }
                });

                $('#frmprodabt').on('submit', function (e) {
                    if ($('#hprod').val() === 'F') {
                        $('#prodid').focus();
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

                <myheader>About Product</myheader>
                <form name="frmprodabt" id="frmprodabt" method="POST" action="insAbout">
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
                    <br>
                    <div class="mb-1">
                        <label for="inputDesc" class="form-label">Description</label>
                        <div class="divfrm" >
                            <div class="form-outline w-50">
                                <input type="text" class="form-control" maxlength="500" id="txtabt" name="txtabt" placeholder="About the product" required>
                            </div>
                        </div>                
                    </div>

                    <br>
                    <button id="btn" name="btn" type="submit" class="btn btn-primary mb-3">Submit</button>

                    <div id="sysmsg"><%=request.getParameter("msg") == null ? "" : request.getParameter("msg").equals("T") ? "Successfully added" : "Unable to add details."%></div>
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
