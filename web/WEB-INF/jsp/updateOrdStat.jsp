<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
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
                <h4>Update Order Status</h4>
                <form name="stsfrm" id="stsfrm" action="statusUpdated" method="POST">
                    <label class="form-label" for="inputRating">Order ID</label>
                    <div class="form-outline w-25">
                        <input type="text" name="orderid" id="orderid" value="<%=request.getParameter("hoid")%>" readonly class="form-control">
                    </div>
                    <!--Order ID - <input type="text" name="orderid" id="orderid" value="< %=request.getParameter("hoid")%>" readonly><br>-->
                    <label class="form-label" for="inputRating">Item No</label>
                    <div class="form-outline w-25">
                        <input type="text" name="itemno" id="itemno" value="<%=request.getParameter("hitem")%>" readonly class="form-control">
                    </div>
                    <!--Item No - <input type="text" name="itemno" id="itemno" value="< %=request.getParameter("hitem")%>" readonly><br>-->
                    <label class="form-label" for="inputRating">Status</label>
                    <div class="form-outline w-25">
                        <select size="1" id="status" name="status" class="form-control">
                            <option selected value="X">(Select)</option>
                            <c:forEach items="${st}" var="st">
                                <option value="${st.getStatusID()}">${st.getStatus_desc()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label class="form-label" for="inputRating">Remarks</label>
                    <div class="form-outline w-50">
                        <input type="text" name="rmk" id="rmk" class="form-control" maxlength="50">
                    </div>
                    <button class="btn btn-primary" type="submit"  class="form-control" style="margin-top:8px;">Update Status</button>
                    <!-- Remarks - 
                     <input type="text" name="rmk" id="rmk" maxlength="50">
 
  <input type="submit" value="Update">-->
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
