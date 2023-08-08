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
                <myheader>Shopping Cart</myheader>
                <hr>
                <table width="100%">
                    <c:forEach items="${ocp}" var="cp" varStatus="status">
                        <c:set var="cnt" scope="session" value="${status.count}"/>  

                        <tr style="border-bottom: 1px solid lightgray;">
                            <td style="width:250px;"><img src="${cp.getPath()}${cp.getFilename()}" width="200"  alt="alt"/></td>
                            <td>
                                <h5>${cp.getProd_desc()} </h5>
                                <b>&#x20b9; ${cp.getSellingprice()}</b> &#x20b9;<span style="text-decoration: line-through;">${cp.getUnitprice()}</span>(<span style="color:darkred;font-weight: bold;font-size: 18px;">${cp.getDiscount()}%</span> Discount)<br><br>
                                <table border="0">
                                    <tr><td>Variant</td><td>:</td><td>${cp.getVariant()}</td></tr>
                                    <tr><td>Quantity</td><td>:</td><td>${cp.getQty()}</td></tr>
                                    <tr><td>Category</td><td>:</td><td>${cp.getMaincat()}/${cp.getSubcat()}</td></tr>
                                    <tr><td>Sold By</td><td>:</td><td>${cp.getSellername()}</td></tr> 
                                    <tr><td colspan="3"><a href="deleteCart?slno=${cp.getCartslno()}&prodid=${cp.getProdid()}">Delete</a></td></tr>
                                </table>
                            </td>
                        </tr>                    
                    </c:forEach>
                        <tr><td></td><td align="right"><h5>Subtotal(${cnt}): &#x20b9; ${totval}</h5></td></tr>
                        <tr>
                            <td></td>
                            <td align="right">
                                <form action="proceedToBuy" name="frmbuy" id="frmbuy" method="post">
                                    <button class="btn btn-primary mb-3" type="submit">Proceed to Buy</button>
                                </form>                                
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
