<%@page import="com.shopping.model.Wallet"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page import="java.util.List"%>
<%
    String _op = "";
    DemoService ds = new DemoService();
    List<Wallet> wList = ds.getWList(session);
    for (int i = 0; i < wList.size(); i++) {
        _op += "<div class=\"card text-bg-info mb-1\">";
        _op += "<div class=\"card-header\"><b>" + wList.get(i).getWallet_name()+ "</b></div>";
        _op += "<Div class=\"card-body\" style=\"text-align: left;background-color: lavender;\">";
        _op += "Amount: " + wList.get(i).getAmount()+ "<br/>";
        _op += "Created on: " + wList.get(i).getCrtdt() + "<br/>";
        _op += "Last Used on: " + wList.get(i).getLastusedt()+ "<br/>";
        _op += "</div></div>";
    }
    response.setContentType("text/plain");
    response.getWriter().write(_op);
%>




