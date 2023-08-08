<%@page import="com.shopping.model.Template"%>
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
    List<Template> spec = ds.getTemplate(pid);

    for (int i = 0; i < spec.size(); i++) {    
        if (i < 1) {
            _op += "<table class=\"table table-sm\"><tr><th width=\"5%\">#</th><th width=\"25%\">Specification</th><th>Value</th><th width=\"25%\">Unit</th></tr>";
        }
        _op += "<tr>";
        _op += "<th><input type=\"text\" class=\"form-control-plaintext\" id=\"slno\" name=\"slno\" value=\""+ spec.get(i).getSlno() +"\" readonly></th>";
        _op += "<td><input type=\"text\" class=\"form-control-plaintext\" id=\"spec\" name=\"spec\" value=\""+ spec.get(i).getSpec() +"\" readonly></td>";
        _op += "<td><input type=\"text\" class=\"form-control\" id=\"specval\" name=\"specval\" maxlength=\"50\" ></td>";
        _op += "<td><input type=\"text\" class=\"form-control-plaintext\" id=\"unit\" name=\"unit\" value=\""+ spec.get(i).getUnit()+"\" readonly></td>";
        _op += "</tr>";  
        //<button type=\"button\" class=\"btn btn-danger\" style=\"padding:0px 4px;\" onClick=\"deleteSpec('"+pid+"',"+spec.get(i).getSlno()+");\">X</button>
    }
    if (_op.length() > 1) {
        _op += "<tr><td colspan=\"4\" align=center><button id=\"btn\" name=\"btn\" type=\"submit\" class=\"btn btn-primary mb-3\">Add Specification</button></td></tr>";
        _op += "</table>";
    }
    response.setContentType("text/plain");
    response.getWriter().write(_op);
%>

