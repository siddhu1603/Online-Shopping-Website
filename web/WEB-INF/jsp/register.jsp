<%-- 
    Document   : register
    Created on : 28 Jul, 2023, 10:54:02 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
        <!-- Include jQuery -->
        <script type="text/javascript" src="js/jquery-3.7.0.min.js"></script>
        <!-- Include Date Range Picker -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>

        <script type="text/javascript" src="js/app-ajax.js"></script>
        <script type="text/javascript">
            /*function listchangerequest(val) {
                if (val !== 'X') {
                    window.location = val;
                }
            }*/
        </script>
    </head>
    <body>
        <script>
            $(document).ready(function () {
                var date_input = $('input[name="dob"]'); //our date input has the name "date"
                var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
                date_input.datepicker({
                    format: 'dd/mm/yyyy',
                    container: container,
                    todayHighlight: true,
                    autoclose: true
                });
            });
        </script>
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
                    String msg = (String) request.getAttribute("sysmsg");
                %>
                <div align=left>

                    <div class="m-4" style="width:700px;text-align:left;">
                        <myheader>Register!</myheader>
                        <form name="frmreg" id="frmreg" class="row g-1"  method="post" action="createUser">

                            <div class="mb-1">
                                <label for="inputuserid" class="form-label">User ID</label>
                                <div class="divfrm" >
                                    <div class="form-outline w-25">
                                        <input type="text" class="form-control" id="userid" placeholder="userid" name="userid" required>
                                    </div>
                                    <div id="sysmsgUID" style="padding:4px 8px;"></div>
                                </div>                
                                <input type="hidden" id="huid" name="huid">
                            </div>

                            <div class="mb-1">
                                <label for="inputpassword" class="form-label">Password</label>                        
                                <div class="divfrm">
                                    <div class="form-outline w-25">
                                        <input type="password" class="form-control" id="pwd" placeholder="Password" name="pwd" required>
                                    </div>
                                    <div id="sysmsgPWD" style="padding:4px 8px;"></div>
                                </div>                                               
                                <input type="hidden" id="hpwd" name="hpwd">
                            </div>

                            <div class="mb-333">
                                <label class="form-label" for="inputPassword">Register as</label>
                                <div class="form-outline w-25">
                                    <select size="1" id="custtype" name="custtype" class="form-control">
                                        <option selected value="C">Customer</option>
                                        <option value="A">Admin</option>
                                        <option value="S">Seller</option>
                                    </select>
                                </div>
                            </div>

                            <div class="mb-1">
                                <label for="inputname" class="form-label">Full Name</label>
                                <div class="divfrm">
                                    <div class="form-outline w-50">
                                        <input type="text" class="form-control" id="username" placeholder="Full Name of employee" name="username" required>
                                    </div>
                                    <div id="sysmsgNAME" style="padding:4px 8px;"></div>
                                </div>                         
                                <input type="hidden" id="hcust_name" name="hcust_name">
                            </div>

                            <div class="mb-1">
                                <label for="inputemail" class="form-label">Email</label>                        
                                <div class="divfrm">
                                    <div class="form-outline w-50">
                                        <input type="email" class="form-control" id="email" placeholder="email@example.com" name="email" required>
                                    </div>
                                    <div id="sysmsgEMAIL" style="padding:4px 8px;"></div>
                                </div>
                                <input type="hidden" id="hemail" name="hemail">                        
                            </div>

                            <div class="mb-1">
                                <label for="inputmobile" class="form-label">Mobile</label>                        
                                <div class="divfrm">
                                    <div class="form-outline w-25">
                                        <input type="text" maxlength="10" class="form-control" id="mobile" placeholder="9876543210" name="mobile" required>
                                    </div>
                                    <div id="sysmsgMOB" style="padding:4px 8px;"></div>
                                </div> 
                                <input type="hidden" id="hmob" name="hmob">                         
                            </div>

                            <div class="mb-1">
                                <label for="example">Date of Birth</label>
                                <div class="divfrm">
                                    <div class="form-outline w-25">
                                        <div id="date-picker-example" class="md-form md-outline input-with-post-icon datepicker" inline="true">
                                            <input placeholder="Select date" type="text" id="dob" name="dob" class="form-control" data-date-end-date="0d" required>
                                            <i class="fas fa-calendar input-prefix"></i>
                                        </div>
                                    </div>
                                    <div id="sysmsgDOB" style="padding:4px 8px;"></div>
                                </div>
                                <input type="hidden" id="hDOB" name="hDOB">
                            </div>

                            <div class="mb-3">
                                <label for="inputgender" class="form-label">Gender</label>
                                <div class="divfrm">
                                    <div class="form-outline w-25">
                                        <select size="1" id="gender" name="gender" class="form-control">
                                            <option selected value="X">(Select)</option>
                                            <option value="M">Male</option>
                                            <option value="F">Female</option>
                                        </select>
                                    </div>
                                    <div id="sysmsgGENDER" style="padding:4px 8px;"></div>
                                </div>
                                <input type="hidden" id="hGENDER" name="hGENDER">
                            </div>

                            <div class="col-auto">
                                <button id="btn" name="btn" type="submit" class="btn btn-primary mb-3">Register</button>
                            </div>
                        </form>
                        <div id="sysmsg"><%=(msg != null) ? "<h6>" + msg + "</h6>" : ""%></div>
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

