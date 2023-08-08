<%@page import="java.util.List"%>
<%@page import="com.shopping.model.OrderDetails"%>
<%@page import="com.shopping.service.DemoService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        <style type="text/css">
            npouter{
                display: flex;
                flex-direction: row;
                flex-grow: 1;
                min-height: 200px;
                border:1px solid RGBA(13,202,240,var(--bs-bg-opacity,1));
                margin:2px;
                border-radius: 10px;
                background-color: white;
            }
            npleft{
                width: 200px;
            }
            npright{
                display: flex;
                flex-grow: 1;
                flex-direction: column;
            }
            nptop{
                min-height: 50px;
                border-bottom: 1px solid lightsteelblue;
                color: darkblue;
            }
            npdetail{
                flex-grow: 1;
            }
            npbottom{
                height: 30px;
            }
            .bodymain{
                background-color: lightsteelblue;
            }
            .bodyrighttop{
                border-radius: 10px!important;
                margin-top: 8px;
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
                    DemoService ds = new DemoService();
                    List<OrderDetails> od = ds.getOrderDetails(request.getAttribute("ordid").toString());
                %>
                <myheader>Order placement status</myheader>
                <c:set var = "_ordid" value = "${fn:substring(ordid, 0, 3)}" />
                <c:choose>
                    <c:when test="${_ordid eq 'Ord'}">
                        Congratulations!!!<br>Order successfully place with order id - <b>${ordid}</b>
                        <%
                            for (int i = 0; i < od.size(); i++) {
                        %>                       
                        <h6 style="color:darkblue;">Item :<%=od.get(i).getOrderid()%>#<%=od.get(i).getItemno()%></h6>
                        <npouter>
                            <npleft>
                                
                                <img src="<%=od.get(i).getPath()%><%=od.get(i).getFilename()%>" alt="" style="max-width: 200px;max-height: 200px;padding:4px;">
                            </npleft>
                            <npright>
                                <nptop>
                                    <b><%=od.get(i).getProd_desc()%>(<%=od.get(i).getVariant()%>)</b><br>
                                    <%=od.get(i).getSellername()%>
                                </nptop>
                                <npdetail>
                                    <table border="0">
                                        <tr><td>Variant</td><td>:</td><td style="padding-left: 4px;"><%=od.get(i).getVariant()%></td></tr>
                                        <tr><td>Price</td><td>:</td><td style="padding-left: 4px;">&#x20b9;<%=od.get(i).getUprice()%>(<%=od.get(i).getDiscount()%>%)</td></tr>
                                        <tr><td>Shipping</td><td>:</td><td style="padding-left: 4px;">&#x20b9;<%=od.get(i).getShipping()%></td></tr>
                                        <tr><td>Selling Price</td><td>:</td><td style="padding-left: 4px;">&#x20b9;<%=od.get(i).getSellprice()%></td></tr>
                                        <tr><td>Payment</td><td>:</td><td style="padding-left: 4px;"><%=od.get(i).getPayment()%> - <%=od.get(i).getWalletname()%>(Transaction id #<%=od.get(i).getTranid()%>)</td></tr>
                                        <tr><td>Address</td><td>:</td><td style="padding-left: 4px;"><%=od.get(i).getAddress()%></td></tr>
                                    </table>
                                </npdetail>
                            </npright>
                        </npouter>
                        <%
                            }
                        %>
                    </c:when>    
                    <c:otherwise>
                        Unable to place order<br>${ordid}
                    </c:otherwise>
                </c:choose>
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
