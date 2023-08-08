<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            function checkNumber(){
                if(isNaN(document.getElementById("txtmon").value)){
                    alert("Enter Number Only!");
                    document.getElementById("txtmon").focus();
                    return false;
                }  
                if(document.getElementById("walletlist").value === 'X'){
                    alert("Please select a wallet!");
                    document.getElementById("walletlist").focus();
                    return false;
                }
                document.frmaddmoney.submit();
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
                <myheader>Add Money</myheader>
                <form name="frmaddmoney" id="frmaddmoney" action="fillWallet" method="POST">
                    <label class="form-label" for="inputCategory">Select Wallet</label>
                    <div class="form-outline w-25">
                        <select size="1" id="walletlist" name="walletlist" class="form-control">
                            <option selected value="X">(Select)</option>
                            <c:forEach items="${Wlist}" var="Wlist">
                                <option value="${Wlist.getSlno()}">${Wlist.getWallet_name()} - (${Wlist.getAmount()})</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-1">
                        <label for="inputamt" class="form-label">Enter Amount</label>
                        <div class="divfrm" >
                            <div class="form-outline w-25">
                                <input type="text" class="form-control" id="txtmon" placeholder="99999.99" name="txtmon" maxlength="7" required>
                            </div>
                            <div id="sysmsgpin" style="padding:4px 8px;"></div>
                        </div>                
                        <input type="hidden" id="hamt" name="hamt">
                    </div>   

                    <button class="btn btn-primary mb-3" type="button" onclick="checkNumber();">Add Money</button>
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