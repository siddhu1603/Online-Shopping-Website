<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">      
        <script type="text/javascript" src="js/jquery-3.7.0.min.js"></script>
        <script type="text/javascript" src="js/app-ajax.js"></script>
        <!-- Include Date Range Picker -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>

        <script type="text/javascript">
            function listchangerequest(val) {
                if (val !== 'X') {
                    window.location = val;
                }
            }
        </script>
        <style type="text/css">
            .col-form-label{
                min-width: 100px;
            }
        </style>
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
                <div class="m-4" style="text-align:left;">
                    <myheader>Update Profile</myheader>
                    <form name="frmreg" id="frmreg" class="row g-1"  method="post" action="updtUser">                        
                        <div class="row g-2 align-items-center">                            
                            <div class="col-auto">
                                <label for="inputname" class="col-form-label">User ID</label>
                            </div>
                            <div class="col-auto">
                                <input type="text" class="form-control" id="userid" placeholder="userid" name="userid" value='<%=session.getAttribute("UID")%>' disabled="">
                            </div>
                        </div>

                        <div class="row g-2 align-items-center">    
                            <div class="col-auto">
                                <label for="inputname" class="col-form-label">Full Name</label>
                            </div>
                            <div class="col-auto">
                                <input type="text" class="form-control" id="cust_name" placeholder="Full Name of employee" name="cust_name" value='<%=session.getAttribute("USERNAME")%>' required>                                   
                            </div>
                            <div class="col-auto">
                                <div id="sysmsgNAME" style="padding:4px 8px;"></div>
                                <input type="hidden" id="hcust_name" name="hcust_name">
                            </div>
                        </div>

                        <div class="row g-2 align-items-center">    
                            <div class="col-auto">
                                <label for="inputemail" class="col-form-label">Email</label>
                            </div>
                            <div class="col-auto">
                                <input type="email" class="form-control" id="email" placeholder="email@example.com" name="email" value="<%=session.getAttribute("USEREMAIL")%>" required>
                            </div>
                            <div class="col-auto">
                                <div id="sysmsgEMAIL" style="padding:4px 8px;"></div>
                                <input type="hidden" id="hemail" name="hemail">
                            </div>
                        </div>

                        <div class="row g-2 align-items-center">    
                            <div class="col-auto">
                                <label for="inputmobile" class="col-form-label">Mobile</label>
                            </div>
                            <div class="col-auto">
                                <input type="text" maxlength="10" class="form-control" id="mobile" placeholder="9876543210" name="mobile" value="<%=session.getAttribute("USERMOBILE")%>" required>
                            </div>
                            <div class="col-auto">
                                <div id="sysmsgMOB" style="padding:4px 8px;"></div>
                                <input type="hidden" id="hmob" name="hmob">
                            </div>
                        </div>


                        <div class="row g-2 align-items-center">    
                            <div class="col-auto">
                                <label for="inputDOB" class="col-form-label">Date of Birth</label>
                            </div>
                            <div class="col-auto">
                                <div class="form-outline w-75">
                                    <div id="date-picker-example" class="md-form md-outline input-with-post-icon datepicker" inline="true">
                                        <input value='<%=session.getAttribute("USERDOB")%>' placeholder="Select date" type="text" id="dob" name="dob" class="form-control" data-date-end-date="0d" required>
                                        <i class="fas fa-calendar input-prefix"></i>
                                    </div>
                                </div>
                            </div>
                        </div>    

                        <div class="row g-2 align-items-center">    
                            <div class="col-auto">
                                <label for="inputgender" class="col-form-label">Gender</label>
                            </div>
                            <div class="col-auto">
                                <div class="form-outline">
                                    <select size="1" id="gender" name="gender" class="form-control">
                                        <option value="X">(Select)</option>
                                        <option <%=session.getAttribute("USERGENDER").toString().equals("M") ? "selected" : ""%> value="M">Male</option>
                                        <option <%=session.getAttribute("USERGENDER").toString().equals("F") ? "selected" : ""%> value="F">Female</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-auto">
                                <div id="sysmsgGENDER" style="padding:4px 8px;"></div>
                                <input type="hidden" id="hGENDER" name="hGENDER">
                            </div>
                        </div>


                        <div class="col-auto" style="padding:6px 12px;">
                            <button id="btn" name="btn" type="submit" class="btn btn-primary mb-3">Update</button>
                        </div>
                    </form>
                    <div id="sysmsg"><%=(msg != null) ? "<h6>" + msg + "</h6>" : ""%></div>
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
