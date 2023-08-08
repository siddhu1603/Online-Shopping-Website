<%@page import="com.shopping.service.DemoService"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    DemoService ds = new DemoService();
    String retval = ds.addprodtoWishlist(request.getParameter("prodid"),Integer.parseInt(request.getParameter("slno")),session);
    retval=retval.equals("T")?"Successfully added to wishlist":"Unable to add to wishlist";
    response.setContentType("text/plain");
    response.getWriter().write(retval);
%>