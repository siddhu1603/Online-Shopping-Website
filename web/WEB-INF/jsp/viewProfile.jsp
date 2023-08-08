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
                <myheader>User Profile</myheader>
                <table border="0">
                    <tr>
                        <td>User ID</td>
                        <td>:</td>
                        <td><%=session.getAttribute("UID")%></td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td>:</td>
                        <td><%=session.getAttribute("USERNAME")%></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td>:</td>
                        <td><%=session.getAttribute("USEREMAIL")%>
                        </td>
                    </tr>
                    <tr>
                        <td>Date of Birth</td>
                        <td>:</td>
                        <td><%=session.getAttribute("USERDOB")%>
                        </td>
                    </tr>
                    <tr>
                        <td>Mobile</td>
                        <td>:</td>
                        <td><%=session.getAttribute("USERMOBILE")%>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <button id="btn" name="btn" type="button" onClick="location.href='edProfile';" class="btn btn-primary mb-3">Edit</button></td>
                        </td>
                    </tr>
                </table>
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

