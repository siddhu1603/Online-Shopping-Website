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
            function checkandplaceorder() {
                //alert(document.querySelector("input[type='radio'][name=btnradio]:checked").value);
                //alert(document.querySelector("input[type='radio'][name=btnwall]:checked").value);
                var wamt = document.querySelector("input[type='radio'][name=btnwall]:checked").value;
                var wamt = wamt.slice(wamt.indexOf('-') + 1);
                //alert(wamt);
                if (wamt > parseFloat(document.frmbuy.hcarttotal.value.replace(/,/g, ''))) {
                    document.frmbuy.submit();
                } else {
                    alert('insufficient amount in the selected wallet.');
                    return false;
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
                <myheader>Place Order</myheader>
                <form name="frmbuy" id="frmbuy" method="post" action="placeOrder">
                    <h5 style="padding:16px;">Cart Amount: &#x20b9;${cartamount}</h5>
                    <input type="hidden" name="hcarttotal" value="${cartamount}">
                    <div style="display: flex;flex-direction: row;padding:8px;">

                        <div style="padding:8px;min-width:250px;">
                            <h5>Select Address</h5>
                            <div class="btn-group-vertical" role="group" aria-label="Basic radio toggle button group">
                                <c:forEach items="${addr}" var="address" varStatus="status">
                                    <input type="radio" class="btn-check" name="btnradio" id="${address.getAlias_name()}" autocomplete="off" <c:if test="${status.count == 1}"><c:out value = "${'checked'}" /></c:if> value="${address.getSlno()}">
                                    <label class="btn btn-outline-primary" for="${address.getAlias_name()}"><b>${address.getAlias_name()}</b><p align="left">${address.getAddress()}</p></label>
                                </c:forEach>
                            </div>
                        </div>
                        <div style="padding:8px;">
                            <h5>Select Wallet</h5>
                            <div class="btn-group-vertical" role="group" aria-label="Basic radio toggle button group">
                                <c:forEach items="${wall}" var="wallet" varStatus="status">
                                    <input type="radio" class="btn-check" name="btnwall" id="${wallet.getWallet_name()}" autocomplete="off" <c:if test="${status.count == 1}"><c:out value = "${'checked'}" /></c:if> value="${wallet.getSlno()}-${wallet.getAmount()}">
                                    <label class="btn btn-outline-primary" for="${wallet.getWallet_name()}"><b>${wallet.getWallet_name()}</b><br>Amount: &#x20b9; ${wallet.getAmountstring()}<br>Last Used on:${wallet.getLastusedt()}</label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div>
                        <!--<input type="button" value="Check" onclick="check();">-->
                        <button class="btn btn-primary mb-3" type="button" onclick="checkandplaceorder();">Place Order</button>
                    </div>
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

