<%@page import="com.shopping.model.OrderDetails"%>
<%@page import="java.util.List"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
        <style type="text/css">
            npouter{
                display: flex;
                flex-direction: row;
                flex-grow: 1;
                min-height: 200px;
                border:1px solid lightgray;
                margin:8px;
                border-radius: 10px;
                background-color: white;
            }
            npouter:hover{
                box-shadow: 0 0 11px rgba(33,33,33,.4);
                border: 1px solid #bbb;
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
            /*.bodymain{
                background-color: lightsteelblue;
            }*/
            .bodyrighttop{
                border-radius: 10px!important;
                margin-top: 8px;
            }
        </style>
    </head>
    <body>
        <%
            String msg=request.getAttribute("msg")==null?"":request.getAttribute("msg").toString() ;
        %>
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
                <myheader>My Orders</myheader>
                <div id="reviewmsg" style="color:orangered;padding-left: 10px;font-size: medium;font-weight: 600;"><%=msg%></div>
                <%
                    DemoService ds = new DemoService();
                    List<String> ordlist = ds.getOrderidList(session);
                    for (int j = 0; j < ordlist.size(); j++) {
                        List<OrderDetails> od = ds.getOrderDetails(ordlist.get(j));
                %>
                <div style="border-radius:10px;border:1px solid darkslategray;background-color: lightslategray;margin:8px;padding:6px;">
                    <h6  style="color:white;"><%=ordlist.get(j)%></h6>

                    <%
                        for (int i = 0; i < od.size(); i++) {
                    %>                       
                    <h6 style="color: aliceblue;">Item No : <%=od.get(i).getOrderid()%>#<%=od.get(i).getItemno()%></h6>
                    <npouter>
                        <npleft>
                            <img src="<%=od.get(i).getPath()%><%=od.get(i).getFilename()%>" alt="" style="max-width: 200px;max-height: 200px;padding:4px;">
                            <form name="frmreview" id="frmreview" method="POST" action="prodReview">
                                <input type="hidden" name="hprodid" id="hprodid" value="<%=od.get(i).getProdid()%>">
                                <button class="btn btn-light" type="submit" style="margin-left: 8px;padding:8px;border-color: #eee;color: #0d6efd;">Write a Product Review</button>
                            </form>
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
                                    <tr><td>Status</td><td>:</td><td style="padding-left: 4px;color:blue;"><%=od.get(i).getStatus()%>(Date - <%=od.get(i).getStatusdt()%>)</td></tr>
                                    <tr><td style="vertical-align: top;">Address</td><td style="vertical-align: top;">:</td><td style="padding-left: 4px;"><%=od.get(i).getAddress()%></td></tr>
                                </table>
                            </npdetail>
                        </npright>
                    </npouter>
                    <% } %>
                </div>
                <% }%>                 
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
