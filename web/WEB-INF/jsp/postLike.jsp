<%@page import="com.shopping.service.DemoService"%>

<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    DemoService ds = new DemoService();
    String retval = ds.registerLike(request.getParameter("prodid"),request.getParameter("liketype"),session);
    retval=retval.equals("T")?"Successfully posted":(retval.equals("A")?"Information already posted":"Unable to post like/dislike");
    response.setContentType("text/plain");
    response.getWriter().write(retval);
%>
