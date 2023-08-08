<%@page import="com.shopping.model.Address"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page import="java.util.List"%>
<%
    String _op = "";
    DemoService ds = new DemoService();
    List<Address> addrList = ds.getAddrList(session);
    for (int i = 0; i < addrList.size(); i++) {
        _op += "<div class=\"card text-bg-info mb-1\">";
        _op += "<div class=\"card-header\"><b>" + addrList.get(i).getAlias_name() + "</b></div>";
        _op += "<Div class=\"card-body\" style=\"text-align: left;background-color: lavender;\">";
        _op += "Line1: " + addrList.get(i).getAddr1() + "<br/>";
        if (addrList.get(i).getAddr2() != null) {
            _op += "Line2: " + addrList.get(i).getAddr2() + "<br/>";
        }
        if (addrList.get(i).getAddr3() != null) {
            _op += "Line3: " + addrList.get(i).getAddr3() + "<br/>";
        }
        _op += "City: " + addrList.get(i).getCity() + "<br/>";
        _op += "State: " + addrList.get(i).getState() + "<br/>";
        _op += "Pincode: " + addrList.get(i).getPincode() + "<br/>";
        _op += "Created on: " + addrList.get(i).getCrtdt() + "<br/>";
        _op += "</div></div>";
    }
    response.setContentType("text/plain");
    response.getWriter().write(_op);
%>
