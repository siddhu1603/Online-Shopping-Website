<%@page import="com.shopping.model.OrderDetails"%>
<%@page import="java.util.List"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <myheader>My Order List</myheader>
        <table class="table">
            <tr>
                <th>Order ID#</th><th>Product</th><th>Price</th><th>Qty</th><th>Sell Price</th><th>Customer</th><th>Date</th><th></th>
            </tr>
            <%
                DemoService ds = new DemoService();
                List<OrderDetails> od = ds.getSellerOrderDetails(session);
                for (int i = 0; i < od.size(); i++) {
            %>     
            <tr>
                <td><%=od.get(i).getOrderid()%>#<%=od.get(i).getItemno()%></td>
                <td><%=od.get(i).getProd_desc()%>(<%=od.get(i).getVariant()%>)</td>
                <td>&#x20b9;<%=od.get(i).getUprice()%>(<%=od.get(i).getDiscount()%>%)</td>
                <td><%=od.get(i).getQty()%></td>
                <td>&#x20b9;<%=od.get(i).getSellprice()%></td>
                <td><%=od.get(i).getCustomername()%></td>
                <td><%=od.get(i).getCrtdt()%></td>
                <td>
                    <form action="updateOrdStat" name="frmos" id="frmos" method="post">
                        <input type="hidden" id="hoid" name="hoid" value="<%=od.get(i).getOrderid()%>">
                        <input type="hidden" id="hitem" name="hitem" value="<%=od.get(i).getItemno()%>">
                        <button class="btn btn-primary mb-3" type="submit">Status</button>
                    </form>
                    
                </td>
            </tr>
            <% }%>
        </table>
    </body>
</html>