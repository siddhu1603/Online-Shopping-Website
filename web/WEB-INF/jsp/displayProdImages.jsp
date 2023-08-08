<%@page import="com.shopping.model.ProductImages"%>
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
    //System.out.println(pid);
    List<ProductImages> pi = ds.getProdImages(pid);
    for (int i = 0; i < pi.size(); i++) {    
        if (i < 1) {
            _op += "<table class=\"table-sm\"><tr>";
        }
        _op += "<td>";
        _op += "<img src=\""+pi.get(i).getPath()+pi.get(i).getFilename() +"\" title=\""+pi.get(i).getImgdesc()+"\" height=\"100\">";
        _op += "</td>";          
    }
    if (_op.length() > 1) {
        _op += "</tr></table>";
    }

    response.setContentType("text/plain");
    response.getWriter().write(_op);
%>
