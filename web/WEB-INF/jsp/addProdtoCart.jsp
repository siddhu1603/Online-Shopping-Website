<%@page import="com.shopping.service.DemoService"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    DemoService ds = new DemoService();
    String retval = ds.addprodtoCart(request.getParameter("prodid"),Integer.parseInt(request.getParameter("slno")),session);
    response.setContentType("text/plain");
    response.getWriter().write(retval);
%>
