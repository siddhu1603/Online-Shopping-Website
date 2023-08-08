<%@page import="com.shopping.model.AboutProduct"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page import="java.util.List"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    String _op = "";
    DemoService ds = new DemoService();
    String pid = request.getParameter("prodid").toString();
    System.out.println(pid);
    List<AboutProduct> p = ds.getProdAbout(pid);
    for (int i = 0; i < p.size(); i++) {
        if (i < 1) {
            _op += "<table class=\"table table-sm\"><thead><tr><th>#</th><th>Description</th><th>Date</th></tr></thead><tbody>";
        }
        _op += "<tr><td>" + (i+1) + "</td><td>" + p.get(i).getAbout() + "</td><td nowrap>" + p.get(i).getCrtdt() + "</td></tr>";
    }
    if (_op.length() > 1) {
        _op += "</tbody></table>";
    }

    response.setContentType("text/plain");
    response.getWriter().write(_op);
%>

