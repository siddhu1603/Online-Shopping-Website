<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>      
        <script type="text/javascript" src="js/jquery-3.7.0.min.js"></script>
        <script type="text/javascript" src="js/cap-ajax.js"></script>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <style>
            .logindiv{
                width: 300px;
                text-align: left;
                padding: 4px 16px;
                border: 1px solid lightgray;
                /*background-color: lightsteelblue;*/
                border-radius: 10px;
            }
            .logindiv:hover{
                box-shadow: 0 0 11px rgba(33,33,33,.4);
                border: 1px solid #bbb;
            }
            #sysmsg{
                color: darkred;
                height: 20px;
                vertical-align: top;
            }
            .form-label{
                margin-top: 0.5rem;
                margin-bottom: 0.2rem;
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
                    String valid = (String) request.getAttribute("isvalid");
                    if (session.getAttribute("UID") == null) {
                %>
                <div class="m-4 logindiv">
                    <myheader>Sign In</myheader>
                    <form id="frmlogin" action="doLogin" method="post">
                        <div class="mb-333">
                            <label class="form-label" for="inputUserID">User ID:</label>
                            <input type="text" class="form-control" id="txtuid" name="txtuid" placeholder="User ID" required>
                        </div>
                        <div class="mb-333">
                            <label class="form-label" for="inputPassword">Password</label>
                            <input type="password" class="form-control" id="txtpwd" name="txtpwd" placeholder="Password" required>
                        </div> 

                        <div class="mb-333">
                            <label class="form-label" for="inputPassword">Login as</label>
                            <!-- <div class="form-outline w-25">-->
                            <select size="1" id="logintype" name="logintype" class="form-control">
                                <option selected value="C">Customer</option>
                                <option value="A">Admin</option>
                                <option value="S">Seller</option>
                            </select>
                            <!--</div>-->
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="inputCaptcha">Captcha</label>

                            <div class="divfrm">
                                <div class="form-outline w-50">
                                    <input type="text" class="form-control" id="txtcap" name="txtcap" required>
                                </div>
                                <div class="form-outline w-50" align="center"><img title="captcha" src="images/captcha/<%=session.getAttribute("fname")%>.jpg"></div>
                            </div>
                            <input type="hidden" id="hcap" name="hcap">
                            <div id="sysmsg"><%=(valid != null && valid.equalsIgnoreCase("false")) ? "<h7>Invalid credential or user is not active</h7>" : ""%></div>
                        </div>

                        <div class="mb-3" align="center">
                            <button type="submit" class="btn btn-primary">Sign in</button>
                        </div>
                    </form>

                    <div class="mb-3">
                        New Customer? <a href="register">Register</a>
                    </div>
                </div>      
                <%
                        //System.out.println("11");
                    } else {
                        response.sendRedirect("home");
                    }
                %>
            </div>
            <div class="bodyright">
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
