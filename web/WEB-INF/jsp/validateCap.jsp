<%   
    String sysmsg = "T";
    String _cap = request.getParameter("CAP");
    if (!_cap.equals(session.getAttribute("capval"))) {
        sysmsg = "FInvalid captcha.";
    }
    response.setContentType("text/plain");
    response.getWriter().write(sysmsg.trim());
%>
